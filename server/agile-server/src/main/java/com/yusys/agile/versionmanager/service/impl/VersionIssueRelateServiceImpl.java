package com.yusys.agile.versionmanager.service.impl;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.externalapiconfig.dao.util.ExternalApiConfigUtil;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.dao.IssueSystemRelpMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.domain.IssueSystemRelp;
import com.yusys.agile.issue.domain.IssueSystemRelpExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueListDTO;
import com.yusys.agile.issue.dto.IssueStringDTO;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.PlanStatesEnum;
import com.yusys.agile.issue.enums.QueryTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.requirement.SysExtendFieldDetailDTO;
import com.yusys.agile.requirement.domain.SysExtendFieldDetail;
import com.yusys.agile.requirement.service.SysExtendFieldDetailService;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.agile.utils.StringUtil;
import com.yusys.agile.versionmanager.dao.VersionIssueRelateMapper;
import com.yusys.agile.versionmanager.enums.IssueApproveStatusEnum;
import com.yusys.agile.versionmanager.service.VersionIssueRelateService;
import com.yusys.agile.versionmanager.service.VersionManagerService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yusys.agile.versionmanager.domain.*;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName VersionIssueRelateServiceImpl
 * @Description TODO
 *
 * @Date 2020/8/21 20:48
 * @Version 1.0
 */
@Service
public class VersionIssueRelateServiceImpl implements VersionIssueRelateService {

    @Resource
    private IssueMapper issueMapper;
    @Resource
    private IssueService issueService;
    @Resource
    private VersionManagerService versionManagerService;

    @Resource
    private VersionIssueRelateMapper versionIssueRelateMapper;
    @Resource
    private SysExtendFieldDetailService sysExtendFieldDetailService;
    @Resource
    private ExternalApiConfigUtil externalApiConfigUtil;
    @Resource
    private IssueSystemRelpMapper issueSystemRelpMapper;


    @Override
    public List<IssueDTO> getVersionNoRelateIssues(Long versionId, Byte issueType, Long sprintId, String BAPerson, String bizNum, String formalReqCode , String askLineTime, String relatedSystem, Integer pageSize, Integer pageNum, String  idOrTitle, SecurityDTO securityDTO) {
        String province = externalApiConfigUtil.getPropValue("ATTRIBUTION");
        List<Long> issueIds = new ArrayList<>();
        Long projectId = securityDTO.getProjectId();

            VersionIssueRelateExample relateExample = new VersionIssueRelateExample();
            VersionIssueRelateExample.Criteria criteria = relateExample.createCriteria();

            if(versionId!=null){
                criteria.andVersionIdEqualTo(versionId);
            }
            if(Optional.ofNullable(issueType).isPresent()){
                criteria.andIssueTypeEqualTo(issueType);
            }

            List<VersionIssueRelate> issueRelates = versionIssueRelateMapper.selectByExample(relateExample);
            if(CollectionUtils.isNotEmpty(issueRelates)){
                issueRelates.forEach(issueRelate ->{issueIds.add(issueRelate.getIssueId());});
            }

        List<Long> longList = Lists.newArrayList();

        // 不传page信息时查全部数据
        if (Optional.ofNullable(pageSize).isPresent() && Optional.ofNullable(pageNum).isPresent() ) {
            PageHelper.startPage(pageNum,  pageSize);
        }
        IssueDTO issueExample = new IssueDTO();
        issueExample.setProjectId(projectId);
        if(CollectionUtils.isNotEmpty(issueIds)){
            if(longList.size()==0){
                issueExample.setInVersionIssueList(issueIds);
            }else{
                issueExample.setSysExtendList(longList);
            }
        }
        if(Optional.ofNullable(issueType).isPresent()){
            issueExample.setIssueType(issueType);
        }
        if(Optional.ofNullable(sprintId).isPresent()){
            issueExample.setSprintId(sprintId);
        }
        if(StringUtils.isNotBlank(BAPerson)){
            issueExample.setHandler(Long.parseLong(BAPerson));
        }
        // 判断是根据id还是name
        if (StringUtils.isNotBlank(idOrTitle)) {
            issueExample.setTitle("%" + idOrTitle + "%");
            try {
                Long id = Long.valueOf(idOrTitle);
                // 能转成long，说明可能是id，也可能是name
                longList.add(id);
            //    criteria1.andIssueIdIn(longList);
                // 同时赋值给标题
         //       criteria2.andTitleLike("%" + idOrTitle + "%");
            //    issueExample.or(criteria2);
            } catch (Exception e) {
                // 存在异常说明只能查name
              //  criteria1.andTitleLike("%" + idOrTitle + "%");
            }
        }
        issueExample.setState(IssueStateEnum.TYPE_VALID.CODE);
        List<IssueDTO> issueDTOS = issueMapper.selectIssueForVersion(issueExample);

        if(CollectionUtils.isNotEmpty(issueDTOS)){
            Map<Long,List<IssueDTO>>  longListMap = issueDTOS.stream().collect(Collectors.groupingBy(IssueDTO::getIssueId));
            List<Long>  longList1 =  Lists.newArrayList(longListMap.keySet());
            Map<String, Map> mapMap = issueService.IssueMap(securityDTO.getProjectId(),null);
            // 处理用户数据，
            Map<Long, String> userMap = mapMap.get("userMap");
            List<SysExtendFieldDetail> allSysExtendFieldDetailList = sysExtendFieldDetailService.getSysExtendFieldDetails(Lists.newArrayList(longListMap.keySet()),null);
            Map<Long,List<SysExtendFieldDetail>> allLongListHashMap = allSysExtendFieldDetailList.stream().collect(Collectors.groupingBy(SysExtendFieldDetail::getIssueId));
            for (IssueDTO issueDTO : issueDTOS) {
                    Map<Long, List<SsoSystem>> mapSsoSystem = mapMap.get("mapSsoSystem");
                    Map<Long, List<IssueSystemRelp>> mapIssueSystemRelp = mapMap.get("mapIssueSystemRelp");
                    if (mapIssueSystemRelp.keySet().contains(issueDTO.getIssueId())) {
                        List<IssueSystemRelp> list = mapIssueSystemRelp.get(issueDTO.getIssueId());
                        //获取子的负责人
                        Map issueChildrenMap = getIssueDTOChildren(issueDTO.getIssueId());
                        StringBuilder strName = new StringBuilder();
                        StringBuilder strIds = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            SsoSystem ssoSystem = mapSsoSystem.get(list.get(i).getSystemId()).get(0);
                            if(null != issueChildrenMap && issueChildrenMap.containsKey(ssoSystem.getSystemId()) && null != issueChildrenMap.get(ssoSystem.getSystemId())){
                                strName.append(ssoSystem.getSystemName() + "(" + MapUtils.getString(userMap, issueChildrenMap.get(ssoSystem.getSystemId())) + ")");
                                strIds.append(ssoSystem.getSystemId() + "(" + issueChildrenMap.get(ssoSystem.getSystemId()) + ")");
                            }else {
                                strName.append(ssoSystem.getSystemName());
                                strIds.append(ssoSystem.getSystemId());
                            }
                            if (i != list.size() - 1) {
                                strName.append(",");
                                strIds.append(",");
                            }
                        }
                        if (!"".equals(strName) && !"".equals(strIds)) {
                            issueDTO.setSystemName(strName.toString());
                        }
                        //laneId
                        String tempStr = "";
                        if (issueDTO.getLaneId() != null) {
                            Map<Long, List<KanbanStageInstance>> kanbanStageInstanceMap = mapMap.get("kanbanStageInstanceMap");
                            List<KanbanStageInstance> instances = kanbanStageInstanceMap.get(issueDTO.getLaneId());
                            if (CollectionUtils.isNotEmpty(instances)) {
                                KanbanStageInstance kanbanStageInstance = instances.get(0);
                                if (kanbanStageInstance != null) {
                                    if (issueDTO.getStageId() != null) {
                                        tempStr = kanbanStageInstanceMap.get(issueDTO.getLaneId()).get(0).getStageName() + "/" + kanbanStageInstance.getStageName();
                                    }
                                }
                            }
                            issueDTO.setStageName(tempStr);
                        }
                    }
                    if(allLongListHashMap.containsKey(issueDTO.getIssueId())){
                        issueDTO.setSysExtendFieldDetailDTOList(ReflectObjectUtil.copyProperties4List(allLongListHashMap.get(issueDTO.getIssueId()), SysExtendFieldDetailDTO.class));
                    }
                    //handler 处理人
                    if (issueDTO.getHandler() != null) {
                        issueDTO.setHandlerName(MapUtils.getString(userMap, issueDTO.getHandler()));
                    }
                }
            }
        return issueDTOS;
    }

    /**
     *
     * @Date 2020/11/23
     * @Description 获取Feature的系统和处理人
     * @param issueId
     * @Return java.util.Map
     */
    private Map getIssueDTOChildren(Long issueId){
        Map<Long, Long> map = new HashMap<Long,Long>();
        IssueExample example = new IssueExample();
        example.createCriteria()
                .andParentIdEqualTo(issueId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FEATURE.CODE);
        List<Issue> issueList = issueMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(issueList)){
            for(Issue issue : issueList){
                IssueSystemRelpExample relpExample = new IssueSystemRelpExample();
                relpExample.createCriteria().andIssueIdEqualTo(issue.getIssueId());
                List<IssueSystemRelp> systemRelpList = issueSystemRelpMapper.selectByExample(relpExample);
                if(null == issue.getHandler()){
                    return map;
                }
                if(CollectionUtils.isNotEmpty(systemRelpList)){
                        map.put(systemRelpList.get(0).getSystemId(),issue.getHandler());
                }
            }
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindVersionRelateIssues(List<VersionIssueRelate> issueRelates, SecurityDTO securityDTO,String province) {

            if(CollectionUtils.isNotEmpty(issueRelates)){
                Long versionId = 0L;

                //获取版本计划关联的工作项
                List<Long> parentIdList = Lists.newArrayList();
                for(VersionIssueRelate versionIssueRelate : issueRelates){
                    versionId = versionIssueRelate.getVersionId();
                    parentIdList.add(versionIssueRelate.getIssueId());
                }

                //把工作项的第一层子工作项也关联到版本计划中
                if(CollectionUtils.isNotEmpty(parentIdList)){
                    IssueExample example = new IssueExample();
                    IssueExample.Criteria criteria = example.createCriteria();
                    criteria.andParentIdIn(parentIdList);
                    List<Issue> childList = issueMapper.selectByExample(example);
                    if(CollectionUtils.isNotEmpty(childList)){
                        generateVersionIssueRelate(issueRelates,childList,versionId);
                    }
                }

                issueRelates.forEach(issueRelate->{
                    issueRelate.setProjectId(securityDTO.getProjectId());
                    issueRelate.setApprovalStatus(NumberConstant.ZERO.byteValue());
                    versionIssueRelateMapper.insertSelective(issueRelate);
                });
            }

    }



    private  List<IssueDTO>  getOtherDeployTypeVersionList(List<Long> epicIdList) {
        List<IssueDTO> issueDTOList = issueService.selectIssueAndExtendsByIssueIds(epicIdList);
        for(IssueDTO issueDTO : issueDTOList){
            List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList  = issueDTO.getSysExtendFieldDetailDTOList();
            sysExtendFieldDetailDTOList.stream().filter(s -> s.getFieldId().equals("bizNum")).forEach(s ->  issueDTO.setBizNum(s.getValue()));
        }
        return issueDTOList;
    }

    private void getNoDeployIssueList(List<Long> epicIdList,List<IssueDTO> issueDTONoDeployList ) {
        List<IssueDTO> issueDTOList = issueService.selectIssueAndExtendsByIssueIds(epicIdList);
        for(IssueDTO isueDTO : issueDTOList){
            List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList  = isueDTO.getSysExtendFieldDetailDTOList();
            sysExtendFieldDetailDTOList.stream().filter(s -> s.getFieldId().equals("bizNum")).forEach(s ->  isueDTO.setBizNum(s.getValue()));
            for(SysExtendFieldDetailDTO sysExtendFieldDetailDTO : sysExtendFieldDetailDTOList){
                if(sysExtendFieldDetailDTO.getFieldId().equals("planStates") &&
                        sysExtendFieldDetailDTO.getValue().equals(PlanStatesEnum.NO_NEED_DEPLOY.CODE)){
                    issueDTONoDeployList.add(isueDTO);
                    break;
                }
            }
        }
    }










    @Override
    public Map<Long, List<Long>> getBindingReqIdToSystemBranchIdListMap(List<VersionIssueRelate> issueRelates) {
        Map<Long, List<Long>> reqIdToSystemBranchIdListMap = new HashMap<>();
        List<Long> issueIdList = Lists.newArrayList();
        for(VersionIssueRelate versionIssueRelate : issueRelates){
            issueIdList.add(versionIssueRelate.getIssueId());
        }
        List<IssueDTO> issueList = issueService.getIssueListByIssueIds(issueIdList,true);
        if (CollectionUtils.isNotEmpty(issueList)) {
            for (IssueDTO issueDTO : issueList) {
                Long issueId = issueDTO.getIssueId();
                List<IssueDTO> systemBranchBindingDTOList = issueDTO.getChildren();
                if (CollectionUtils.isNotEmpty(systemBranchBindingDTOList)) {
                    List<Long> systemBranchIdList = new ArrayList<>();
                    for (IssueDTO systemBranchBindingDTO : systemBranchBindingDTOList) {
                        systemBranchIdList.add(systemBranchBindingDTO.getIssueId());
                    }
                    reqIdToSystemBranchIdListMap.put(issueId, systemBranchIdList);
                }
            }
        }
        return reqIdToSystemBranchIdListMap;
    }

    private void generateVersionIssueRelate(List<VersionIssueRelate> issueRelates, List<Issue> featureList,Long versionId) {
        VersionIssueRelate versionIssueRelate;
        for(Issue issue : featureList){
            versionIssueRelate = new VersionIssueRelate();
            versionIssueRelate.setIssueId(issue.getIssueId());
            versionIssueRelate.setIssueType(issue.getIssueType());
            versionIssueRelate.setVersionId(versionId);
            versionIssueRelate.setProjectId(issue.getProjectId());
            issueRelates.add(versionIssueRelate);
        }
    }

    @Override
    public PageInfo getVersionRelateIssues(Long versionId, Map<String, Object> map,SecurityDTO securityDTO)throws Exception {
        PageInfo pageInfo = new PageInfo();
        List<Long> issueIds = new ArrayList<>();
        List<Long> issueIdsAll = new ArrayList<>();
        List<Long> featureIdList;
        Long projectId = securityDTO.getProjectId();

        Integer validStatus;
            VersionIssueRelateExample relateExample = new VersionIssueRelateExample();
            VersionIssueRelateExample.Criteria criteria = relateExample.createCriteria();
            criteria.andVersionIdEqualTo(versionId);
            List<VersionIssueRelate> issueRelates = versionIssueRelateMapper.selectByExample(relateExample);
            Map<Long,List<VersionIssueRelate>> longListMap = new HashMap<>() ;
            if(!CollectionUtils.isNotEmpty(issueRelates)){
                return pageInfo;
            }
            issueRelates.forEach(issueRelate ->{issueIds.add(issueRelate.getIssueId());});
            longListMap = issueRelates.stream().collect(Collectors.groupingBy(VersionIssueRelate::getIssueId));
            List<IssueListDTO> issueListDTOS = Lists.newArrayList();
            map.put("issueIds",issueIds);
            JSONObject jsonObject = new JSONObject(map);
            IssueStringDTO issueStringDTO  = JSON.parseObject(jsonObject.toJSONString(),IssueStringDTO.class);
            //项目下的IssueData
            Map<String, Map> mapMap =  issueService.IssueMap(projectId,null);
            // 处理用户数据，
            Map<Long, List<SysExtendFieldDetail>> mapSysExtendFieldDetail = mapMap.get("mapSysExtendFieldDetail");
            List<Issue> issues = issueService.queryIssueList(map, projectId);
            if (issues != null && !issues.isEmpty()) {
                for (Issue issue : issues) {
                    IssueListDTO issueListDTOResult = ReflectObjectUtil.copyProperties(issue, IssueListDTO.class);
                    issueService.arrangeIssueListDTO(issue, issueListDTOResult, mapMap);
                    issueService.sortIssueDTO(QueryTypeEnum.TYPE_INVALID.CODE, issueStringDTO.getRootIds(), issueListDTOResult, mapMap);
                    if (longListMap.containsKey(issue.getIssueId())) {
                        Map map1 = new HashMap<String, String>();
                        map1.put("name", IssueApproveStatusEnum.getDesc(longListMap.get(issue.getIssueId()).get(0).getApprovalStatus().toString()));
                        map1.put("id",longListMap.get(issue.getIssueId()).get(0).getApprovalStatus());
                        issueListDTOResult.setAssessIsPass(map1);
                    }
                    Map map2 = new HashMap<String, String>();
                    map2.put("name", "未移除");
                    map2.put("id",1);
                    issueListDTOResult.setVersionIsRemove(map2);
                    if (mapSysExtendFieldDetail.containsKey(issue.getIssueId())) {
                        for(int i=0;i<mapSysExtendFieldDetail.get(issue.getIssueId()).size();i++){
                            SysExtendFieldDetail sysExtendFieldDetail =   mapSysExtendFieldDetail.get(issue.getIssueId()).get(i);
                            if(sysExtendFieldDetail.getFieldId().equals("approvalResult")){
                                Map map1 = new HashMap<String, String>();
                                map1.put("name",sysExtendFieldDetail.getValue());
                                map1.put("id",sysExtendFieldDetail.getValue());
                                issueListDTOResult.setAssessIsPassResult(map1);
                            }
                        }
                    }
                    issueListDTOS.add(issueListDTOResult);
                }
            }
            pageInfo = new PageInfo<>(issues);
            pageInfo.setList(issueListDTOS);
        map.put("pageNum", null);
        map.put("pageSize", null);
        List<Issue> issueTotal = issueService.queryIssueList(map, projectId);
        pageInfo.setTotal(issueTotal.size());
        return pageInfo;
    }

    @Override
    public void removeVersionRelateIssues(Long oldVersionId,Long newVersionId,List<Long> issueIds, SecurityDTO securityDTO) {
        String province = externalApiConfigUtil.getPropValue("ATTRIBUTION");



            if(CollectionUtils.isNotEmpty(issueIds)){
                VersionIssueRelateExample relateExample = new VersionIssueRelateExample();
                VersionIssueRelateExample.Criteria criteria = relateExample.createCriteria();
                criteria.andVersionIdEqualTo(oldVersionId)
                        .andIssueIdIn(issueIds);
                List<VersionIssueRelate> oldVersionIssueRelates =  versionIssueRelateMapper.selectByExample(relateExample);
              for(int i= 0;i< oldVersionIssueRelates.size();i++){
                  VersionIssueRelate versionIssueRelate = oldVersionIssueRelates.get(i);
                  versionIssueRelate.setVersionId(newVersionId);
                  versionIssueRelateMapper.insertSelective(versionIssueRelate);
              }

                versionIssueRelateMapper.deleteByExample(relateExample);
            }

    }

    @Override
    public void removeRelateIssuesByVersionId(Long versionId) {
        VersionIssueRelateExample relateExample = new VersionIssueRelateExample();
        VersionIssueRelateExample.Criteria criteria = relateExample.createCriteria();
        criteria.andVersionIdEqualTo(versionId);
        versionIssueRelateMapper.deleteByExample(relateExample);
    }

    /**
     *功能描述 根据版本id和项目id获取工作项目id
     *
     * @date 2020/9/18
     * @param issueStringDTO
     * @param projectId
     * @return
     */
    @Override
    public List<VersionIssueRelate> queryVersionIssueRelatList(IssueStringDTO issueStringDTO, Long projectId) {
        List<Long> versionIdList = Lists.newArrayList();
        boolean b = false;
        if(StringUtils.isNotEmpty(issueStringDTO.getVersionName())){
            String[] versionIdStrs = issueStringDTO.getVersionName().split(",");
            b = StringUtil.contains(versionIdStrs,"null");
            versionIdList = StringUtil.removeNULL("null",versionIdStrs).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
//            Long[] versionIds = (Long[]) ConvertUtils.convert(issueStringDTO.getVersionId().split(","),Long.class);
//            Collections.addAll(versionIdList,versionIds);
        }

        VersionIssueRelateExample relateExample = new VersionIssueRelateExample();
        VersionIssueRelateExample.Criteria criteria = relateExample.createCriteria();

        if(versionIdList != null && versionIdList.size()>0){
            criteria.andProjectIdEqualTo(projectId).andVersionIdIn(versionIdList);
            if(b){
                VersionIssueRelateExample.Criteria criteria1 = relateExample.createCriteria();
                criteria1.andProjectIdEqualTo(projectId).andVersionIdIsNull();
                relateExample.or(criteria1);
            }
        }else{
            if(b){
                criteria.andProjectIdEqualTo(projectId).andVersionIdIsNull();
            }
        }

        List<VersionIssueRelate> issueRelates = versionIssueRelateMapper.selectByExample(relateExample);
        return issueRelates;
    }

    @Override
    public VersionIssueRelate queryVersionIssueRelate(Long issueId) {
        return versionIssueRelateMapper.selectVersionIssueRelateByIssueId(issueId);
    }

    @Override
    public void audit(List<IssueDTO> issueDTONoDeployList, Long versionPlanId) {

    }
}
