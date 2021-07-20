package com.yusys.agile.issue.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yusys.agile.commit.dto.CommitDTO;
import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.fault.enums.FaultStatusEnum;
import com.yusys.agile.fault.enums.FaultTypeEnum;
import com.yusys.agile.fault.service.FaultService;
import com.yusys.agile.headerfield.dao.HeaderFieldMapper;
import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.headerfield.domain.HeaderFieldExample;
import com.yusys.agile.headerfield.enums.IsCustomEnum;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.headerfield.util.HeaderFieldUtil;
import com.yusys.agile.issue.dao.IssueHistoryRecordMapper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.dao.UserAttentionMapper;
import com.yusys.agile.issue.dto.*;
import com.yusys.agile.issue.service.IssueCustomFieldService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.IssueSystemRelpService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.issue.utils.IssueHistoryRecordFactory;
import com.yusys.agile.issue.utils.IssueUpRegularFactory;
import com.yusys.agile.leankanban.dao.SLeanKanbanMapper;
import com.yusys.agile.leankanban.domain.SLeanKanban;
import com.yusys.agile.leankanban.dto.SLeanKanbanDTO;
import com.yusys.agile.leankanban.enums.LaneKanbanStageConstant;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.module.domain.Module;
import com.yusys.agile.module.service.ModuleService;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.sysextendfield.SysExtendFieldDetailDTO;
import com.yusys.agile.sysextendfield.domain.SysExtendField;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import com.yusys.agile.sysextendfield.service.SysExtendFieldService;
import com.yusys.agile.sysextendfield.util.SytExtendFieldDetailFactory;
import com.yusys.agile.review.dto.StoryCheckResultDTO;
import com.yusys.agile.review.service.ReviewService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.service.StageService;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.enums.TeamTypeEnum;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.agile.user.service.ReqUserRlatService;
import com.yusys.agile.utils.DateTools;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.agile.utils.StringUtil;
import com.yusys.agile.versionmanager.domain.VersionIssueRelate;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.service.VersionIssueRelateService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yusys.agile.issue.domain.*;
import com.yusys.agile.issue.enums.*;
import com.yusys.agile.versionmanager.service.VersionManagerService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoProject;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * :
 *
 * @Date: 2020/4/16
 * @Description: 1
 */
@Service("issueService")
public class IssueServiceImpl implements IssueService {

    private static final Logger loggr = LoggerFactory.getLogger(IssueServiceImpl.class);
    private static final String FIELD_STR = "satisfaction";
    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";
    private static final String BYTE = "Byte";
    private static final String LONG = "Long";
    private static final String INTEGER = "Integer";
    private static final String STRING = "String";
    private static final String DATE = "Date";
    private static final Integer ATTENIONTYPE_1 = 1;//1是issue
    private static final String USERMAP = "userMap";
    private static final String ISSUEPARENTMAP = "issueParentMap";
    private static final String MAPSSOSYSTEM = "mapSsoSystem";
    private static final String MODULEMAP =  "moduleMap";
    private static final String SPRINTMAP =   "sprintMap";
    private static final String MAPSTEAM =   "mapSTeam";
    private static final String MAPUSERATTENTION =  "mapUserAttention";
    private static final String MAPLISTISSUECUSTOMFIELD =  "mapListIssueCustomField";
    private static final String MAPHEADERFIELDCONTENT=  "mapHeaderFieldContent";







    /**
     * 百分号
     */
    private static final String PERCENT_SIGN = "%";

    @Resource
    private IssueMapper issueMapper;
    @Resource
    private Sprintv3Service sprintv3Service;
    @Resource
    private IssueFactory issueFactory;
    @Resource
    private HeaderFieldMapper headerFieldMapper;
    @Autowired
    private FaultService faultService;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private IFacadeSystemApi iFacadeSystemApi;
    @Resource
    private StageService stageService;
    @Resource
    private IssueSystemRelpService issueSystemRelpService;
    @Resource
    private IssueHistoryRecordMapper issueHistoryRecordMapper;
    @Resource
    private UserAttentionMapper userAttentionMapper;
    @Autowired
    private IssueHistoryRecordFactory recordFactory;
    @Resource
    private IssueCustomFieldService issueCustomFieldService;
    @Resource
    private HeaderFieldService headerFieldService;
    @Autowired
    private ReviewService reviewService;
    @Resource
    private SysExtendFieldDetailService sysExtendFieldDetailService;
    //业务需求/研发需求列表展示版本计划名称及支持按版本名称高级搜索
    @Resource
    private VersionIssueRelateService versionIssueRelateService;
    @Resource
    private VersionManagerService versionManagerService;
    @Resource
    private IFacadeProjectApi iFacadeProjectApi;
    @Resource
    private ModuleService moduleService;
    @Resource
    private SysExtendFieldService sysExtendFieldService;
    @Resource
    private ReqUserRlatService reqUserRlatService;
    @Resource
    private SytExtendFieldDetailFactory sytExtendFieldDetailFactory;
    @Autowired
    private StoryService storyService;
    @Autowired
    private SSprintMapper ssprintMapper;
    @Autowired
    private IStageService iStageService;
    @Autowired
    private Teamv3Service teamv3Service;
    @Autowired
    private IssueUpRegularFactory issueUpRegularFactory;
    @Autowired
    private LeanKanbanService leanKanbanService;
    @Autowired
    private SLeanKanbanMapper leanKanbanMapper;


    private LoadingCache<Long, SsoUser> userCache = CacheBuilder.newBuilder().build(new CacheLoader<Long, SsoUser>() {
        @Override
        public SsoUser load(Long key) throws Exception {
            return iFacadeUserApi.queryUserById(key);
        }
    });

    private LoadingCache<Long, SsoSystem> systemCache = CacheBuilder.newBuilder().build(new CacheLoader<Long, SsoSystem>() {
        @Override
        public SsoSystem load(Long key) throws Exception {
            return iFacadeSystemApi.querySystemBySystemId(key);
        }
    });

    /**
     * 功能描述
     *
     * @param map
     * @return java.util.List<com.yusys.agile.requirement.SysExtendFieldDetailDTO ;>
     * @date 2020/4/20
     */
    @Override
    public PageInfo getIssueList(Map<String, Object> map) {
        PageInfo pageInfo = new PageInfo();
        List<Map> maps = Lists.newArrayList();
        List<IssueListDTO> issueListDTOS = Lists.newArrayList();
        JSONObject jsonObject = new JSONObject(map);
        IssueStringDTO issueStringDTO = JSON.parseObject(jsonObject.toJSONString(), IssueStringDTO.class);
        Byte issueType = Byte.parseByte(map.get("issueType").toString());
        List<Issue> issues = queryIssueList(map);
        if (CollectionUtils.isEmpty(issues)) {
            pageInfo.setList(new ArrayList());
            return pageInfo;
        }
        Map<Long, List<Issue>> issueRestltMap = issues.stream().collect(Collectors.groupingBy(Issue::getIssueId));

        List<Long> allIssueId = issueMapper.listAllIssueId(Lists.newArrayList(issueRestltMap.keySet()));
        if (CollectionUtils.isEmpty(allIssueId)) {
            pageInfo.setList(new ArrayList());
            return pageInfo;
        }
        //当前页
        Map<String, Map> mapMap = issueMap(null);
        if (issues != null && !issues.isEmpty()) {
            for (Issue issue : issues) {
                IssueListDTO issueListDTOResult = ReflectObjectUtil.copyProperties(issue, IssueListDTO.class);
                arrangeIssueListDTO(issue, issueListDTOResult, mapMap);
                sortIssueDTO(QueryTypeEnum.TYPE_VALID.CODE, issueStringDTO.getRootIds(), issueListDTOResult, mapMap);
                issueListDTOS.add(issueListDTOResult);
            }
        }
        pageInfo = new PageInfo<>(issues);
        /**
         *
         * 以下是扩展字段查询组织数据逻辑
         */
        if (CollectionUtils.isNotEmpty(issueListDTOS)) {
            List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getIssueExtendDetailList(allIssueId);
            Map<Long, List<SysExtendFieldDetail>> longListMap = sysExtendFieldDetailList.stream().collect(Collectors.groupingBy(SysExtendFieldDetail::getIssueId));
            for (IssueListDTO issueListDTO : issueListDTOS) {
                Map<String, Object> mapT = Maps.newHashMap();
                BeanMap beanMap = BeanMap.create(issueListDTO);
                for (Object key : beanMap.keySet()) {
                    mapT.put(key.toString(), beanMap.get(key));
                }
                if (longListMap.containsKey(issueListDTO.getIssueId())) {
                    List<SysExtendFieldDetail> sysExtendFieldDetailList1 = longListMap.get(issueListDTO.getIssueId());
                    for (SysExtendFieldDetail s : sysExtendFieldDetailList1) {
                        mapT.put(s.getFieldId(), translateExtendFieldMap(s.getFieldId(), s.getValue().trim(),issueType,mapMap ));
                    }
                }
                maps.add(mapT);
            }

        }
        pageInfo.setList(maps);
        map.put("pageNum", null);
        map.put("pageSize", null);
        List<Issue> issueTotal = queryIssueList(map);
        pageInfo.setTotal(issueTotal.size());
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createRelation(Long parentId, Long issueId) {
        if(Optional.ofNullable(parentId).isPresent()){
            Issue issue = issueMapper.selectByPrimaryKey(parentId);
            if(IsAchiveEnum.ACHIVEA_TRUE.CODE.equals(issue.getIsArchive())){
                throw new BusinessException("工作项已归档不能关联");
            }
        }
        List<IssueHistoryRecord> history = new ArrayList<>();
        Issue issueOld = issueMapper.selectByPrimaryKey(issueId);
        String oldValue = Optional.ofNullable(issueOld.getParentId()).toString();
        setHistoryRecordList(history, issueId, oldValue, parentId.toString());
        issueFactory.dealHistory(history);
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andIssueIdEqualTo(issueId);
        Issue issue = new Issue();
        issue.setParentId(parentId);
        issueMapper.updateByExampleSelective(issue, issueExample);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRelation(Long parentId, Long issueId) {
        List<IssueHistoryRecord> history = new ArrayList<>();
        setHistoryRecordList(history, issueId, parentId.toString(), null);
        issueFactory.dealHistory(history);
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        Long sprintId = getRelatedIssueSprintId(issue, IssueTypeEnum.TYPE_TASK.CODE);
        //迭代信息校验
        storyService.checkSprintParam(issueId, sprintId);
        issueMapper.deleteRelation(parentId, sprintId, issueId);
    }

    /**
     * @param issue
     * @param type
     * @return
     * @description 根据工作项编号和类型查询迭代编号
     * @date 2020/08/19
     */
    private Long getRelatedIssueSprintId(Issue issue, Byte type) {
        Long sprintId = null;
        if (null != issue) {
            Byte issueType = issue.getIssueType();
            Assert.notNull(issueType, "issueId:[" + issue.getIssueId() + "]工作项类型不能为空");
            if (ObjectUtil.equals(issueType, type)) {
                sprintId = issue.getSprintId();
            }
        }
        return sprintId;
    }


    /**
     * 功能描述  递归查找Issue的children
     *
     * @param rootIds
     * @param issueListDTO
     * @param queryType    1、不展开child。2、展开child
     * @return void
     * @date 2020/4/16
     */
    @Override
    public void sortIssueDTO(Byte queryType, String rootIds, com.yusys.agile.issue.dto.IssueListDTO issueListDTO, Map<String, Map> mapMap) {
        List<IssueListDTO> issueListDTOS = Lists.newArrayList();
        Boolean flag;
        Map<Long, List<Issue>> issueParentMap = mapMap.containsKey(ISSUEPARENTMAP)?mapMap.get("issueParentMap"):new HashMap<>();
        if (issueParentMap == null) {
            return;
        }
        List<Issue> issuesChildren = issueParentMap.get(issueListDTO.getIssueId());
        issueListDTO.setIsParent(false);
        if (issuesChildren != null && !issuesChildren.isEmpty()) {
            issueListDTO.setIsParent(true);
            flag = QueryTypeEnum.TYPE_INVALID.CODE.equals(queryType);
            if (flag || (rootIds != null && (Arrays.asList(rootIds.split(",")).contains(issueListDTO.getIssueId().toString())))) {
                for (Issue issue : issuesChildren) {
                    IssueListDTO issueDTOChildren = ReflectObjectUtil.copyProperties(issue, IssueListDTO.class);
                    issueDTOChildren = arrangeIssueListDTO(issue, issueDTOChildren, mapMap);
                    sortIssueDTO(queryType, rootIds, issueDTOChildren, mapMap);
                    issueListDTOS.add(issueDTOChildren);
                }
            }
        }
        issueListDTO.setChildren(issueListDTOS);
        //自定义字段
        Map<Long, List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>> mapListIssueCustomField = mapMap.containsKey(MAPLISTISSUECUSTOMFIELD)?mapMap.get(MAPLISTISSUECUSTOMFIELD):new HashMap<>();
        if (mapListIssueCustomField.containsKey(issueListDTO.getIssueId())) {
            issueListDTO.setCustomFieldList(mapListIssueCustomField.get(issueListDTO.getIssueId()));
        }
    }

    @Override
    public IssueDTO selectIssueAndExtends(Long bizBacklogId) {
        Issue issue = issueMapper.selectByPrimaryKey(bizBacklogId);
        IssueDTO issueDTO = ReflectUtil.copyProperties(issue, IssueDTO.class);
        List<Long> issueIdList = Lists.newArrayList();
        issueIdList.add(bizBacklogId);
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getIssueExtendDetailList(issueIdList);
        List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList;
        try {
            sysExtendFieldDetailDTOList = ReflectUtil.copyProperties4List(sysExtendFieldDetailList, SysExtendFieldDetailDTO.class);
        } catch (Exception e) {
            throw new BusinessException("selectIssueAndExtends sysExtendFieldDetailList translate to sysExtendFieldDetailDTOList error{}", e.getMessage());
        }
        issueDTO.setSysExtendFieldDetailDTOList(sysExtendFieldDetailDTOList);
        return issueDTO;
    }

    @Override
    public List<IssueDTO> selectIssueAndExtendsByIssueIds(List<Long> duplicatedBindingReqIds) {
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andIssueIdIn(duplicatedBindingReqIds);
        List<Issue> issueList = issueMapper.selectByExample(issueExample);
        List<IssueDTO> issueDTOList = Lists.newArrayList();
        try {
            issueDTOList = ReflectUtil.copyProperties4List(issueList, IssueDTO.class);
        } catch (Exception e) {
            loggr.error("selectIssueByIssueIds issueList transfer to issueDTOList error{}", e.getMessage());
        }
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getIssueExtendDetailList(duplicatedBindingReqIds);
        List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList = Lists.newArrayList();
        try {
            sysExtendFieldDetailDTOList = ReflectUtil.copyProperties4List(sysExtendFieldDetailList, SysExtendFieldDetailDTO.class);
        } catch (Exception e) {
            loggr.error("selectIssueByIssueIds sysExtendFieldDetailList transfer to sysExtendFieldDetailDTOList error{}", e.getMessage());
        }
        Map<Long, List<SysExtendFieldDetailDTO>> longListMap = sysExtendFieldDetailDTOList.stream().collect(Collectors.groupingBy(SysExtendFieldDetailDTO::getIssueId));
        for (IssueDTO issueDTO : issueDTOList) {
            issueDTO.setSysExtendFieldDetailDTOList(longListMap.get(issueDTO.getIssueId()));
        }
        return issueDTOList;
    }

    @Override
    public List<IssueDTO> getIssueListByIssueIds(List<Long> issueIdList, boolean getChildren) {
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andIssueIdIn(issueIdList);
        List<Issue> issueList = issueMapper.selectByExample(issueExample);
        List<IssueDTO> issueDTOList = Lists.newArrayList();
        try {
            issueDTOList = ReflectUtil.copyProperties4List(issueList, IssueDTO.class);
        } catch (Exception e) {
            loggr.error("getIssueListByIssueIds issueList transfer to issueDTOList error:{}", e.getMessage());
        }
        if (getChildren) {
            issueExample.clear();
            issueExample.createCriteria().andParentIdIn(issueIdList);
            List<Issue> children = issueMapper.selectByExample(issueExample);
            List<IssueDTO> childrenDTOList = Lists.newArrayList();
            try {
                childrenDTOList = ReflectUtil.copyProperties4List(children, IssueDTO.class);
            } catch (Exception e) {
                loggr.error("getIssueListByIssueIds issueList transfer to issueDTOList error:{}", e.getMessage());
            }
            Map<Long, List<IssueDTO>> childrenMap = childrenDTOList.stream().collect(Collectors.groupingBy(IssueDTO::getParentId));
            for (IssueDTO issueDTO : issueDTOList) {
                issueDTO.setChildren(childrenMap.get(issueDTO.getIssueId()));
            }
        }
        return issueDTOList;
    }

    @Override
    public Issue selectIssueByIssueId(Long issueId) {
        return issueMapper.selectByPrimaryKey(issueId);
    }

    /**
     * 功能描述  处理返回的IssueListDTO对象
     *
     * @param issue
     * @param issueListDTO
     * @return void
     * @date 2020/4/17
     */
    @Override
    public IssueListDTO arrangeIssueListDTO(Issue issue, IssueListDTO issueListDTO, Map<String, Map> mapMap) {

        // 处理用户数据，
        Map<Long, String> userMap = mapMap.containsKey(USERMAP)?mapMap.get(USERMAP):new HashMap<>();
        Map map = new HashMap<>();
        //Issue类型
        if (Optional.ofNullable(issue.getIssueType()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", IssueTypeEnum.getName(issue.getIssueType()));
            map.put("id", issue.getIssueType());
            issueListDTO.setIssueType(map);
            if (IssueTypeEnum.TYPE_FAULT.CODE.equals(issue.getIssueType())) {
                if (issue.getCreateTime() != null) {
                    issueListDTO.setBeginDate(issue.getCreateTime());
                }
                if (issue.getDeadline() != null) {
                    issueListDTO.setEndDate(issue.getDeadline());
                }
            }
        }
        //列头的Coentent数据
        Map<String, HashMap<String, String>> mapHeaderFieldContent = mapMap.containsKey(MAPHEADERFIELDCONTENT)?mapMap.get(MAPHEADERFIELDCONTENT):new HashMap<>();
        //任务类型
        if (Optional.ofNullable(issue.getTaskType()).isPresent()) {
            issueListDTO.setTaskType(getOptionList(issue.getTaskType().toString(), "taskType", mapHeaderFieldContent));
        }
        //迭代ID
        if (Optional.ofNullable(issue.getSprintId()).isPresent()) {
            if (mapMap.containsKey(SPRINTMAP)&&mapMap.get(SPRINTMAP).containsKey(issue.getSprintId())) {
                Map<Long, List<SSprint>> sprintMap = mapMap.get(SPRINTMAP);
                map = new HashMap<String, String>();
                map.put("name", sprintMap.get(issue.getSprintId()).get(0).getSprintName());
                map.put("id", issue.getSprintId());
                issueListDTO.setSprintId(map);
            }
        }
        //模块ID
        Map<Long, String> moduleMap = mapMap.containsKey(MODULEMAP)?mapMap.get(MODULEMAP):new HashMap<>();
        if (Optional.ofNullable(issue.getModuleId()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", moduleMap.get(issue.getModuleId()));
            map.put("id", issue.getModuleId());
            issueListDTO.setModuleId(map);
        }
        //系统ID
        if (Optional.ofNullable(issue.getIssueId()).isPresent()) {
            String systemName;
            Long systemId;
            Long parentIssueSystemId = null;
            Byte issueType = issue.getIssueType();
            if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType)) {
                Issue storyIssue = issueMapper.getParentIssue(issue.getIssueId());
                if (null != storyIssue) {
                    parentIssueSystemId = storyIssue.getSystemId();
                }
            }
            Map<Long, List<SsoSystemRestDTO>> mapSsoSystem = mapMap.containsKey(MAPSSOSYSTEM)?mapMap.get(MAPSSOSYSTEM):new HashMap<>();
            if (Optional.ofNullable(parentIssueSystemId).isPresent()) {
                systemName = mapSsoSystem.containsKey(parentIssueSystemId) ? mapSsoSystem.get(parentIssueSystemId).get(0).getSystemName() : "";
                systemId = parentIssueSystemId;
            } else {
                systemName = mapSsoSystem.containsKey(issue.getSystemId()) ? mapSsoSystem.get(issue.getSystemId()).get(0).getSystemName() : "";
                systemId = issue.getSystemId();
            }
            map = new HashMap<String, String>();
            if (StringUtils.isNotEmpty(systemName)) {
                map.put("name", systemName);
                map.put("id", systemId);
                issueListDTO.setSystemId(map);
            }
        }
        //对应的团队
        if (Optional.ofNullable(issue.getIssueId()).isPresent()) {
            issueListDTO.setTeamId(getIssueTeamMap(issue.getIssueId(), issue.getIssueType(), mapMap));
        }
        //priority优先级
        if (Optional.ofNullable(issue.getPriority()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", issue.getPriority());
            map.put("id", issue.getPriority());
            issueListDTO.setPriority(map);
        }
        //importance
        if (Optional.ofNullable(issue.getImportance()).isPresent()) {
            issueListDTO.setImportance(getOptionList(issue.getImportance().toString(), HeaderFieldUtil.IMPORTANCE, mapHeaderFieldContent));
        }
        //completion
        if (Optional.ofNullable(issue.getCompletion()).isPresent()) {
            issueListDTO.setCompletion(getOptionList(issue.getCompletion(), HeaderFieldUtil.COMPLETION, mapHeaderFieldContent));
        }
        //createUid
        if (Optional.ofNullable(issue.getCreateUid()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", MapUtils.getString(userMap, issue.getCreateUid()));
            map.put("id", issue.getCreateUid());
            issueListDTO.setCreateUid(map);
        }
        //handler 处理人
        if (Optional.ofNullable(issue.getHandler()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", MapUtils.getString(userMap, issue.getHandler()));
            map.put("id", issue.getHandler());
            issueListDTO.setHandler(map);
           /* //当issueType是bug时
            if (issue.getIssueType() != null && IssueTypeEnum.TYPE_FAULT.CODE.equals(issue.getIssueType())) {
                issueListDTO.setCreateUid(map);
            }*/
        }

        //       updateUid
        if (Optional.ofNullable(issue.getUpdateUid()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", MapUtils.getString(userMap, issue.getUpdateUid()));
            map.put("id", issue.getUpdateUid());
            issueListDTO.setUpdateUid(map);
        }
        // stageId
        //当阶段id不在 FirstStageEnum (1-8) 之间时会导致 "查询Issue异常：null"
        if (Optional.ofNullable(issue.getStageId()).isPresent()) {
            issueListDTO.setStageId(getFirstStageMapByTypeAndId(issue.getStageId()));
        }
        //laneId
        if (Optional.ofNullable(issue.getLaneId()).isPresent()) {
            issueListDTO.setLaneId(getSecondStageMapByTypeAndId(issue.getLaneId(), issue.getIssueType(), issue.getStageId()));
        }
        //  将阶段与状态拼成   ""/"" 形式
        if (Optional.ofNullable(issue.getStageId()).isPresent()&&
                Optional.ofNullable(issueListDTO.getStageId()).isPresent()&&
                issueListDTO.getStageId().containsKey("name")&&
                Optional.ofNullable(issue.getLaneId()).isPresent() &&
                Optional.ofNullable(issueListDTO.getLaneId()).isPresent()&&
                issueListDTO.getLaneId().containsKey("name")) {
            String name = issueListDTO.getStageId().get("name").toString();
            name = name.trim() + "/" + issueListDTO.getLaneId().get("name").toString().trim();
            issueListDTO.getStageId().put("name", name);
        }

        // 缺陷类型
        if (Optional.ofNullable(issue.getFaultType()).isPresent()) {
            issueListDTO.setFaultType(getOptionList(issue.getFaultType().toString(), HeaderFieldUtil.FAULTTYPE, mapHeaderFieldContent));
        }
        // 缺陷等级
        if (Optional.ofNullable(issue.getFaultLevel()).isPresent()) {
            issueListDTO.setFaultLevel(getOptionList(issue.getFaultLevel().toString(), HeaderFieldUtil.FAULTLEVEL, mapHeaderFieldContent));
        }
        // 解决人
        if (Optional.ofNullable(issue.getFixedUid()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", MapUtils.getString(userMap, issue.getFixedUid()));
            map.put("id", issue.getFixedUid());
            issueListDTO.setFixedUid(map);
        }
        // 验证人
        if (Optional.ofNullable(issue.getTestUid()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", MapUtils.getString(userMap, issue.getTestUid()));
            map.put("id", issue.getTestUid());
            issueListDTO.setTestUid(map);
        }

        //是否归档
        if (Optional.ofNullable(issue.getIsArchive()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", IsAchiveEnum.getName(issue.getIsArchive()));
            map.put("id", issue.getIsArchive());
            issueListDTO.setIsArchive(map);
        }

        Map<Long, List<UserAttention>> mapUserAttention = mapMap.containsKey(MAPUSERATTENTION)?mapMap.get(MAPUSERATTENTION):new HashMap<>();
        if (mapUserAttention.keySet().contains(issue.getIssueId())) {
            issueListDTO.setIsCollect(Byte.parseByte("1"));
        }
        return issueListDTO;
    }


    /**
     * 功能描述 查询当前Issue
     *
     * @param issueId
     * @param issueQuery 1:不查询child，2：查询child
     * @return com.yusys.agile.requirement.SysExtendFieldDetailDTO;
     * @date 2020/4/21
     */
    @Override
    public IssueListDTO getIssue(Long issueId, Byte issueQuery, String noLogin) {

        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        List<Long> longList = Lists.newArrayList();
        longList.add(issueId);
        //租户下的IssueData
        Map<String, Map> mapMap = issueMap(noLogin);
        IssueListDTO issueListDTO = ReflectObjectUtil.copyProperties(issue, IssueListDTO.class);
        String rootIds = "";
        //将一些属性转成对象
        arrangeIssueListDTO(issue, issueListDTO, mapMap);
        //查询是否有children
        Map<Long, List<Issue>> issueParentMap = mapMap.containsKey(ISSUEPARENTMAP)?mapMap.get("issueParentMap"):new HashMap<>();
        issueListDTO.setIsParent(false);
        if (issueParentMap != null && issueParentMap.containsKey(issueId)) {
            issueListDTO.setIsParent(true);
        }
        //是否将children查出
        if (issueQuery != null && issueQuery.equals(Byte.parseByte("2"))) {
            rootIds += issueId;
            sortIssueDTO(issueQuery, rootIds, issueListDTO, mapMap);
        }
        return issueListDTO;
    }

    /**
     * 功能描述  根据issueId查询当前Issue
     *
     * @param issueId
     * @param systemId
     * @return Map
     * @date 2020/10/15
     */
    @Override
    public Map getIssueByIssueId(Long issueId, Long systemId) throws Exception {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        Map map = new HashMap<String, String>();
        if (issue != null) {
            map.put("issueType", issue.getIssueType());
            Long sprintId = issue.getSprintId();
            map.put("sprintId", sprintId);
            SSprintWithBLOBs sprint = ssprintMapper.selectByPrimaryKey(sprintId);
            if (Optional.ofNullable(sprint).isPresent()) {
                map.put("sprintName", sprint.getSprintName());
            }
            Long issueSystemId = issue.getSystemId();
            map.put("systemId", issueSystemId == null ? "" : issueSystemId);
            try {
                if (null != issueSystemId) {
                    SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(issue.getSystemId());
                    map.put("systemName", Optional.ofNullable(ssoSystem).isPresent() ? ssoSystem.getSystemName() : null);
                } else {
                    map.put("systemName", "");
                }
            } catch (Exception e) {
                loggr.info("获取系统名称异常:{}", e.getMessage());
            }
            //epic、feature设置团队信息
            if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issue.getIssueType()) || IssueTypeEnum.TYPE_EPIC.CODE.equals(issue.getIssueType())) {
                setTeamInfo(map, issue.getTeamId(),sprintId,issueId,issue.getIssueType());
            }
            if (IssueTypeEnum.TYPE_STORY.CODE.equals(issue.getIssueType()) ||
                    IssueTypeEnum.TYPE_TASK.CODE.equals(issue.getIssueType())) {
                if(IssueTypeEnum.TYPE_TASK.CODE.equals(issue.getIssueType())){
                    map.put("taskType", issue.getTaskType());
                }
                setTeamInfo(map,null,sprintId,issueId,issue.getIssueType());
            }
        }
        return map;
    }

    private void setTeamInfo(Map map, Long teamId,Long sprintId,Long issueId,Byte issueType) {
        if(IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueType)){
            map.put("teamId", teamId == null ? "" : teamId);
            if (null != teamId) {
                STeam sTeam = teamv3Service.getTeamById(teamId);
                setTeamDetail(map, sTeam);
            } else {
                map.put("teamName", "");
                map.put("teamType", "");
            }
        }else{
            //story or task
            if(null != sprintId){
                //根据迭代ID获取团队信息
                SSprintWithBLOBs sprintWithBLOB = ssprintMapper.selectByPrimaryKey(sprintId);
                STeam sTeam = teamv3Service.getTeamById(sprintWithBLOB.getTeamId());
                setTeamDetail(map, sTeam);
            }else{
                //从feature上获取团队信息
                Issue feature = null;
                if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
                    feature = issueMapper.getParentIssue(issueId);
                } else {
                    Issue story = issueMapper.getParentIssue(issueId);
                    if(Optional.ofNullable(story).isPresent()){
                        feature = issueMapper.getParentIssue(story.getIssueId());
                    }
                }
                if(null != feature){
                    setTeamInfo(map, feature.getTeamId(),null,null,feature.getIssueType());
                }
            }
        }
    }

    private void setTeamDetail(Map map, STeam sTeam) {
        if (null != sTeam) {
            map.put("teamName", sTeam.getTeamName());
            map.put("teamType", sTeam.getTeamType());
        } else {
            map.put("teamName", "");
            map.put("teamType", "");
        }
    }

    /**
     * 功能描述  添加、取消Issue的收藏
     *
     * @param issueId
     * @param isCollect 0：非收藏 1：收藏
     * @return void
     * @date 2020/4/22
     */
    @Override
    public void isCollect(Long issueId, Byte isCollect, SecurityDTO securityDTO) {
        UserAttention userAttention = new UserAttention();
        userAttention.setAttentionType(ATTENIONTYPE_1);
        userAttention.setSubjectId(issueId);
        userAttention.setUserId(securityDTO.getUserId());
        UserAttentionExample userAttentionExample = new UserAttentionExample();
        userAttentionExample.createCriteria()
                .andStateEqualTo("U")
                .andAttentionTypeEqualTo(ATTENIONTYPE_1)
                .andSubjectIdEqualTo(issueId)
                .andUserIdEqualTo(securityDTO.getUserId());
        if (isCollect.equals(Byte.parseByte("1"))) {
            if (userAttentionMapper.selectByExample(userAttentionExample).size() <= 0) {
                userAttentionMapper.insert(userAttention);
            }
        } else {
            userAttentionMapper.deleteByExample(userAttentionExample);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createBatchRelation(Long parentId, List<Long> listIssueId, Long userId) {
        List<IssueHistoryRecord> history = new ArrayList<>();
        for (Long issueId : listIssueId) {
            setHistoryRecordList(history, issueId, null, parentId.toString());
        }
        issueFactory.dealHistory(history);
        Issue issueParent = issueMapper.selectByPrimaryKey(parentId);
        Long sprintId = getRelatedIssueSprintId(issueParent, IssueTypeEnum.TYPE_STORY.CODE);
        issueMapper.createBatchRelation(listIssueId, sprintId, parentId, userId,issueParent.getKanbanId(),issueParent.getTeamId());
    }

    public void setHistoryRecordList(List<IssueHistoryRecord> history, Long issueId, String oldValue, String newValue) {
        IssueHistoryRecord historyRecord = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.PARENTID.getDesc());
        historyRecord.setOldValue(oldValue);
        historyRecord.setNewValue(newValue);
        history.add(historyRecord);
    }

    @Override
    public List<IssueListDTO> issueListByIds(String rootIds, Long projectId) throws Exception {
        Map<String, Map> mapMap = issueMap(null);
        List listId = Lists.newArrayList(rootIds.split(","));
        List<IssueListDTO> issueListDTOS = Lists.newArrayList();
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        //查询有效数据
        criteria.andStateEqualTo(StateEnum.U.getValue());
        criteria.andIssueIdIn(listId);
        criteria.andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
        List<Issue> issues = issueMapper.selectByExampleWithBLOBs(issueExample);
        if (issues != null && !issues.isEmpty()) {
            for (Issue issue : issues) {
                IssueListDTO issueListDTOResult = ReflectObjectUtil.copyProperties(issue, IssueListDTO.class);
                sortIssueDTO(QueryTypeEnum.TYPE_INVALID.CODE, rootIds, arrangeIssueListDTO(issue, issueListDTOResult, mapMap), mapMap);
                issueListDTOS.add(issueListDTOResult);
            }
        } else {
            return issueListDTOS;
        }
        //按照前端传的顺序返回
        Map<Long, List<IssueListDTO>> integerListMap = issueListDTOS.stream().collect(Collectors.groupingBy(IssueListDTO::getIssueId));
        issueListDTOS = Lists.newArrayList();
        for (int i = 0; i < listId.size(); i++) {
            Long key = Long.parseLong(listId.get(i).toString());
            if (integerListMap.containsKey(key)) {
                issueListDTOS.add(integerListMap.get(key).get(0));
            }
        }
        return issueListDTOS;
    }


    @Override
    public void createBatchIssue(String listIssue) {
        JSONArray issueArray = JSON.parseArray(listIssue);
        String newMsg;
        for (Object jsonObject : issueArray) {
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toString(), IssueDTO.class);
            Byte issueType = issueDTO.getIssueType();
            if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueType)) {
                newMsg = "新增研发需求";
                String fieldList = getFieldList(issueDTO);
                issueDTO.setFieldList(fieldList);
                issueFactory.createIssue(issueDTO, "", newMsg, issueType);
            } else {
                issueDTO.setFaultType(FaultTypeEnum.ONLINE.getID());
                faultService.addFault(issueDTO);
            }

        }
    }

    /**
     * @Date: 14:52
     * @Description: 获取需求满意度自定义字段内容
     * @Param: * @param issueDTO
     * @Return: java.lang.String
     */
    public String getFieldList(com.yusys.agile.issue.dto.IssueDTO issueDTO) {
        HeaderFieldExample example = new HeaderFieldExample();
        HeaderFieldExample.Criteria criteria = example.createCriteria();
        criteria.andFieldCodeEqualTo(FIELD_STR);
        List<HeaderField> headerFieldList = headerFieldMapper.selectByExample(example);
        HeaderField headerField = headerFieldList.get(0);
        return "[{fieldId:" + headerField.getFieldId() + ",fieldValue:'" + issueDTO.getSatisfaction() + "'}]";
    }

    /**
     * 功能描述
     *
     * @param filedCodeValue
     * @param filedCode
     * @return java.util.Map
     * @date 2021/2/14
     */
    @Override
    public Map getOptionList(String filedCodeValue, String filedCode, Map<String, HashMap<String, String>> hashMapMap) {
        Map map = new HashMap<String, String>();
        if (hashMapMap.containsKey(filedCode)) {
            for (String id : hashMapMap.get(filedCode).keySet()) {
                if (filedCodeValue.equals(id)) {
                    map.put("id", id);
                    map.put("name", hashMapMap.get(filedCode).get(id));
                    return map;
                }
            }
        }

        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateHandler(Long issueId, Long handler) {
        Issue issue = new Issue();
        issue.setHandler(handler);
        issue.setIssueId(issueId);
        issueMapper.updateHandler(issue);
        //todo 处理代办
        issueFactory.dealCommissionEdit(issueId);
    }

    @Override
    public ControllerResponse recordHistories(Long issueId, Integer pageNum, Integer pageSize, SecurityDTO securityDTO) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            //查询历史记录
            IssueHistoryRecordExample example = new IssueHistoryRecordExample();
            IssueHistoryRecordExample.Criteria criteria = example.createCriteria();
            criteria.andIssueIdEqualTo(issueId);
            example.setOrderByClause(CREATE_TIME_DESC);
            List<IssueHistoryRecordDTO> issueHistoryRecords = issueHistoryRecordMapper.selectByExampleDTO(example);
            Issue issue = issueMapper.selectByPrimaryKey(issueId);
            Byte issueType = issue.getIssueType();
            Map<String, String> sprintMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(issueHistoryRecords)) {
//                Map<String, String> stagesInstanceMapInfo = recordFactory.getStagesInstanceMapInfo(systemId);
                issueHistoryRecords.forEach(issueHistoryRecordDTO -> {
                    //recordType 0 常规文本 1 富文本
                    if ("1".equals(issueHistoryRecordDTO.getRecordType())) {
                        issueHistoryRecordDTO.setOldValue("");
                        issueHistoryRecordDTO.setNewValue("");
                    }
                    if (Optional.ofNullable(issueHistoryRecordDTO.getCreateUid()).isPresent()) {
                        SsoUser ssoUserDTO = iFacadeUserApi.queryUserById(issueHistoryRecordDTO.getCreateUid());
                        if (Optional.ofNullable(ssoUserDTO).isPresent()) {
                            issueHistoryRecordDTO.setCreateName(ssoUserDTO.getUserName());
                        }
                    } else {
                        issueHistoryRecordDTO.setCreateName("");
                    }
                    String operationField = Optional.ofNullable(issueHistoryRecordDTO.getOperationField()).orElse("");
                    String oldValue = issueHistoryRecordDTO.getOldValue();
                    String newValue = issueHistoryRecordDTO.getNewValue();
                    switch (operationField) {
                        case "阶段id":
                        case "二阶段状态id":
                            if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType)) {
                                if (StringUtils.isNotEmpty(oldValue) && NumberUtil.isLong(oldValue)) {
                                    issueHistoryRecordDTO.setOldValue(TaskStatusEnum.getName(Long.valueOf(oldValue)));
                                }
                                if (StringUtils.isNotEmpty(newValue) && NumberUtil.isLong(newValue)) {
                                    issueHistoryRecordDTO.setNewValue(TaskStatusEnum.getName(Long.valueOf(newValue)));
                                }
                            } else if (IssueTypeEnum.TYPE_FAULT.CODE.equals(issueType)) {
                                if (StringUtils.isNotEmpty(oldValue) && NumberUtil.isLong(oldValue)) {
                                    issueHistoryRecordDTO.setOldValue(FaultStatusEnum.getMsg(Long.valueOf(oldValue)));
                                }
                                if (StringUtils.isNotEmpty(newValue) && NumberUtil.isLong(newValue)) {
                                    issueHistoryRecordDTO.setNewValue(FaultStatusEnum.getMsg(Long.valueOf(newValue)));
                                }
                            } else {
//                                if (StringUtils.isNotEmpty(oldValue)) {
//                                    issueHistoryRecordDTO.setOldValue(stagesInstanceMapInfo.get(oldValue));
//                                }
//                                if (StringUtils.isNotEmpty(newValue)) {
//                                    issueHistoryRecordDTO.setNewValue(stagesInstanceMapInfo.get(newValue));
//                                }
                            }
                            break;
                        case "优先级":
                            if (StringUtils.isNotEmpty(oldValue)) {
                                issueHistoryRecordDTO.setOldValue(IssuePriorityEnum.getName(Byte.valueOf(oldValue)));
                            }
                            if (StringUtils.isNotEmpty(newValue)) {
                                issueHistoryRecordDTO.setNewValue(IssuePriorityEnum.getName(Byte.valueOf(newValue)));
                            }
                            break;
                        case "迭代计划":
                            if (StringUtils.isNotEmpty(oldValue) && NumberUtil.isLong(oldValue)) {
                                if (!sprintMap.containsKey(oldValue)) {
                                    recordFactory.getSprintMapInfo(Long.valueOf(oldValue), sprintMap);
                                }
                                issueHistoryRecordDTO.setOldValue(sprintMap.get(oldValue));
                            }
                            if (StringUtils.isNotEmpty(newValue) && NumberUtil.isLong(oldValue)) {
                                if (!sprintMap.containsKey(newValue)) {
                                    recordFactory.getSprintMapInfo(Long.valueOf(newValue), sprintMap);
                                }
                                issueHistoryRecordDTO.setNewValue(sprintMap.get(newValue));
                            }
                            break;
                        case "重要程度":
                            if (StringUtils.isNotEmpty(oldValue)) {
                                issueHistoryRecordDTO.setOldValue(IssueImportanceEnum.getName(Byte.valueOf(oldValue)));
                            }
                            if (StringUtils.isNotEmpty(newValue)) {
                                issueHistoryRecordDTO.setNewValue(IssueImportanceEnum.getName(Byte.valueOf(newValue)));
                            }
                            break;
                        case "完成情况":
                            if (StringUtils.isNotEmpty(oldValue)) {
                                issueHistoryRecordDTO.setOldValue(IssueCompletionEnum.getName(oldValue));
                            }
                            if (StringUtils.isNotEmpty(newValue)) {
                                issueHistoryRecordDTO.setNewValue(IssueCompletionEnum.getName(newValue));
                            }
                            break;
                        case "系统":
                            if (StringUtils.isNotEmpty(oldValue)) {
                                issueHistoryRecordDTO.setOldValue(recordFactory.getSystemInfo(oldValue));
                            }
                            if (StringUtils.isNotEmpty(newValue)) {
                                issueHistoryRecordDTO.setNewValue(recordFactory.getSystemInfo(newValue));
                            }
                            break;
                        case "模块":
                            if (StringUtils.isNotEmpty(oldValue)) {
                                issueHistoryRecordDTO.setOldValue(recordFactory.getModuleInfos(oldValue));
                            }
                            if (StringUtils.isNotEmpty(newValue)) {
                                issueHistoryRecordDTO.setNewValue(recordFactory.getModuleInfos(newValue));
                            }
                            break;
                        case "处理人":
                        case "测试负责人":
                        case "开发负责人":
                            if (StringUtils.isNotEmpty(oldValue) && NumberUtil.isLong(oldValue)) {
                                SsoUser ssoUserDTO = iFacadeUserApi.queryUserById(Long.valueOf(oldValue));
                                if (Optional.ofNullable(ssoUserDTO).isPresent()) {
                                    issueHistoryRecordDTO.setOldValue(ssoUserDTO.getUserName());
                                }
                            }
                            if (StringUtils.isNotEmpty(newValue) && NumberUtil.isLong(oldValue)) {
                                SsoUser ssoUserDTO = iFacadeUserApi.queryUserById(Long.valueOf(newValue));
                                if (Optional.ofNullable(ssoUserDTO).isPresent()) {
                                    issueHistoryRecordDTO.setNewValue(ssoUserDTO.getUserName());
                                }
                            }
                            break;
                        case "是否归档":
                            if (StringUtils.isNotEmpty(oldValue)) {
                            issueHistoryRecordDTO.setOldValue(IsAchiveEnum.getName(Byte.valueOf(oldValue)));
                        }
                            if (StringUtils.isNotEmpty(newValue)) {
                                issueHistoryRecordDTO.setNewValue(IsAchiveEnum.getName(Byte.valueOf(newValue)));
                            }
                            break;
                        default:
                            break;
                    }
                });
            }
            return ControllerResponse.success(new PageInfo<>(issueHistoryRecords));
        } catch (Exception e) {
            loggr.error("工作项查询操作历史失败:{}", e);
            return ControllerResponse.fail("查询操作历史失败");
        }

    }

    @Override
    public ControllerResponse getRecordRichText(Long recordId) {
        return ControllerResponse.success(issueHistoryRecordMapper.selectByPrimaryKey(recordId));
    }

    @Override
    public List<IssueDTO> listRelation(Long issueId, Byte issueType) {
        Long projectId = issueFactory.getSystemIdByIssueId(issueId);
        List<IssueDTO> issueDTOList = issueMapper.listRelation(issueId, projectId);
        List<Long> handlers = new ArrayList<>();
        for (IssueDTO issueDTO : issueDTOList) {
            if (null != issueDTO.getHandler()) {
                handlers.add(issueDTO.getHandler());
            }
            setParentIssue(issueType, issueDTO);
            Long teamId = issueDTO.getTeamId();
            if (null != teamId) {
                Map<Long,String> mapStage = iStageService.getStageMapByTeamId(teamId);
                Long stageId = issueDTO.getStageId();
                Long laneId = issueDTO.getLaneId();
                if(null != stageId){
                    String stageName = mapStage.get(stageId);
                    if(null != laneId){
                        issueDTO.setStageName(stageName + "/" + mapStage.get(laneId));
                    }else{
                        issueDTO.setStageName(stageName);
                    }
                }
            }else {
                if(IssueTypeEnum.TYPE_EPIC.CODE.equals(issueDTO.getIssueType())){
                    //通过枚举获取
                    Long stageId = issueDTO.getStageId();
                    String firstStageName = StageConstant.FirstStageEnum.getFirstStageName(stageId);
                    issueDTO.setStageName(firstStageName);
                }
            }
        }
        issueFactory.setHandlerName(handlers, issueDTOList);
        return issueDTOList;
    }

    private void setParentIssue(Byte issueType, IssueDTO issueDTO) {
        switch (IssueTypeEnum.getByCode(issueType)) {
            case TYPE_FEATURE:
                if (IssueTypeEnum.TYPE_EPIC.CODE.equals(issueDTO.getIssueType())) {
                    issueDTO.setParent(true);
                }
                break;
            case TYPE_STORY:
                if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueDTO.getIssueType())) {
                    issueDTO.setParent(true);
                }
                break;
            case TYPE_TASK:
                if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueDTO.getIssueType())) {
                    issueDTO.setParent(true);
                }
                break;
            case TYPE_FAULT:
                if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueDTO.getIssueType())) {
                    issueDTO.setParent(true);
                }
                break;
            default:
                break;
        }
    }

    public Map getFirstStageMapByTypeAndId(Long stageId) {
        Map map = new HashMap<String, String>();
        map.put("name", StageConstant.FirstStageEnum.getFirstStageName(stageId));
        map.put("id", stageId);
        return map;
    }

    public Map getSecondStageMapByTypeAndId(Long stageId, Byte issueType, Long firstStageId) {
        Map map = new HashMap<String, String>();
        if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType)) {
            map.put("name", TaskStatusEnum.getName(stageId));
            map.put("id", stageId);
        }
        if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            map.put("name", StoryStatusEnum.getName(stageId));
            map.put("id", stageId);
        }
        if (IssueTypeEnum.TYPE_FAULT.CODE.equals(issueType)) {
            map.put("name", FaultStatusEnum.getMsg(stageId));
            map.put("id", stageId);
        } else {
            List<StageInstance> instanceList = iStageService.getSecondStageListByParentId(firstStageId);
            for (int i = 0; i < instanceList.size(); i++) {
                KanbanStageInstance kanbanStageInstance = instanceList.get(i);
                if (kanbanStageInstance.getStageId().equals(stageId)) {
                    map.put("name", kanbanStageInstance.getStageName());
                    map.put("id", stageId);
                    break;
                }
            }
        }
        return map;
    }

    @Override
    public List<String> getTemplateParentIssueList(Long projectId, Byte issueType) {
        List<String> result = Lists.newArrayList();
        if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueType) || IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueType)) {
                issueType = IssueTypeEnum.TYPE_EPIC.CODE;
            } else {
                issueType = IssueTypeEnum.TYPE_FEATURE.CODE;
            }
            //List<Issue> issues = issueMapper.getTemplateParentIssueList(projectId, issueType);
            List<Issue> issues = issueMapper.getIssueTemplateParentList(projectId, issueType);
            if (CollectionUtils.isNotEmpty(issues)) {
                for (Issue issue : issues) {
                    Long issueId = issue.getIssueId();
                    String name = issue.getTitle();
                    String split = issueId + "+" + name;
                    result.add(split);
                }
            }
        }
        return result;
    }

    @Override
    public List<Issue> queryIssueList(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        IssueStringDTO issueStringDTO = JSON.parseObject(jsonObject.toJSONString(), IssueStringDTO.class);
        IssueRecord issueRecord = new IssueRecord();
        Long systemId = null;
        if (map.containsKey("systemId") && Optional.ofNullable(map.get("systemId")).isPresent()) {
            systemId = Long.parseLong(map.get("systemId").toString());
        }
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        if(StringUtils.isNotEmpty(tenantCode)){
            issueRecord.setTenantCode(tenantCode);
        }
        //组织查询自定义字段条件
        List<CustomFieldJsonType> customFieldJsonTypeList = getCustomIssueIds(map, systemId);
        //扩展字段，先确定是否有扩展字段，再过滤出满足条件的issueId
        if (!checkAndGetIssueIdByExtendFieldParam(issueRecord, map)) {
            return Lists.newArrayList();
        }
        // 不传page信息时查全部数据
        if (StringUtils.isNotEmpty(issueStringDTO.getIsCollect())) {
            Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
            List<Long> issueIds = getIsCollectByUserId(userId);
            if (CollectionUtils.isNotEmpty(issueIds)) {
                if(CollectionUtils.isNotEmpty(issueRecord.getIssueIds())){
                    issueRecord.getIssueIds().retainAll(issueIds);
                }else {
                    issueRecord.setIssueIds(issueIds);
                }
            } else {
                return Lists.newArrayList();
            }
        }
        //组织基本查询条件
        orgIssueBasicParams(issueRecord,issueStringDTO);
        return issueMapper.queryIssueList(issueRecord, customFieldJsonTypeList);
    }

    /**
     * 功能描述 封装基本字段的高级搜索条件
     *
     * @param string
     * @param type
     * @return com.yusys.agile.issue.domain.FieldJsonType
     * @date 2021/2/16
     */
    public FieldJsonType dealData(String string, String type) {
        FieldJsonType fieldJsonType = new FieldJsonType();
        List<String> list = Lists.newArrayList(string.split(","));

        if (CollectionUtils.isNotEmpty(list)) {
            if (list.contains("null")) {
                list.remove("null");
                fieldJsonType.setQueryType(3);
                if (list.size() > 0) {
                    fieldJsonType.setQueryType(1);
                }
            } else {
                fieldJsonType.setQueryType(2);
            }
            if (!fieldJsonType.getQueryType().equals(3)) {
                if (type.equals(BYTE)) {
                    fieldJsonType.setDataByte(StringUtil.stringToByteList(list));
                }
                if (type.equals(INTEGER)) {
                    fieldJsonType.setDataInteger(StringUtil.stringToIntegerList(list));
                }
                if (type.equals(LONG)) {
                    fieldJsonType.setDataLong(StringUtil.stringToLongrList(list));
                }
                if (type.equals(STRING)) {
                    fieldJsonType.setDataString(list);
                }
                if (type.equals(DATE)) {
                    fieldJsonType.setDateBegin(new Date(Long.parseLong(list.get(0))));
                    fieldJsonType.setDateEnd(new Date(Long.parseLong(list.get(1))));
                }
            }
        }
        return fieldJsonType;
    }

    /**
     * 功能描述  查询当前用户关注的工作项id
     *
     * @param userId
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/17
     */
    public List<Long> getIsCollectByUserId(Long userId) {
        UserAttentionExample userAttentionExample = new UserAttentionExample();
        userAttentionExample.createCriteria()
                .andStateEqualTo(StateEnum.U.toString())
                .andAttentionTypeEqualTo(ATTENIONTYPE_1)
                .andUserIdEqualTo(userId);
        return userAttentionMapper.selectIssueIdByExample(userAttentionExample);
    }

    @Override
    public PageInfo countIssueByStageId(Long projectId, Integer pageNum, Integer pageSize) {
        Page page = new Page(pageNum, pageSize);
        List<com.yusys.agile.issue.dto.IssueStageIdCountDTO> issueStageIdCountDTOS = new ArrayList<>();
        List<SsoSystem> ssoSystems = iFacadeSystemApi.querySystemsByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(ssoSystems)) {
            for (SsoSystem ssoSystem : ssoSystems) {
                //获取系统下的子工作项，最子到故事，没有子就统计父需求
                com.yusys.agile.issue.dto.IssueStageIdCountDTO stageIdCountDTO = new com.yusys.agile.issue.dto.IssueStageIdCountDTO();
                List<com.yusys.agile.issue.dto.IssueDTO> storyDTOS = issueMapper.selectBySystemId(projectId, ssoSystem.getSystemId(), IssueTypeEnum.TYPE_STORY.CODE);
                countStageId(projectId, ssoSystem, stageIdCountDTO, storyDTOS);
                if (CollectionUtils.isEmpty(storyDTOS)) {
                    List<com.yusys.agile.issue.dto.IssueDTO> featureDTOS = issueMapper.selectBySystemId(projectId, ssoSystem.getSystemId(), IssueTypeEnum.TYPE_FEATURE.CODE);
                    countStageId(projectId, ssoSystem, stageIdCountDTO, featureDTOS);
                    if (CollectionUtils.isEmpty(featureDTOS)) {
                        List<com.yusys.agile.issue.dto.IssueDTO> epicDTOS = issueMapper.selectBySystemId(projectId, ssoSystem.getSystemId(), IssueTypeEnum.TYPE_EPIC.CODE);
                        countStageId(projectId, ssoSystem, stageIdCountDTO, epicDTOS);
                        if (CollectionUtils.isEmpty(epicDTOS)) {
                            setStageIdCountDTO(projectId, ssoSystem, stageIdCountDTO, 0, 0, 0, 0, 0, 0, 0);
                        }
                    }
                }
                issueStageIdCountDTOS.add(stageIdCountDTO);
            }
        }
        return getPageInfo(pageNum, pageSize, page, issueStageIdCountDTOS);
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param page
     * @param issueStageIdCountDTOS
     * @Date 2021/2/10
     * @Description 为处理好的list增加分页
     * @Return com.github.pagehelper.PageInfo
     */
    private PageInfo getPageInfo(Integer pageNum, Integer pageSize, Page page, List<IssueStageIdCountDTO> issueStageIdCountDTOS) {
        //为Page类中的total属性赋值
        int total = issueStageIdCountDTOS.size();
        page.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(issueStageIdCountDTOS.subList(startIndex, endIndex));
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    @Override
    public DemandPlanDTO getDemandPlans(String title, String stages, SecurityDTO securityDTO) {
        DemandPlanDTO planDTO = new DemandPlanDTO();
        Long projectId = securityDTO.getProjectId();
        try {
            IssueExample epicExample = new IssueExample();
            IssueExample.Criteria criteria = epicExample.createCriteria();
            criteria.andProjectIdEqualTo(projectId)
                    .andStateEqualTo(StateEnum.U.toString())
                    .andIssueTypeEqualTo(IssueTypeEnum.TYPE_EPIC.CODE).andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
            if (StringUtils.isNotEmpty(title)) {
                criteria.andTitleLike(PERCENT_SIGN + title + PERCENT_SIGN);
            }
            if (Optional.ofNullable(stages).isPresent() && !"".equals(stages)) {
                String[] split = stages.split(",");
                Long stageId = Long.parseLong(split[0]);
                criteria.andStageIdEqualTo(stageId);
                if (split.length > 1) {
                    Long laneId = Long.parseLong(split[1]);
                    criteria.andLaneIdEqualTo(laneId);
                }
            }
            epicExample.setOrderByClause(CREATE_TIME_DESC);
            List<Issue> epics = issueMapper.selectByExample(epicExample);

            if (CollectionUtils.isEmpty(epics)) {
                planDTO.setEpics(Collections.emptyList());
                planDTO.setFeatures(Collections.emptyList());
                planDTO.setStorys(Collections.emptyList());
                return planDTO;
            }

            List<Long> epicIssueIds = new ArrayList<>();
            List<Long> featureIds = new ArrayList<>();

            epics.forEach(epic -> {
                epicIssueIds.add(epic.getIssueId());
            });
            List<IssueDTO> features = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(epicIssueIds)) {
                IssueExample featureExample = new IssueExample();
                featureExample.createCriteria().andProjectIdEqualTo(projectId)
                        .andStateEqualTo(StateEnum.U.toString())
                        .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FEATURE.CODE)
                        .andParentIdIn(epicIssueIds).andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
                featureExample.setOrderByClause(CREATE_TIME_DESC);
                List<Issue> featureIssues = issueMapper.selectByExample(featureExample);
                if (CollectionUtils.isNotEmpty(featureIssues)) {
                    features = ReflectUtil.copyProperties4List(featureIssues, IssueDTO.class);
                    featureIssues.forEach(feature -> {
                        featureIds.add(feature.getIssueId());
                    });
                }
            }
            List<IssueDTO> storys = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(featureIds)) {
                IssueExample storysExample = new IssueExample();
                storysExample.createCriteria().andProjectIdEqualTo(projectId)
                        .andStateEqualTo(StateEnum.U.toString())
                        .andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE)
                        .andParentIdIn(featureIds).andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
                storysExample.setOrderByClause(CREATE_TIME_DESC);
                List<Issue> issueList = issueMapper.selectByExample(storysExample);
                storys = ReflectUtil.copyProperties4List(issueList, IssueDTO.class);
            }
            planDTO.setEpics(ReflectUtil.copyProperties4List(epics, IssueDTO.class));
            planDTO.setFeatures(features);
            planDTO.setStorys(storys);
            return planDTO;
        } catch (Exception e) {
            loggr.error("获取需求规划列表异常：{}", e);
            throw new BusinessException("获取需求规划列表异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dragDemand(Long issueId, Long sprintId, Long parentId, Long projectId) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);

        Optional.ofNullable(issue).orElseThrow(() -> new BusinessException("工作项不存在!"));
        if (StateEnum.E.toString().equals(issue.getState())) {
            throw new BusinessException("工作项不存在!");
        }
        issue.setSprintId(sprintId);
        issue.setParentId(parentId);
        Byte issueType = issue.getIssueType();

        // 评审拦截
        if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType) && null != issue.getSprintId()) {
            StoryCheckResultDTO storyCheckResultDTO = reviewService.allowStoryInSprint(issue.getIssueId());
            if (null != storyCheckResultDTO && !storyCheckResultDTO.getHasPassed()) {
                loggr.info("由于未通过评审，拖拽故事关联迭代失败！storyId = {}", issue.getIssueId());
                throw new BusinessException(storyCheckResultDTO.getMsg());
            }
        }


        issueMapper.updateByPrimaryKey(issue);
        // 拖拽故事
        if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            IssueExample example = new IssueExample();
            example.createCriteria().andParentIdEqualTo(issueId).andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
            List<Issue> issues = issueMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(issues)) {
//                issues.forEach(issueTask->{
//                    issueTask.setSprintId(sprintId);
//                    issueMapper.updateByPrimaryKey(issueTask);
//                });
                for (Issue temp : issues) {
                    temp.setSprintId(sprintId);
                    issueMapper.updateByPrimaryKey(temp);

                }
            }
        }
    }

    /**
     * @param projectId
     * @param ssoSystem
     * @param stageIdCountDTO
     * @param i，i2，i3，i4，i5，i5，i7
     * @Date 2021/2/22
     * @Description 各阶段个数赋值
     * @Return void
     */
    private void setStageIdCountDTO(Long projectId, SsoSystem ssoSystem, com.yusys.agile.issue.dto.IssueStageIdCountDTO stageIdCountDTO,
                                    int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        stageIdCountDTO.setProjectId(projectId);
        stageIdCountDTO.setSystemId(ssoSystem.getSystemId());
        stageIdCountDTO.setSystemName(ssoSystem.getSystemName());
        stageIdCountDTO.setReadyStageNum(i);
        stageIdCountDTO.setAnalysisStageNum(i2);
        stageIdCountDTO.setDesignStageNum(i3);
        stageIdCountDTO.setDevelopStageNum(i4);
        stageIdCountDTO.setTestStageNum(i5);
        stageIdCountDTO.setOnlineStageNum(i6);
        stageIdCountDTO.setFinishStageNum(i7);
    }

    /**
     * @param projectId
     * @param ssoSystem
     * @param stageIdCountDTO
     * @param issueDTOS
     * @Date 2021/2/22
     * @Description 统计需求各个阶段的个数
     * @Return voido.
     */
    private void countStageId(Long projectId, SsoSystem ssoSystem, IssueStageIdCountDTO stageIdCountDTO, List<com.yusys.agile.issue.dto.IssueDTO> issueDTOS) {
        if (CollectionUtils.isNotEmpty(issueDTOS)) {
            int readyStageNum = 0;
            int analysisStageNum = 0;
            int designStageNum = 0;
            int developStageNum = 0;
            int testStageNum = 0;
            int onlineStageNum = 0;
            int finishStageNum = 0;
            for (IssueDTO issueDTO : issueDTOS) {
                if (null != issueDTO.getStageId()) {
                    if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.READY_STAGE.getValue())) {
                        readyStageNum++;
                    } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.ANALYSIS_STAGE.getValue())) {
                        analysisStageNum++;
                    } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.DESIGN_STAGE.getValue())) {
                        designStageNum++;
                    } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue())) {
                        developStageNum++;
                    } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.TEST_STAGE.getValue())) {
                        testStageNum++;
                    } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.ONLINE_STAGE.getValue())) {
                        onlineStageNum++;
                    } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.FINISH_STAGE.getValue())) {
                        finishStageNum++;
                    }
                    setStageIdCountDTO(projectId, ssoSystem, stageIdCountDTO, readyStageNum, analysisStageNum, designStageNum, developStageNum, testStageNum, onlineStageNum, finishStageNum);
                }
            }
        }
    }

    @Override
    public long getProjectMemberTaskTotal(CommitDTO commitDTO) {
        IssueExample issueExample = getCommitQueryObject(commitDTO);
        return issueMapper.countByExample(issueExample);
    }

    @Override
    public List<Issue> getProjectMemberTaskList(CommitDTO commitDTO) {
        IssueExample issueExample = getCommitQueryObject(commitDTO);
        List<Issue> tasks = issueMapper.selectByExample(issueExample);
        return tasks;
    }

    @Override
    public List<Issue> getProjectMemberTaskList(CommitDTO commitDTO, long startIndex, long pageSize) {
        List<Issue> tasks = issueMapper.getProjectMemberTaskList(commitDTO.getProjectId(), commitDTO.getSprintId(), commitDTO.getMemberIdList(), startIndex, pageSize);
        return tasks;
    }

    private IssueExample getCommitQueryObject(CommitDTO commitDTO) {
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andProjectIdEqualTo(commitDTO.getProjectId())
                //.andIssueTypeEqualTo(IssueTypeEnum.TYPE_TASK.CODE)
                .andHandlerIn(commitDTO.getMemberIdList())
                .andStateEqualTo(StateEnum.U.getValue()).andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
        Long sprintId = commitDTO.getSprintId();
        if (null != sprintId) {
            criteria.andSprintIdEqualTo(sprintId);
        }
        return issueExample;
    }

    public List<CustomFieldJsonType> getCustomIssueIds(Map<String, Object> map, Long systemId) {
        List<HeaderField> headerFields = headerFieldService.getAllCustomHeaderFieldBySystemId(systemId);
        Map<String, List<HeaderField>> mapHeaderField = headerFields.stream().collect(Collectors.groupingBy(HeaderField -> {
            if (HeaderField.getFieldPoolCode() != null) {
                return HeaderField.getFieldPoolCode();
            } else {
                return HeaderField.getFieldCode();
            }
        }));
        Map<String, String> mapTemp = new HashMap<>();
        Byte issueType = Byte.parseByte(map.get("issueType").toString());
        for (String key : map.keySet()) {
            String[] strings = key.split("-");
            if (strings.length > 1) {
                if (mapHeaderField.containsKey(strings[0])) {
                    List<HeaderField> headerFields1 = mapHeaderField.get(strings[0]);
                    for (HeaderField headerField : headerFields1) {
                        if (headerField.getCategory().equals(issueType)) {
                            mapTemp.put(headerField.getFieldCode() + "-" + strings[1] + "-" + strings[2], map.get(key).toString());
                        }
                    }
                }
            }
        }
        List<CustomFieldJsonType> customFieldJsonTypeList = Lists.newArrayList();
        if (mapTemp.isEmpty()) {
            return customFieldJsonTypeList;
        }
        for (String key : mapTemp.keySet()) {
            String[] strings = key.split("-");
            if (strings.length > 1) {
                CustomFieldJsonType customFieldJsonType = new CustomFieldJsonType();
                customFieldJsonType.setFieldId(Long.parseLong(strings[0]));
                customFieldJsonType.setFieldType(Byte.parseByte(strings[1]));
                customFieldJsonType.setQueryType(Integer.parseInt(strings[2]));
                //  1:查询全部;2:根据value查询;3:查询为null的记录
                if (3 != Integer.parseInt(strings[2])) {
                    List<String> list = Lists.newArrayList(mapTemp.get(key).split(","));
                    if ("0".equals(strings[1]) || "2".equals(strings[1]) || "5".equals(strings[1])) {
                        customFieldJsonType.setNumBegin(Long.parseLong(list.get(0)));
                        customFieldJsonType.setNumEnd(Long.parseLong(list.get(1)));
                    } else {
                        customFieldJsonType.setDataString(list);
                    }
                }
                customFieldJsonTypeList.add(customFieldJsonType);
            }
        }
        return customFieldJsonTypeList;
    }


    /**
     * @param projectId
     * @param sprintId
     * @param queryStr
     * @param pageNumber
     * @param pageSize
     * @return
     * @description 查询迭代下提交任务列表
     */
    @Override
    public PageInfo<List<Issue>> getSprintRelatedCommitTaskList(Long projectId, Long sprintId, String queryStr, Integer pageNumber, Integer pageSize) {
        Assert.notNull(sprintId, "迭代编号不能为空");
        pageNumber = null == pageNumber ? 1 : pageNumber;
        pageSize = null == pageSize ? 20 : pageSize;
        PageHelper.startPage(pageNumber, pageSize);
        IssueExample issueExample = new IssueExample();
        issueExample.setOrderByClause("issue_id desc");
        IssueExample.Criteria criteria = issueExample.createCriteria();
        if (StringUtils.isNotBlank(queryStr)) {
            criteria.andTitleLike("%" + queryStr + "%");
        }
        criteria.andProjectIdEqualTo(projectId)
                .andSprintIdEqualTo(sprintId)
                .andIssueTypeIn(Lists.newArrayList(IssueTypeEnum.TYPE_STORY.CODE, IssueTypeEnum.TYPE_TASK.CODE))
                .andStateEqualTo(StateEnum.U.getValue());
        return new PageInfo(issueMapper.selectByExample(issueExample));
    }

    @Override
    public List<Long> selectIssueIdByProjectId(Long projectId, String title) {
        return issueMapper.selectIssueIdByProjectId(projectId, title);
    }


    /**
     * 功能描述  封装列表查询的基础对象
     *
     * @return java.util.Map
     * @date 2020/8/10
     */
    @Override
    public Map issueMap(String noLogin) {
        String tenantCode= UserThreadLocalUtil.getTenantCode();
        Map<String, Map> mapResult = new HashMap<>();
        //Issue
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria()
                .andStateEqualTo("U");
        List<Issue> issues = issueMapper.selectByExampleWithBLOBs(issueExample);
        Map<Long, List<Issue>> issueMap = issues.stream().collect(Collectors.groupingBy(Issue::getIssueId));
        Map<Long, List<Issue>> issueParentMap = issues.stream().collect(Collectors.groupingBy(Issue -> {
            if (Issue.getParentId() != null) {
                return Issue.getParentId();
            } else {
                return -1L;
            }
        }));
        //  mapResult.put("issueMap", issueMap);
        mapResult.put(ISSUEPARENTMAP, issueParentMap);
        //租户下的人员
        List<SsoUserDTO> userDTOS = iFacadeUserApi.queryUsersByTenantCodeNoPage(tenantCode);

        Map<Long, String> userMap = Maps.newHashMap();
        // 查询所有人员信息
        for (SsoUserDTO ssoUserDTO : userDTOS) {
            String userName = StrUtil.builder().append(ssoUserDTO.getUserName())
                    .append("(").append(ssoUserDTO.getUserAccount()).append(")").toString();
            userMap.put(ssoUserDTO.getUserId(), userName);
        }
        mapResult.put(USERMAP, userMap);
        //查询所有迭代
        List<SSprint> sSprints = sprintv3Service.queryAllSprint();
        Map<Long, List<SSprint>> sprintMap = sSprints.stream().collect(Collectors.groupingBy(SSprint::getSprintId));
        mapResult.put(SPRINTMAP, sprintMap);
        //所有的阶段
        // List<KanbanStageInstance> kanbanStageInstances = stageService.getAllStageInfo(projrctId);
        // Map<Long, List<KanbanStageInstance>> kanbanStageInstanceMap = kanbanStageInstances.stream().collect(Collectors.groupingBy(KanbanStageInstance::getStageId));
        // mapResult.put("kanbanStageInstanceMap", kanbanStageInstanceMap);
        //租户下的模块
        List<Module> moduleList = moduleService.listModule();
        Map<Long, String> moduleMap = Maps.newHashMap();
        for (Module module : moduleList) {
            moduleMap.put(module.getModuleId(), module.getModuleName());
        }
        mapResult.put(MODULEMAP, moduleMap);
        //租户下的系统
        List<SsoSystemRestDTO> ssoSystemList = iFacadeSystemApi.querySystemsByTenantCode(tenantCode);
        Map<Long, List<SsoSystemRestDTO>> mapSsoSystem = ssoSystemList.stream().collect(Collectors.groupingBy(SsoSystemRestDTO::getSystemId));
        mapResult.put(MAPSSOSYSTEM, mapSsoSystem);
        //租户下的所有的团队
        List<STeam> sTeamList = teamv3Service.listTeamByTenantCode(tenantCode);
        Map<Long, List<STeam>> mapSTeam = sTeamList.stream().collect(Collectors.groupingBy(STeam::getTeamId));
        mapResult.put(MAPSTEAM, mapSTeam);
        // 当前用户收藏的工作项
        List<UserAttention> userAttentions = Lists.newArrayList();
        if (StringUtils.isBlank(noLogin)) {
            UserAttentionExample userAttentionExample = new UserAttentionExample();
            userAttentionExample.createCriteria()
                    .andStateEqualTo("U")
                    .andAttentionTypeEqualTo(ATTENIONTYPE_1);

//                    .andUserIdEqualTo(UserThreadLocalUtil.getUserInfo().getUserId());
            userAttentions = userAttentionMapper.selectByExample(userAttentionExample);
        }
        Map<Long, List<UserAttention>> mapUserAttention = userAttentions.stream().collect(Collectors.groupingBy(UserAttention::getSubjectId));
        mapResult.put(MAPUSERATTENTION, mapUserAttention);
        //查询当前租户下的所有自定义字段数据对象
        List<SIssueCustomField> issueCustomFields = issueCustomFieldService.selectIssueIdByTenantCode(tenantCode);
        Map<Long, List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>> mapListIssueCustomField = new HashMap<>();
        List<com.yusys.agile.issue.dto.IssueCustomFieldDTO> issueCustomFieldDTOS = Lists.newArrayList();
        try {
            issueCustomFieldDTOS = ReflectUtil.copyProperties4List(issueCustomFields, com.yusys.agile.issue.dto.IssueCustomFieldDTO.class);
        } catch (Exception e) {
            loggr.error(e.getMessage());
        }
        //租户下所有的自定义字段列头对象
        List<HeaderField> headerFields = headerFieldService.getAllCustomHeaderField(tenantCode);
        Map<String, List<HeaderField>> mapHeaderField = headerFields.stream().collect(Collectors.groupingBy(HeaderField::getFieldCode));
        mapResult.put("mapHeaderField", mapHeaderField);
        //自定义字段
        issueCustomFieldDTOS.forEach(IssueCustomFieldDTO -> {
            if (mapHeaderField.containsKey(IssueCustomFieldDTO.getFieldId().toString())) {
                IssueCustomFieldDTO.setFieldType(mapHeaderField.get(IssueCustomFieldDTO.getFieldId().toString()).get(0).getFieldType());
                IssueCustomFieldDTO.setFieldContent(mapHeaderField.get(IssueCustomFieldDTO.getFieldId().toString()).get(0).getFieldContent());
                IssueCustomFieldDTO.setFieldPoolCode(mapHeaderField.get(IssueCustomFieldDTO.getFieldId().toString()).get(0).getFieldPoolCode());
                //IssueCustomFieldDTO.setFieldCode(mapHeaderField.get(IssueCustomFieldDTO.getFieldId().toString()).get(0).getFieldCode());
                IssueCustomFieldDTO.setCategory(mapHeaderField.get(IssueCustomFieldDTO.getFieldId().toString()).get(0).getCategory().toString());
                IssueCustomFieldDTO.setFieldCode(IssueCustomFieldDTO.getFieldPoolCode());
            }
        });
        mapListIssueCustomField = issueCustomFieldDTOS.stream().collect(Collectors.groupingBy(com.yusys.agile.issue.dto.IssueCustomFieldDTO::getIssueId));
        mapResult.put(MAPLISTISSUECUSTOMFIELD, mapListIssueCustomField);
        //列头数据的Content
        Map<String, HashMap<String, String>> mapHeaderFieldContent = headerFieldService.getAllHeaderFieldContNotNull();
        mapResult.put(MAPHEADERFIELDCONTENT, mapHeaderFieldContent);
        //工作项关联的产品
        Set<Long> issueId = issueMap.keySet();

        List<IssueSystemRelp> list = issueSystemRelpService.listIssueSystemRelpByProjectId(Lists.newArrayList(issueId));
        Map<Long, List<IssueSystemRelp>> mapIssueSystemRelp = list.stream().collect(Collectors.groupingBy(IssueSystemRelp::getIssueId));
        mapResult.put("mapIssueSystemRelp", mapIssueSystemRelp);
        //工作项的扩展字段
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getIssueExtendDetailList(Lists.newArrayList(issueId));
        Map<Long, List<SysExtendFieldDetail>> mapSysExtendFieldDetail = sysExtendFieldDetailList.stream().collect(Collectors.groupingBy(SysExtendFieldDetail::getIssueId));
        mapResult.put("mapSysExtendFieldDetail", mapSysExtendFieldDetail);
        return mapResult;

    }

    /**
     * @param issueId
     * @param type
     * @return
     * @description 根据工作项id和类型查询工作项信息
     * @date 2021/3/18
     */
    @Override
    public Issue selectIssueInfo(Long issueId, int type) {
        Issue issue = issueMapper.selectIssueInfo(issueId, type);
        return issue;
    }

    /**
     * 功能描述: 根据版本id和项目id获取工作项目id
     *
     * @param issueStringDTO
     * @param projectId
     * @return java.util.List<java.lang.Long>
     * @date 2021/3/18
     */
    public List<Long> queryVersionIssueRelatList(com.yusys.agile.issue.dto.IssueStringDTO issueStringDTO, Long projectId) {
        List<Long> listIssuesId = Lists.newArrayList();
        List<VersionIssueRelate> versionIssueRelateList = versionIssueRelateService.queryVersionIssueRelatList(issueStringDTO, projectId);
        versionIssueRelateList.forEach(versionIssueRelate -> {
            listIssuesId.add(versionIssueRelate.getIssueId());
        });
        return listIssuesId;
    }


    /**
     * 功能描述: 根据缺陷状态编码和项目id获取工作项目id
     *
     * @param issueStringDTO
     * @param projectId
     * @return java.util.List<java.lang.Long>
     * @date 2021/3/23
     */
    public List<Long> queryIssueByFaultStatusList(com.yusys.agile.issue.dto.IssueStringDTO issueStringDTO, Long projectId) {
        List<Long> listIssuesId = Lists.newArrayList();

        List<Long> faultStatusList = Lists.newArrayList();
        boolean b = false;
        if (StringUtils.isNotEmpty(issueStringDTO.getFaultStatus())) {
            String[] faultStatusStrs = issueStringDTO.getFaultStatus().split(",");
            b = StringUtil.contains(faultStatusStrs, "null");
            faultStatusList = StringUtil.removeNULL("null", faultStatusStrs).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        }

        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        if (faultStatusList != null && faultStatusList.size() > 0) {
            criteria.andStageIdIn(faultStatusList);
            if (b) {
                IssueExample.Criteria criteria1 = issueExample.createCriteria();
                criteria1.andStageIdIsNull();
                issueExample.or(criteria1);
            }
        } else {
            if (b) {
                criteria.andStageIdIsNull();
            }
        }

        List<Issue> issueList = issueMapper.selectByExample(issueExample);
        issueList.forEach(issue -> {
            listIssuesId.add(issue.getIssueId());
        });
        return listIssuesId;
    }

    /**
     * @param parentId
     * @return
     * @description 查询子工作项
     * @date 2020/10/02
     */
    private List<Issue> getChildIssueList(Long parentId) {
        IssueExample example = new IssueExample();
        example.createCriteria()
                .andParentIdEqualTo(parentId)
                .andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issueList = issueMapper.selectByExample(example);
        return issueList;
    }

    /**
     * @param parentId
     * @return
     * @description 查询子工作项状态
     */
    @Override
    public Map<Boolean, List<Long>> getChildIssueWaitOnlineState(Long parentId) {
        Map<Boolean, List<Long>> resultMap = null;
        List<Issue> issueList = getChildIssueList(parentId);
        if (CollectionUtils.isNotEmpty(issueList)) {
            int count = 0;
            List<Long> issueIds = Lists.newArrayList();
            for (Issue issue : issueList) {
                Long stageId = issue.getStageId();
                Long laneId = issue.getLaneId();
                if (StageConstant.FirstStageEnum.ONLINE_STAGE.getValue().equals(stageId) && ("104".equals(String.valueOf(laneId)) || "105".equals(String.valueOf(laneId)))) {
                    issueIds.add(issue.getIssueId());
                    ++count;
                }
            }
            resultMap = Maps.newHashMap();
            int size = issueList.size();
            if (size == count) {
                resultMap.put(Boolean.TRUE, issueIds);
            }
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateIssueLaunchState(Long issueId, Byte issueType) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        if (null == issue) {
            throw new RuntimeException("工作项:[" + issueId + "]不存在");
        }
        List<Issue> issueHistoryList = Lists.newArrayList();
        issueHistoryList.add(issue);
        if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueType)) {
            List<Long> featureIds = Lists.newArrayList(issueId);
            loggr.info("更新分支类型工作项已上线状态研发需求集合:{}", featureIds);
            //更新分支和分支下的故事状态
            List<Issue> storyList = getChildIssueList(issueId);
            issueHistoryList.addAll(storyList);
            List<Long> storyIds = assembleIssueIdList(storyList);
            loggr.info("更新分支类型工作项已上线状态故事集合:{}", storyIds);
            if (CollectionUtils.isNotEmpty(storyIds)) {
                featureIds.addAll(storyIds);
            }
            int row = issueMapper.batchUpdateIssueStageStatus(featureIds, StageConstant.FirstStageEnum.FINISH_STAGE.getValue(), null);
            loggr.info("更新分支类型工作项已上线状态工作项集合:{},更新记录条数:{}", featureIds, row);
            featureIds.clear();
        } else if (IssueTypeEnum.TYPE_EPIC.CODE.equals(issueType)) {
            List<Long> epicIds = Lists.newArrayList(issueId);
            loggr.info("更新需求类型工作项已上线状态业务需求集合:{}", epicIds);
            //更新需求实际上线日期开始
            String actualOnlineTime = null;
            VersionManagerDTO versionManagerDTO = versionManagerService.queryVersionManageInfo(issueId);
            if (null != versionManagerDTO) {
                Date planReleaseDate = versionManagerDTO.getPlanReleaseDate();
                if (null != planReleaseDate) {
                    actualOnlineTime = DateTools.parseToString(planReleaseDate, DateTools.YYYY_MM_DD);
                }
            }
            sysExtendFieldDetailService.batchUpdateEpicActualOnlineTime(epicIds, actualOnlineTime);
            //更新需求实际上线日期结束
            List<Issue> featureList = getChildIssueList(issueId);
            if (CollectionUtils.isNotEmpty(featureList)) {
                issueHistoryList.addAll(featureList);
                List<Long> featureIds = Lists.newArrayList();
                for (Issue feature : featureList) {
                    Long featureIssueId = feature.getIssueId();
                    featureIds.add(featureIssueId);
                }
                loggr.info("更新需求类型工作项已上线状态研发需求集合:{}", featureIds);
                IssueExample example = new IssueExample();
                example.createCriteria().andParentIdIn(featureIds).andStateEqualTo(StateEnum.U.getValue());
                List<Issue> storyList = issueMapper.selectByExample(example);
                List<Long> storyIds = assembleIssueIdList(storyList);
                if (CollectionUtils.isNotEmpty(storyIds)) {
                    issueHistoryList.addAll(storyList);
                    epicIds.addAll(storyIds);
                    loggr.info("更新需求类型工作项已上线状态故事集合:{}", storyIds);
                }
                epicIds.addAll(featureIds);
                featureIds.clear();
            }
            int row = issueMapper.batchUpdateIssueStageStatus(epicIds, StageConstant.FirstStageEnum.FINISH_STAGE.getValue(), null);
            loggr.info("更新需求类型工作项已上线工作项集合:{},更新记录条数:{}", epicIds, row);
            epicIds.clear();
        }
        dealStateHistory(issueHistoryList);
    }

    /**
     * @Date 2020/10/21
     * @Description 根据登入用户获取代办事项
     * @Return PageInfo
     */
    @Override
    public PageInfo getIssueCommissionByHandler(Integer pageNum, Integer pageSize) {

        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        //获取登入用户id
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        String tenantCode = UserThreadLocalUtil.getUserInfo().getTenantCode();

        List<Long> subjectIds = reqUserRlatService.listReqUserRlatByUserId(userId, tenantCode);
        List<Long> issueIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(subjectIds)) {
            issueIds = reviewService.listByListReviewId(subjectIds, tenantCode);
        }

        loggr.info("工作台待办当前登陆的租户tenantCode={},userId={}", tenantCode, userId);
        //拼接不属于代办的条件
        List<Long> stageIdList = Lists.newArrayList();
        stageIdList.add(StageConstant.FirstStageEnum.FINISH_STAGE.getValue());
        stageIdList.add(TaskStatusEnum.TYPE_CLOSED_STATE.CODE);
        stageIdList.add(FaultStatusEnum.CLOSED.CODE);

        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andHandlerEqualTo(userId)
                .andStateEqualTo(StateEnum.U.getValue()).andStageIdNotIn(stageIdList)
                .andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);

        if (CollectionUtils.isNotEmpty(issueIds)) {
            IssueExample.Criteria criteria1 = example.createCriteria();
            criteria1.andIssueIdIn(issueIds).andStateEqualTo(StateEnum.U.getValue());
            example.or(criteria1);
        }
        // 排序
        example.setOrderByClause("update_time desc");
        List<Issue> issueList = issueMapper.selectByExample(example);
        List<com.yusys.agile.issue.dto.IssueListDTO> issueListDTOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(issueList)) {
            Map<String, Map> mapResult = setIssueTypeEnumName(issueList);
            if (issueList != null && !issueList.isEmpty()) {


                for (Issue issue : issueList) {


                    com.yusys.agile.issue.dto.IssueListDTO issueListDTOResult = ReflectObjectUtil.copyProperties(issue, com.yusys.agile.issue.dto.IssueListDTO.class);
                    getIssueTypeEnumName(issue, issueListDTOResult, mapResult);
                    issueListDTOS.add(issueListDTOResult);
                }
            }
        }

        PageInfo pageInfo = new PageInfo<>();
        pageInfo.setTotal(issueListDTOS.size());
        pageInfo.setSize(issueListDTOS.size());
        List<com.yusys.agile.issue.dto.IssueListDTO> returnList = startPage(issueListDTOS, pageNum, pageSize);
        pageInfo.setList(returnList);
        return pageInfo;
    }

    @Override
    public PanoramasEpicDTO getIssuePanoramas(String issueId, String planDeployDate, String noLogin) throws Exception {
        return null;
    }


    /**
     * 人工获取分页数据
     *
     * @param list     要分页的集合
     * @param pageNum  页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static List startPage(List list, Integer pageNum,
                                 Integer pageSize) {
        if (org.springframework.util.CollectionUtils.isEmpty(list)) {
            return null;
        }
        //  不进行分页
        if (pageNum == null || pageSize == null) {
            return list;
        }

        // 记录总数
        Integer count = list.size();
        // 页数
        Integer pageCount = 0;

        // 取得数量超过了，直接返回空
        if (count < pageSize * (pageNum - 1)) {
            return new ArrayList();
        }

        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        // 开始索引
        int fromIndex = 0;
        // 结束索引
        int toIndex = 0;

        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List pageList = list.subList(fromIndex, toIndex);

        return pageList;
    }


    private List<com.yusys.agile.issue.dto.PanoramasFeatureDTO> generateFeaturePanoramas(com.yusys.agile.issue.dto.IssueListDTO issueListDTO, List<com.yusys.agile.issue.dto.IssueListDTO> featureDTOList) {
        List<com.yusys.agile.issue.dto.PanoramasFeatureDTO> panoramasFeatureDTOList = Lists.newArrayList();
        com.yusys.agile.issue.dto.PanoramasFeatureDTO panoramasFeatureDTO;
        for (com.yusys.agile.issue.dto.IssueListDTO feature : featureDTOList) {
            panoramasFeatureDTO = new com.yusys.agile.issue.dto.PanoramasFeatureDTO();
            generatePanoramasFeature(panoramasFeatureDTO, feature);

            List<com.yusys.agile.issue.dto.PanoramasStoryDTO> panoramasStoryDTOList = Lists.newArrayList();
            List<com.yusys.agile.issue.dto.IssueListDTO> storyDTOList = feature.getChildren();
            generatePanoramasStory(panoramasStoryDTOList, storyDTOList);
            panoramasFeatureDTO.setStories(panoramasStoryDTOList);

            panoramasFeatureDTOList.add(panoramasFeatureDTO);
        }
        return panoramasFeatureDTOList;
    }

    private void generatePanoramasStory(List<com.yusys.agile.issue.dto.PanoramasStoryDTO> panoramasStoryDTOList, List<com.yusys.agile.issue.dto.IssueListDTO> storyDTOList) {
        com.yusys.agile.issue.dto.PanoramasStoryDTO panoramasStoryDTO;
        for (com.yusys.agile.issue.dto.IssueListDTO story : storyDTOList) {
            //过滤掉缺陷类故事
            if (story.getTitle().indexOf("缺陷类故事") != -1) {
                continue;
            }

            panoramasStoryDTO = new com.yusys.agile.issue.dto.PanoramasStoryDTO();
            panoramasStoryDTO.setIssueId(story.getIssueId());
            //由于改了IssueListDTO里的moduleId类型，将long改成Map了，为满足高级查询展示模块名称
            Issue issue = issueMapper.selectByPrimaryKey(story.getIssueId());
            Long moduleId = issue.getModuleId();
            Module issueModule = moduleService.detailModule(moduleId);
            if (Optional.ofNullable(issueModule).isPresent()) {
                panoramasStoryDTO.setModuleName(issueModule.getModuleName());
            } else {
                panoramasStoryDTO.setModuleName("");
            }

            SysExtendFieldDetail sysExtendFieldDetail = sysExtendFieldDetailService.getSysExtendFieldDetail(story.getIssueId(), "serverAnalystManager");
            String serverAnalystManager = getName(sysExtendFieldDetail);
            panoramasStoryDTO.setServerAnalystManager(serverAnalystManager);

            panoramasStoryDTO.setTitle(story.getTitle());
            panoramasStoryDTO.setHandler(getHandlerName(story.getHandler()));

            List<com.yusys.agile.issue.dto.IssueListDTO> taskList = story.getChildren();
            List<com.yusys.agile.issue.dto.PanoramasTaskDTO> panoramasTaskDTOList = Lists.newArrayList();
            generateTaskPanoramas(panoramasTaskDTOList, taskList);
            panoramasStoryDTO.setDevTasks(panoramasTaskDTOList);

            List<com.yusys.agile.issue.dto.PanoramasTaskDTO> panoramasFunTestTaskDTOList = Lists.newArrayList();
            com.yusys.agile.issue.dto.PanoramasTaskDTO panoramasTaskDTO = new com.yusys.agile.issue.dto.PanoramasTaskDTO();
            SysExtendFieldDetail sysExtendFieldDetailTester = sysExtendFieldDetailService.getSysExtendFieldDetail(story.getIssueId(), "externalHandlerId");
            String handler = getName(sysExtendFieldDetailTester);
            panoramasTaskDTO.setHandler(handler);
            Map stageMap = story.getStageId();
            String stageName = (String) stageMap.get("name");
            panoramasStoryDTO.setStoryStatus(stageName);

            Long stageId = (Long) stageMap.get("id");
            if (StageConstant.FirstStageEnum.SYS_TEST_STAGE.getValue().equals(stageId) ||
                    StageConstant.FirstStageEnum.ONLINE_STAGE.getValue().equals(stageId) ||
                    StageConstant.FirstStageEnum.FINISH_STAGE.getValue().equals(stageId)) {
                panoramasTaskDTO.setTaskStatus("已完成");
            } else if (StageConstant.FirstStageEnum.TEST_STAGE.getValue().equals(stageId)) {
                Map laneMap = story.getLaneId();
                Long laneId = (Long) laneMap.get("id");
                KanbanStageInstance kanbanStageInstance = stageService.getStageInfo(story.getProjectId(), laneId);
                if ("待领取".equals(kanbanStageInstance.getStageName())) {
                    panoramasTaskDTO.setTaskStatus("待领取");
                } else {
                    panoramasTaskDTO.setTaskStatus("进行中");
                }
            } else {
                panoramasTaskDTO.setTaskStatus("未开始");
            }
            panoramasFunTestTaskDTOList.add(panoramasTaskDTO);
            panoramasStoryDTO.setFunTestTask(panoramasFunTestTaskDTOList);
            panoramasStoryDTOList.add(panoramasStoryDTO);
        }
    }

    private String getName(SysExtendFieldDetail sysExtendFieldDetail) {
        String name = "";
        if (Optional.ofNullable(sysExtendFieldDetail).isPresent()) {
            if (Optional.ofNullable(sysExtendFieldDetail.getValue()).isPresent()) {
                try {
                    SsoUser user = userCache.get(Long.valueOf(sysExtendFieldDetail.getValue()));
                    if (Optional.ofNullable(user).isPresent()) {
                        name = user.getUserName();
                    }
                } catch (Exception e) {
                    loggr.error("getName error:{}", e.getMessage());
                }
            }
        }
        return name;
    }

    private void generateTaskPanoramas(List<com.yusys.agile.issue.dto.PanoramasTaskDTO> panoramasTaskDTOList, List<com.yusys.agile.issue.dto.IssueListDTO> taskList) {
        com.yusys.agile.issue.dto.PanoramasTaskDTO panoramasTaskDTO;
        for (com.yusys.agile.issue.dto.IssueListDTO task : taskList) {
            panoramasTaskDTO = new com.yusys.agile.issue.dto.PanoramasTaskDTO();
            panoramasTaskDTO.setIssueId(task.getIssueId());
            panoramasTaskDTO.setHandler(getHandlerName(task.getHandler()));
            Map stageMap = task.getStageId();
            Long stageId = (Long) stageMap.get("id");
            panoramasTaskDTO.setTaskStatus(TaskStatusEnum.getName(stageId));
            panoramasTaskDTOList.add(panoramasTaskDTO);
        }
    }

    private void generatePanoramasFeature(com.yusys.agile.issue.dto.PanoramasFeatureDTO panoramasFeatureDTO, com.yusys.agile.issue.dto.IssueListDTO feature) {
        panoramasFeatureDTO.setFeatureStatus(getStageName(feature.getStageId()));
        String handlerName = getHandlerName(feature.getHandler());
        panoramasFeatureDTO.setHandler(handlerName);
        panoramasFeatureDTO.setIssueId(feature.getIssueId());
        String title = feature.getTitle();
        panoramasFeatureDTO.setSystemName(title.substring(title.indexOf("_") + 1));
        SysExtendFieldDetail sysExtendFieldDetail = sysExtendFieldDetailService.getSysExtendFieldDetail(feature.getIssueId(), "externalHandlerId");
        String tester = getName(sysExtendFieldDetail);
        panoramasFeatureDTO.setSysTester(tester);
        Map stageMap = feature.getStageId();
        Long stageId = (Long) stageMap.get("id");
        if (StageConstant.FirstStageEnum.FINISH_STAGE.getValue().equals(stageId)
                || StageConstant.FirstStageEnum.ONLINE_STAGE.getValue().equals(stageId)) {
            panoramasFeatureDTO.setSysTestStatus("已完成");
        } else if (StageConstant.FirstStageEnum.SYS_TEST_STAGE.getValue().equals(stageId)) {
            Map laneMap = feature.getLaneId();
            Long laneId = (Long) laneMap.get("id");
            KanbanStageInstance kanbanStageInstance = stageService.getStageInfo(feature.getProjectId(), laneId);
            if ("待领取".equals(kanbanStageInstance.getStageName())) {
                panoramasFeatureDTO.setSysTestStatus("待领取");
            } else if ("待审核".equals(kanbanStageInstance.getStageName())) {
                panoramasFeatureDTO.setSysTestStatus("待审核");
            } else {
                panoramasFeatureDTO.setSysTestStatus("进行中");
            }
        } else {
            panoramasFeatureDTO.setSysTestStatus("未开始");
        }

    }

    private String getStageName(Map<String, String> stageMap) {
        String name = stageMap.get("name");
        return name;
    }


    private void setVersionName(com.yusys.agile.issue.dto.PanoramasEpicDTO panoramasEpicDTO, Long versionId) {
        VersionManagerDTO versionManagerDTO = versionManagerService.getVersionInfo(versionId);
        if (Optional.ofNullable(versionManagerDTO).isPresent()) {
            panoramasEpicDTO.setVersionName(versionManagerDTO.getVersionName());
        }
    }

    private String getHandlerName(Map mapHandler) {
        String handlerName = "";
        if (Optional.ofNullable(mapHandler).isPresent()) {
            handlerName = (String) mapHandler.get("name");
        }
        return handlerName;
    }

    /**
     * @Date 2020/10/21
     * @Description 设置Issue枚举的名称
     * @Return map
     */
    private Map<String, Map> setIssueTypeEnumName(List<Issue> issueList) {
        Map<String, Map> mapResult = new HashMap<>();
        //处理人id集合，并去重
        List<Long> handlerSet = issueList.stream().map(Issue::getHandler).distinct().collect(Collectors.toList());
        Map<Long, String> userMap = Maps.newHashMap();
        if (handlerSet != null && !handlerSet.isEmpty()) {
            userMap = faultService.getUserMapByUserIdList(handlerSet);
        }
        mapResult.put(USERMAP, userMap);

        //迭代id集合，并去重
        List<Long> sprintIdSet = issueList.stream().map(Issue::getSprintId).distinct().collect(Collectors.toList());
        Map<Long, List<SprintV3DTO>> sprintMap = Maps.newHashMap();
        if (sprintIdSet != null && !sprintIdSet.isEmpty()) {
            List<SprintV3DTO> sprintDTOS = sprintv3Service.selectSprintsBySprintIdList(sprintIdSet);
            sprintMap = sprintDTOS.stream().collect(Collectors.groupingBy(SprintV3DTO::getSprintId));
        }
        mapResult.put(SPRINTMAP, sprintMap);

        //项目id集合，并去重
        List<Long> projectIdSet = issueList.stream().map(Issue::getProjectId).distinct().collect(Collectors.toList());
        Map<Long, String> projectMap = Maps.newHashMap();
        if (projectIdSet != null && !projectIdSet.isEmpty()) {
            List<SsoProject> ssoProjectList = iFacadeProjectApi.getProjectListByProjectIds(projectIdSet);
            for (SsoProject ssoProject : ssoProjectList) {
                projectMap.put(ssoProject.getProjectId(), ssoProject.getProjectName());
            }
        }
        mapResult.put("projectMap", projectMap);

        //列头数据的Content
        Map<String, HashMap<String, String>> mapHeaderFieldContent = headerFieldService.getAllHeaderFieldContNotNull();
        mapResult.put(MAPHEADERFIELDCONTENT, mapHeaderFieldContent);
        return mapResult;
    }

    /**
     * @Date 2020/10/21
     * @Description 获取Issue枚举的名称
     * @Return IssueListDTO
     */
    private IssueListDTO getIssueTypeEnumName(Issue issue, com.yusys.agile.issue.dto.IssueListDTO issueListDTO, Map<String, Map> mapMap) {
        // 处理用户数据，
        Map<Long, String> userMap = mapMap.containsKey(USERMAP)?mapMap.get("userMap"):new HashMap<>();
        Map map;
        //Issue类型
        if (Optional.ofNullable(issue.getIssueType()).isPresent()) {
            map = new HashMap<String, String>();
            map.put("name", IssueTypeEnum.getName(issue.getIssueType()));
            map.put("id", issue.getIssueType());
            issueListDTO.setIssueType(map);
            if (IssueTypeEnum.TYPE_FAULT.CODE.equals(issue.getIssueType())) {
                if (Optional.ofNullable(issue.getCreateTime()).isPresent()) {
                    issueListDTO.setBeginDate(issue.getCreateTime());
                }
                if ( Optional.ofNullable(issue.getDeadline()).isPresent()){
                    issueListDTO.setEndDate(issue.getDeadline());
                }
            }
        }
        //列头的Coentent数据
        Map<String, HashMap<String, String>> mapHeaderFieldContent = mapMap.containsKey(MAPHEADERFIELDCONTENT)?mapMap.get(MAPHEADERFIELDCONTENT):new HashMap<>();

        //迭代ID
        if (Optional.ofNullable(issue.getSprintId()).isPresent()) {
            if (mapMap.containsKey(SPRINTMAP)&&
                    Optional.ofNullable(mapMap.get(SPRINTMAP)).isPresent()&&
                    mapMap.get(SPRINTMAP).containsKey(issue.getSprintId())) {
                Map<Long, List<SprintV3DTO>> sprintMap = mapMap.get(SPRINTMAP);
                map = new HashMap<String, String>();
                map.put("name", sprintMap.get(issue.getSprintId()).get(0).getSprintName());
                map.put("id", issue.getSprintId());
                issueListDTO.setSprintId(map);
            }
        }
        //priority优先级
        if (Optional.ofNullable(issue.getPriority()).isPresent()) {
            issueListDTO.setPriority(getOptionList(issue.getPriority().toString(), HeaderFieldUtil.PRIORITY, mapHeaderFieldContent));
        }
        //handler 处理人
        if (Optional.ofNullable(issue.getHandler()).isPresent()) {
            if (userMap != null && userMap.containsKey(issue.getHandler())) {
                map = new HashMap<String, String>();
                map.put("name", MapUtils.getString(userMap, issue.getHandler()));
                map.put("id", issue.getHandler());
                issueListDTO.setHandler(map);
            }
        }

        //projectId 项目名称
        if (Optional.ofNullable(issue.getProjectId()).isPresent()) {
            if (mapMap.containsKey("projectMap")&&
                    Optional.ofNullable(mapMap.get("projectMap")).isPresent() &&
                    mapMap.get("projectMap").containsKey(issue.getProjectId())) {
                Map<Long, String> projectMap = mapMap.get("projectMap");
                map = new HashMap<String, String>();
                map.put("name", MapUtils.getString(projectMap, issue.getProjectId()));
                map.put("id", issue.getProjectId());
                issueListDTO.setProjectIdMap(map);
            }
        }
        return issueListDTO;
    }

    /**
     * @param issueList
     * @return
     * @description 拼装工作项id
     * @date 2020/10/12
     */
    private List<Long> assembleIssueIdList(List<Issue> issueList) {
        List<Long> issueIdList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(issueList)) {
            List<Long> issueIds = Lists.newArrayList();
            for (Issue issue : issueList) {
                issueIds.add(issue.getIssueId());
            }
            issueIdList.addAll(issueIds);
        }
        return issueIdList;
    }

    /**
     * 功能描述  查询扩展字段
     *
     * @param map
     * @param stringBooleanMap 根据此字段判断是否有扩展字段的查询条件
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/11
     */
    public Map<Byte, List<Long>> getExtendFields(Map<String, Object> map, Map<String, Boolean> stringBooleanMap) {
        Map<Byte, List<Long>> listMap = new HashMap<>();
        List issueIds = Lists.newArrayList();
        if (map.containsKey("issueIds")) {
            issueIds = (List) map.get("issueIds");
        }
        //获取所有类型的扩展字段
        List<SysExtendField> sysExtendFieldList = sysExtendFieldService.getAllSysExtendField(null);
        Map<String, List<SysExtendField>> listMapT = sysExtendFieldList.stream().collect(Collectors.groupingBy(SysExtendField::getFieldId));
        Iterator it = map.keySet().iterator();
        Map<String, Map<String, String>> mapAll = new HashMap<>();
        Map<String, Object> mapEpic = new HashMap<>();
        Map<String, Object> mapFeature = new HashMap<>();
        Map<String, Object> mapStory = new HashMap<>();
        Map<String, Object> mapTask = new HashMap<>();
        while (it.hasNext()) {
            String key = it.next().toString();
            //将版本计划versionName从扩展字段移除
            if ("versionName".equals(key)) {
                continue;
            }
            if (listMapT.containsKey(key)) {
                List<SysExtendField> sysExtendFieldList1 = listMapT.get(key);
                for (int i = 0; i < sysExtendFieldList1.size(); i++) {

                    //为空的暂时不处理  后续补充 TODO
                    if (IssueTypeEnum.TYPE_EPIC.CODE.equals(sysExtendFieldList1.get(i).getExtendType())) {
                        //局方需求负责人支持多人同时查询 比如：张三,李四
                        if ("responsiblePerson".equals(key)) {
                            mapEpic.put(sysExtendFieldList1.get(i).getFieldId(), map.get(key).toString().split(","));
                        } else {
                            mapEpic.put(sysExtendFieldList1.get(i).getFieldId(), map.get(key).toString().split(",")[0]);
                        }
                        break;
                    } else if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(sysExtendFieldList1.get(i).getExtendType())) {
                        mapFeature.put(sysExtendFieldList1.get(i).getFieldId(), map.get(key).toString().split(",")[0]);
                        break;
                    } else if (IssueTypeEnum.TYPE_STORY.CODE.equals(sysExtendFieldList1.get(i).getExtendType())) {
                        mapStory.put(sysExtendFieldList1.get(i).getFieldId(), map.get(key).toString().split(",")[0]);
                        break;
                    } else if (IssueTypeEnum.TYPE_TASK.CODE.equals(sysExtendFieldList1.get(i).getExtendType())) {
                        mapTask.put(sysExtendFieldList1.get(i).getFieldId(), map.get(key).toString().split(",")[0]);
                    }
                }
            }
        }
        if (MapUtils.isEmpty(mapEpic) && MapUtils.isEmpty(mapFeature) && MapUtils.isEmpty(mapStory) && MapUtils.isEmpty(mapTask)) {
            stringBooleanMap.put("stringBooleanMap", false);
            return listMap;
        }
        if (MapUtils.isNotEmpty(mapEpic)) {
            List<Long> longListEpic = sysExtendFieldDetailService.getSysExtendFieldDetail(mapEpic, issueIds);
            if (CollectionUtils.isEmpty(longListEpic)) {
                return new HashMap<>();
            }
            listMap.put(IssueTypeEnum.TYPE_EPIC.CODE, longListEpic);
        }
        if (MapUtils.isNotEmpty(mapFeature)) {
            List<Long> longListFeature = sysExtendFieldDetailService.getSysExtendFieldDetail(mapFeature, issueIds);
            if (CollectionUtils.isEmpty(longListFeature)) {
                return new HashMap<>();
            }
            listMap.put(IssueTypeEnum.TYPE_FEATURE.CODE, longListFeature);
        }
        if (MapUtils.isNotEmpty(mapStory)) {
            List<Long> longListStory = sysExtendFieldDetailService.getSysExtendFieldDetail(mapStory, issueIds);
            if (CollectionUtils.isEmpty(longListStory)) {
                return new HashMap<>();
            }
            listMap.put(IssueTypeEnum.TYPE_STORY.CODE, longListStory);
        }
        if (MapUtils.isNotEmpty(mapTask)) {
            List<Long> longListTask = sysExtendFieldDetailService.getSysExtendFieldDetail(mapTask, issueIds);
            if (CollectionUtils.isEmpty(longListTask)) {
                return new HashMap<>();
            }
            listMap.put(IssueTypeEnum.TYPE_TASK.CODE, longListTask);
        }
        return listMap;
    }

    @Override
    public String translateExtendFieldMap(String fieldCode, String value, Byte issueType, Map<String, Map> mapMap) {
        String result =  value;
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        HeaderFieldExample.Criteria criteria = headerFieldExample.createCriteria();
        criteria.andFieldCodeEqualTo(fieldCode)
                .andIsCustomEqualTo(Byte.parseByte("0"))
                .andCategoryEqualTo(issueType);
        List<HeaderField> headerFields = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExample);
        //当扩展字段是人员时：将人员翻译成用户名（账号）
        if (CollectionUtils.isNotEmpty(headerFields)&&"user".equals(headerFields.get(0).getFieldGroup())) {
            Map<Long,String> mapUser = mapMap.containsKey(USERMAP)?mapMap.get(USERMAP):new HashMap<>();
            result = mapUser.containsKey(Long.parseLong(value))?mapUser.get(Long.parseLong(value)):value;
        }
        return result;
    }

    @Override
    public Map queryIssueEpic(Long issueId, Byte issueType) {
        Map<String, Object> mapT = Maps.newHashMap();
        Long issueIdEpic = issueFactory.getEpicId(issueId, issueType);
        if (issueIdEpic != null) {
            List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getSysExtendFieldDetail(issueIdEpic);
            Issue issue = issueMapper.selectByPrimaryKey(issueIdEpic);
            BeanMap beanMap = BeanMap.create(issue);
            for (Object key : beanMap.keySet()) {
                mapT.put(key.toString(), beanMap.get(key));
            }
            if (CollectionUtils.isNotEmpty(sysExtendFieldDetailList)) {
                Map<String, Map> mapMap = issueMap(null);
                for (SysExtendFieldDetail s : sysExtendFieldDetailList) {
                    mapT.put(s.getFieldId(), translateExtendFieldMap(s.getFieldId(), s.getValue().trim(),issueType,mapMap ));
                }
            }
        }
        return mapT;
    }


    @Override
    public List<Long> getNotCanceledAndOnlineIssueByIssueIdList(List<Long> issueIdList) {
        return issueMapper.getNotCanceledAndOnlineIssueByIssueIdList(issueIdList);
    }


    /**
     * @param issueHistoryList
     * @description 处理状态变化
     * @date 2020/12/10
     */
    private void dealStateHistory(List<Issue> issueHistoryList) {
        if (CollectionUtils.isNotEmpty(issueHistoryList)) {
            Long[] stages = new Long[]{StageConstant.FirstStageEnum.FINISH_STAGE.getValue()};
            Issue newIssue;
            List<IssueHistoryRecord> historyRecords;
            for (Issue oldIssue : issueHistoryList) {
                //历史记录状态变化
                historyRecords = Lists.newArrayList();
                newIssue = new Issue();
                newIssue.setIssueId(oldIssue.getIssueId());
                issueFactory.dealStages(newIssue, oldIssue, stages, historyRecords);
                issueFactory.dealHistory(historyRecords);
            }
        }
    }

    @Override
    public List<Long> getNotOnlineEpic() {
        return issueMapper.getNotOnlineEpic();
    }

    @Override
    public List<Issue> getValidIssue(List<Long> epicIdList) {
        IssueExample example = new IssueExample();
        example.createCriteria().andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE).andIssueIdIn(epicIdList);
        return issueMapper.selectByExample(example);
    }

    private List<Long> updateChildrenStage(List<Long> parentIdList) {
        List<Long> childrenIdList = Lists.newArrayList();
        IssueExample exampleFeature = new IssueExample();
        exampleFeature.createCriteria().andParentIdIn(parentIdList).andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        List<Issue> issueList = issueMapper.selectByExample(exampleFeature);
        if (CollectionUtils.isNotEmpty(issueList)) {
            childrenIdList = issueList.stream().map(Issue::getIssueId).collect(Collectors.toList());
            issueMapper.batchUpdateIssueStageStatus(childrenIdList, StageConstant.FirstStageEnum.FINISH_STAGE.getValue(), null);
            insertHistoryRecord(childrenIdList);
        }
        return childrenIdList;
    }

    private void insertHistoryRecord(List<Long> issueIdList) {
        IssueHistoryRecord issueHistoryRecord;
        for (Long issueId : issueIdList) {
            issueHistoryRecord = new IssueHistoryRecord();
            issueHistoryRecord.setOldValue("6-9");
            issueHistoryRecord.setNewValue(StageConstant.FirstStageEnum.FINISH_STAGE.getValue().toString());
            issueHistoryRecord.setIssueId(issueId);
            issueHistoryRecord.setOperationField("阶段id");
            issueHistoryRecordMapper.insertSelective(issueHistoryRecord);
        }
    }

    @Override
    public List<Long> getIssueIds(List<Long> parentIds) {
        List<Long> longList = Lists.newArrayList();
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue());
        if (CollectionUtils.isNotEmpty(parentIds)) {
            criteria.andParentIdIn(parentIds);
        }
        List<Issue> issues = issueMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(issues)) {
            Map<Long, List<Issue>> map = issues.stream().collect(Collectors.groupingBy(Issue::getIssueId));
            longList = Lists.newArrayList(map.keySet());
        }
        return longList;
    }


    /**
     * 精益看板获取树形工作项列表
     * 默认不展示已归档的feature
     * @param kanbanId
     * @param issueType
     * @return
     */
    @Override
    public List<IssueDTO> getIssueTrees(Long kanbanId, Byte issueType) {
        IssueExample example = new IssueExample();
        example.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue())
                .andIssueTypeEqualTo(issueType)
                .andKanbanIdTo(kanbanId)
                .andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);

        example.setOrderByClause("create_time desc");
        List<Issue> issues = issueMapper.selectByExample(example);
        List<IssueDTO> issueDTOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(issues)) {
            try {
                issueDTOS = ReflectUtil.copyProperties4List(issues, IssueDTO.class);
                recursionGetIssues(issueDTOS, kanbanId);
            } catch (Exception e) {
                loggr.info("工作项数据转换异常:{}", e.getMessage());
            }
        }
        return issueDTOS;
    }

    /**
     * 递归获取子工作项
     *
     * @param issues
     */
    public void recursionGetIssues(List<IssueDTO> issues, Long kanbanId) throws ExecutionException {
        for (IssueDTO issueDTO : issues) {

            Long issueId = issueDTO.getIssueId();
            //处理系统信息
            Long systemId = issueDTO.getSystemId();
            SsoSystem ssoSystem = systemCache.get(systemId);
            if (Optional.ofNullable(ssoSystem).isPresent()) {
                issueDTO.setSystemCode(ssoSystem.getSystemCode());
                issueDTO.setSystemName(ssoSystem.getSystemName());
            }
            //处理人信息
            Long handler = issueDTO.getHandler();
            SsoUser user = userCache.get(handler);
            if (Optional.ofNullable(user).isPresent()) {
                issueDTO.setHandlerAccount(user.getUserAccount());
                issueDTO.setHandlerName(user.getUserName());
            }

            IssueExample example = new IssueExample();
            example.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                    .andKanbanIdTo(kanbanId).andParentIdEqualTo(issueId);
            example.setOrderByClause("create_time desc");
            List<Issue> issueList = issueMapper.selectByExample(example);

            List<IssueDTO> childs = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(issueList)) {
                //处理卡片上显示数据
                Byte type = issueDTO.getIssueType();
                if(IssueTypeEnum.TYPE_FEATURE.CODE.equals(type)){

                    issueDTO.setStoryTotalNum(issueList.size());
                    issueDTO.setStroyFinishNum(getIssueFinishNum(issueId,kanbanId));
                }else if(IssueTypeEnum.TYPE_STORY.CODE.equals(type)){

                    issueDTO.setTaskNum(issueList.size());
                    issueDTO.setTaskFinishNum(getIssueFinishNum(issueId,kanbanId));
                }

                try {
                    childs = ReflectUtil.copyProperties4List(issueList, IssueDTO.class);
                    issueDTO.setChildren(childs);
                    recursionGetIssues(childs, kanbanId);
                } catch (Exception e) {
                    loggr.info("工作项数据转换异常:{}", e.getMessage());
                }
            } else {
                issueDTO.setChildren(childs);
            }
        }
    }

    private int getIssueFinishNum(Long issueId,Long kanbanId){
        List<Long> laneIds = Lists.newArrayList();
        laneIds.add(LaneKanbanStageConstant.DevStageEnum.DEVFINISH.getValue());
        laneIds.add(LaneKanbanStageConstant.TestStageEnum.TESTFINISH.getValue());

        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andKanbanIdTo(kanbanId)
                .andParentIdEqualTo(issueId)
                .andLaneIdIn(laneIds);
        long count = issueMapper.countByExample(issueExample);
        return (int)count;
    }

    @Override
    public boolean checkHasChildren(Long epicId) {
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andParentIdEqualTo(epicId).andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        List<Issue> issueList = issueMapper.selectByExample(issueExample);
        return CollectionUtils.isNotEmpty(issueList) ? false : true;
    }


    /**
     * 拖拽规则
     * <p>
     * 算法介绍：
     * 按照悲观法计算，以状态最小的去更新,
     * 改变featuer状态触发epic状态改变，
     * 改变task状态触发story状态改变，从而触发feature和epic状态改变
     *
     * @param issueId
     * @param stageId
     * @param laneId
     * @return
     */
    @Override
    public IssueDTO dragIssueCard(Long issueId, Long stageId, Long laneId) throws ExecutionException {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        Long kanbanId = issue.getKanbanId();

        if (!Optional.ofNullable(kanbanId).isPresent()) {
            throw new BusinessException("该工作项不属于精益看板，不能拖拽!");
        }
        issue.setStageId(stageId);
        issue.setLaneId(laneId);
        if(StageConstant.FirstStageEnum.READY_STAGE.equals(stageId)){
            throw new BusinessException("工作项不能拖拽到就绪阶段!");
        }
        issueMapper.updateByPrimaryKey(issue);

        //更新该工作项对应的数据
        Byte type = issue.getIssueType();

        //feature向上汇总改变epic状态
        if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(type)) {

            issueUpRegularFactory.commonIssueUpRegular(issueId);

        } else if (IssueTypeEnum.TYPE_TASK.CODE.equals(type)) {
            //task改变后状态向 上汇总
            updateTaskParentStatus(issueId, kanbanId);

            Long storyId = issue.getParentId();
            Issue story = issueMapper.selectByPrimaryKey(storyId);
            if (Optional.ofNullable(story).isPresent()) {

                Long featureId = story.getParentId();
                Issue feature = issueMapper.selectByPrimaryKey(featureId);
                if (Optional.ofNullable(feature).isPresent()) {
                    //返回前端变更数据
                    IssueDTO storyDTO = ReflectUtil.copyProperties(story, IssueDTO.class);
                    IssueDTO taskDTO = ReflectUtil.copyProperties(issue, IssueDTO.class);
                    IssueDTO issueDTO = ReflectUtil.copyProperties(feature, IssueDTO.class);
                    setOtherInfo(storyDTO);
                    setOtherInfo(taskDTO);
                    setOtherInfo(issueDTO);

                    List<IssueDTO> taskDTOS = Lists.newArrayList();
                    List<IssueDTO> storyDTOS = Lists.newArrayList();
                    taskDTOS.add(taskDTO);
                    storyDTOS.add(storyDTO);
                    storyDTO.setChildren(taskDTOS);
                    issueDTO.setChildren(storyDTOS);
                    return issueDTO;
                }
            }
        }
        return null;
    }

    private void setOtherInfo(IssueDTO issueDTO) throws ExecutionException {
        //处理系统信息
        Long systemId = issueDTO.getSystemId();
        SsoSystem ssoSystem = systemCache.get(systemId);
        if (Optional.ofNullable(ssoSystem).isPresent()) {

            issueDTO.setSystemCode(ssoSystem.getSystemCode());
            issueDTO.setSystemName(ssoSystem.getSystemName());
        }
        //处理人信息
        Long handler = issueDTO.getHandler();
        SsoUser ssoUser = userCache.get(handler);
        if (Optional.ofNullable(ssoUser).isPresent()) {

            issueDTO.setHandlerAccount(ssoUser.getUserAccount());
            issueDTO.setHandlerName(ssoUser.getUserName());
        }

        //处理卡片上显示数据
        Long issueId = issueDTO.getIssueId();
        Long kanbanId = issueDTO.getKanbanId();
        Byte type = issueDTO.getIssueType();

        if(IssueTypeEnum.TYPE_FEATURE.CODE.equals(type)){
            issueDTO.setStoryTotalNum(getIssueChildNum(issueId,kanbanId));
            issueDTO.setStroyFinishNum(getIssueFinishNum(issueId,kanbanId));

        }else if(IssueTypeEnum.TYPE_STORY.CODE.equals(type)){
            issueDTO.setTaskNum(getIssueChildNum(issueId,kanbanId));
            issueDTO.setTaskFinishNum(getIssueFinishNum(issueId,kanbanId));
        }
    }

    private int getIssueChildNum(Long issueId ,Long kanbanId){
        IssueExample example = new IssueExample();
        example.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andKanbanIdTo(kanbanId).andParentIdEqualTo(issueId);
        long count = issueMapper.countByExample(example);
        return (int) count;
    }


    /**
     * 更新 issue状态，联动更新父级的工作项状态
     *
     * @param issueId
     * @return
     */
    @Override
    public void updateTaskParentStatus(Long issueId, Long kanbanId) {

        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        if (Optional.ofNullable(issue).isPresent()) {

            Long parentId = issue.getParentId();
            Byte type = issue.getIssueType();

            //如果工作项类型为任务,阶段分为 开发和测试阶段
            if (IssueTypeEnum.TYPE_TASK.CODE.equals(type)) {
                List<Issue> issueList = issueMapper.selectIssueListByParentId(parentId, kanbanId);
                if (CollectionUtils.isNotEmpty(issueList)) {

                    //获取最左边的issue状态
                    Issue first = issueList.get(0);
                    Long storyLaneId = null;
                    Long storyStageId = null;

                    //如果有开发任务在进行中，则故事在开发中状态
                    Long stageId = first.getStageId();
                    Long laneId = first.getLaneId();
                    if (StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(stageId) &&
                            !LaneKanbanStageConstant.DevStageEnum.DEVFINISH.getValue().equals(laneId)) {
                        storyLaneId = LaneKanbanStageConstant.DevStageEnum.ONGOING.getValue();
                        storyStageId = StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue();
                    }

                    //如果所有故事都到已完成，根据测试任务去计算故事状态
                    if (StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(stageId) &&
                            LaneKanbanStageConstant.DevStageEnum.DEVFINISH.getValue().equals(laneId)) {

                        //获取第二个状态的工作项
                        Issue second = null;
                        for (Issue iss : issueList) {
                            if (!laneId.equals(iss.getLaneId())) {
                                second = iss;
                                break;
                            }
                        }

                        //如果从左边数第二个状态和第一个状态一致，说明没有测试任务,故事状态为开发完成
                        if (!Optional.ofNullable(second).isPresent()) {
                            storyLaneId = LaneKanbanStageConstant.DevStageEnum.FINISH.getValue();
                            storyStageId = StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue();

                        } else {
                            Long secondLaneId = second.getLaneId();
                            //如果第二个状态为测试完成，故事为测试完成
                            if (LaneKanbanStageConstant.TestStageEnum.TESTFINISH.getValue().equals(secondLaneId)) {
                                storyLaneId = LaneKanbanStageConstant.TestStageEnum.TESTFINISH.getValue();
                                storyStageId = StageConstant.FirstStageEnum.TEST_STAGE.getValue();

                                Issue story = issueMapper.selectByPrimaryKey(parentId);
                                if (Optional.ofNullable(story).isPresent()) {
                                    Long featureId = story.getParentId();
                                    story.setStageId(storyStageId);
                                    story.setLaneId(storyLaneId);
                                    issueMapper.updateByPrimaryKeySelective(story);

                                    IssueExample example = new IssueExample();
                                    example.createCriteria().andParentIdEqualTo(featureId).andStateEqualTo(StateEnum.U.getValue());
                                    List<Issue> issues = issueMapper.selectByExample(example);
                                    Set<Long> result = issues.stream().map(issu -> issu.getLaneId()).collect(Collectors.toSet());
                                    //如果feature 下故事都已经完成，则更新feature状态为 系统测试阶段，进行中
                                    if(result.size() == 1 && result.contains(storyLaneId)){
                                        Issue feature = issueMapper.selectByPrimaryKey(featureId);
                                        if(Optional.ofNullable(feature).isPresent()){
                                            feature.setStageId(StageConstant.FirstStageEnum.SYS_TEST_STAGE.getValue());
                                            feature.setLaneId(LaneKanbanStageConstant.SystemTestStageEnum.ONGOING.getValue());
                                            issueMapper.updateByPrimaryKeySelective(feature);
                                            //向上汇总状态
                                            issueUpRegularFactory.commonIssueUpRegular(feature.getIssueId());
                                            return;
                                        }
                                    }else {
                                        //向上汇总状态
                                        issueUpRegularFactory.commonIssueUpRegular(story.getIssueId());
                                        return;
                                    }
                                }
                            } else {
                                storyLaneId = LaneKanbanStageConstant.TestStageEnum.TESTING.getValue();
                                storyStageId = StageConstant.FirstStageEnum.TEST_STAGE.getValue();
                            }
                        }
                    }

                    Issue story = issueMapper.selectByPrimaryKey(parentId);
                    if (Optional.ofNullable(story).isPresent()) {
                        story.setStageId(storyStageId);
                        story.setLaneId(storyLaneId);
                        issueMapper.updateByPrimaryKeySelective(story);
                        //向上汇总状态
                        issueUpRegularFactory.commonIssueUpRegular(story.getIssueId());
                    }
                }
            }
        }
    }

    @Override
    public Issue getIssueByIssueId(Long issueId) throws Exception {
        return issueMapper.selectByPrimaryKey(issueId);
    }

    @Override
    public void orgIssueExtendFields(Long issueId, Map<String, Object> map) {
        if (Optional.ofNullable(issueId).isPresent()) {
            List<Long> issueIds = Lists.newArrayList();
            issueIds.add(issueId);
            List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getIssueExtendDetailList(issueIds);
            for (int i = 0; i < sysExtendFieldDetailList.size(); i++) {
                map.put(sysExtendFieldDetailList.get(i).getFieldId(), sysExtendFieldDetailList.get(i).getValue());
            }
        }
    }

    @Override
    public void checkIssueState(Long issueId, Long fromStageId,Long fromLaneId, Long toStageId,Long toLaneId,Long fromKanbanId) {

        loggr.info("校验卡片状态入参: issueId{},fromStageId:{},fromLaneId:{},toStageId:{},toLaneId:{}",issueId,fromStageId,fromLaneId,toStageId,toLaneId);
        Issue issue = issueMapper.selectByPrimaryKey(issueId);


        if(!fromKanbanId.equals(issue.getKanbanId())){
            throw new BusinessException("卡片拖动失败,,当前卡片不在此看板,请刷新");
        }

        boolean isOrigPosition = true;
        Long laneId = issue.getLaneId();
        Long stageId = issue.getStageId();
        loggr.info("数据库issue状态: laneId{},stageId{}",laneId,stageId);
        if (fromStageId.equals(stageId)) {
            if(laneId != null && fromLaneId != null && !laneId.equals(fromLaneId)){
                isOrigPosition =  false;
            }else {
                if((fromLaneId == null && laneId != null) || (laneId == null && fromLaneId != null) ){
                    isOrigPosition =  false;
                }
            }
        }else {
            isOrigPosition = false;
        }

        if(!isOrigPosition){
            throw new BusinessException("卡片拖动失败,卡片位置不在当前位置,请刷新");
        }

        Long teamId = issue.getTeamId();
        if(!Optional.ofNullable(teamId).isPresent()){
            Long kanbanId = issue.getKanbanId();
            SLeanKanban sLeanKanban = leanKanbanMapper.selectByPrimaryKey(kanbanId);
            if(!Optional.ofNullable(sLeanKanban).isPresent()){
                throw new BusinessException("当前看板不存在");
            }
            teamId = sLeanKanban.getTeamId();
        }

        List<Long> stageIds = Lists.newArrayList();
        StageConstant.FirstStageEnum[] values = StageConstant.FirstStageEnum.values();
        for (int i = 0; i < values.length; i++) {
            stageIds.add(values[i].getValue());
        }
        stageIds = stageIds.stream().sorted().collect(Collectors.toList());
        if(stageIds.indexOf(toStageId) < stageIds.indexOf(fromStageId)){

            throw new BusinessException("任务卡片不能往回拖动");
        }

        if(fromLaneId != null  && toLaneId != null){
            SLeanKanbanDTO leanKanbanDTO = leanKanbanService.queryLeanKanbanInfo(teamId);
            List<KanbanStageInstanceDTO> kanbanStageInstances = leanKanbanDTO.getKanbanStageInstances();
            List<Long> laneIds = Lists.newArrayList();
            kanbanStageInstances.forEach(
                    kanbanStageInstanceDTO -> {
                        List<KanbanStageInstanceDTO> secondStages = kanbanStageInstanceDTO.getSecondStages();
                        if(CollectionUtils.isNotEmpty(secondStages)){
                            secondStages.forEach(
                                    secondStage->{
                                        laneIds.add(secondStage.getStageId());
                                    }
                            );
                        }
                    }
            );

            loggr.info("获取看板:{},所有laneId集合 :{}",leanKanbanDTO.getKanbanName(),JSONObject.toJSONString(laneIds));
            if(laneIds.indexOf(toLaneId) < laneIds.indexOf(fromLaneId)){

                throw new BusinessException("任务卡片不能往回拖动");
            }
        }

        if(IssueTypeEnum.TYPE_TASK.CODE.equals(issue.getIssueType())){
            //开发类任务不能拖入测试中
            if(!TaskTypeEnum.TEST.CODE.equals(issue.getTaskType())){

                if(StageConstant.FirstStageEnum.TEST_STAGE.getValue().equals(toStageId)){
                    throw new BusinessException("开发任务不能拖入测试阶段.");
                }
            }

        }
    }

    /**
     * 组织，查询团队信息
     *
     * @param issueId
     * @param issueType
     * @param mapMap
     * @return
     */
    public Map getIssueTeamMap(Long issueId, Byte issueType, Map<String, Map> mapMap) {
        Map map = new HashMap<String, String>();
        Issue issue = selectIssueByIssueId(issueId);
        Long parentId = issue.getParentId();
        Long teamId = issue.getTeamId();
        if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType) && Optional.ofNullable(parentId).isPresent()) {
            parentId = selectIssueByIssueId(parentId).getParentId();
            if (Optional.ofNullable(parentId).isPresent()) {
                teamId = selectIssueByIssueId(parentId).getTeamId();
            }
        }
        if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            if (Optional.ofNullable(parentId).isPresent()) {
                teamId = selectIssueByIssueId(parentId).getTeamId();
            }
        }
        if (Optional.ofNullable(teamId).isPresent()) {
            Map<Long, List<STeam>> mapSTeam = mapMap.containsKey(MAPSTEAM)?mapMap.get(MAPSTEAM):new HashMap<>();
            if (mapSTeam.containsKey(teamId)) {
                STeam sTeam = mapSTeam.get(teamId).get(0);
                map.put("name", sTeam.getTeamName());
                map.put("id", sTeam.getTeamId());
                map.put("TeamType", sTeam.getTeamType());
                map.put("TeamTypeName", TeamTypeEnum.getNameByCode(sTeam.getTeamType()));
            }
        }
        return map;
    }

    /**
     * 校验扩展字段是否满足条件以及满足条件的过滤结果
     *
     * @param issueRecord
     * @param map
     * @return
     */
    public boolean checkAndGetIssueIdByExtendFieldParam(IssueRecord issueRecord, Map map) {

        Map<String, Boolean> stringBooleanMap = new HashMap<>();
        stringBooleanMap.put("stringBooleanMap", true);
        Byte issueType = Byte.parseByte(map.get("issueType").toString());
        Map<Byte, List<Long>> extendFieldIssueIds = getExtendFields(map, stringBooleanMap);
        if (stringBooleanMap.get("stringBooleanMap")) {
            if (MapUtils.isEmpty(extendFieldIssueIds)) {
                return false;
            } else {
                //根据扩展字段查询满足条件的上级主键Id, >_< >_< >_<
                List<Long> epicResultIds = Lists.newArrayList();
                if (issueType.compareTo(IssueTypeEnum.TYPE_EPIC.CODE) >= 0) {
                    if (extendFieldIssueIds.containsKey(IssueTypeEnum.TYPE_EPIC.CODE)) {
                        if (CollectionUtils.isEmpty(extendFieldIssueIds.get(IssueTypeEnum.TYPE_EPIC.CODE))) {
                            return false;
                        }
                        epicResultIds = issueMapper.listLevelIssueIdforEpic(extendFieldIssueIds.get(IssueTypeEnum.TYPE_EPIC.CODE));
                        if (CollectionUtils.isEmpty(epicResultIds)) {
                            return false;
                        }
                    }
                }
                if (issueType.compareTo(IssueTypeEnum.TYPE_FEATURE.CODE) >= 0) {
                    if (extendFieldIssueIds.containsKey(IssueTypeEnum.TYPE_FEATURE.CODE)) {
                        if (CollectionUtils.isEmpty(extendFieldIssueIds.get(IssueTypeEnum.TYPE_FEATURE.CODE))) {
                            return false;
                        }
                        epicResultIds = issueMapper.listLevelIssueIdforFeature(extendFieldIssueIds.get(IssueTypeEnum.TYPE_FEATURE.CODE), epicResultIds);
                        if (CollectionUtils.isEmpty(epicResultIds)) {
                            return false;
                        }
                    } else {
                        epicResultIds = issueMapper.listLevelIssueIdforFeature(Lists.newArrayList(), epicResultIds);
                        if (CollectionUtils.isEmpty(epicResultIds)) {
                            return false;
                        }
                    }
                }
                if (issueType.compareTo(IssueTypeEnum.TYPE_STORY.CODE) >= 0) {
                    if (extendFieldIssueIds.containsKey(IssueTypeEnum.TYPE_STORY.CODE)) {
                        if (CollectionUtils.isEmpty(extendFieldIssueIds.get(IssueTypeEnum.TYPE_STORY.CODE))) {
                            return false;
                        }
                        epicResultIds = issueMapper.listLevelIssueIdforStory(extendFieldIssueIds.get(IssueTypeEnum.TYPE_STORY.CODE), epicResultIds);
                        if (CollectionUtils.isEmpty(epicResultIds)) {
                            return false;
                        }
                    } else {
                        epicResultIds = issueMapper.listLevelIssueIdforStory(Lists.newArrayList(), epicResultIds);
                        if (CollectionUtils.isEmpty(epicResultIds)) {
                            return false;
                        }
                    }
                }
                if (issueType.compareTo(IssueTypeEnum.TYPE_TASK.CODE) >= 0) {
                    if (extendFieldIssueIds.containsKey(IssueTypeEnum.TYPE_TASK.CODE)) {
                        if (CollectionUtils.isEmpty(extendFieldIssueIds.get(IssueTypeEnum.TYPE_TASK.CODE))) {
                            return false;
                        }
                        epicResultIds = issueMapper.listLevelIssueIdforTask(extendFieldIssueIds.get(IssueTypeEnum.TYPE_TASK.CODE), epicResultIds);
                        if (CollectionUtils.isEmpty(epicResultIds)) {
                            return false;
                        }
                    } else {
                        epicResultIds = issueMapper.listLevelIssueIdforTask(Lists.newArrayList(), epicResultIds);
                        if (CollectionUtils.isEmpty(epicResultIds)) {
                            return false;
                        }
                    }
                }
                if (issueRecord.getIssueIds() != null && issueRecord.getIssueIds().size() > 0) {
                    issueRecord.getIssueIds().retainAll(epicResultIds);
                } else {
                    issueRecord.setIssueIds(epicResultIds);
                }
            }
        }
        return true;
    }

    @Override
    public List<Long> selectIssueIdByTenantCode(String tenantCode) {
        return issueMapper.selectIssueIdByTenantCode(tenantCode);
    }

    /**
     * 组织基本字段查询条件
     * @param issueRecord
     * @param issueStringDTO
     */
    public void orgIssueBasicParams(IssueRecord issueRecord,IssueStringDTO issueStringDTO){

        if (Optional.ofNullable(issueStringDTO.getPageNum()).isPresent()&&
                Optional.ofNullable(issueStringDTO.getPageSize()).isPresent() ) {
            issueRecord.setPageNum(StringUtil.StringtoInteger(issueStringDTO.getPageNum()));
            issueRecord.setPageSize(StringUtil.StringtoInteger(issueStringDTO.getPageSize()));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getIssueType())) {
            issueRecord.setIssueTypes(dealData(issueStringDTO.getIssueType(), BYTE));
        }

        if (StringUtils.isNotEmpty(issueStringDTO.getPriority())) {
            issueRecord.setPrioritys(dealData(issueStringDTO.getPriority(), BYTE));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getImportance())) {
            issueRecord.setImportances(dealData(issueStringDTO.getImportance(), BYTE));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getCompletion())) {
            issueRecord.setCompletions(dealData(issueStringDTO.getCompletion(), STRING));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getStageId())) {
            issueRecord.setStageIds(dealData(issueStringDTO.getStageId(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getFaultLevel())) {
            issueRecord.setFaultLevels(dealData(issueStringDTO.getFaultLevel(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getFaultType())) {
            issueRecord.setFaultTypes(dealData(issueStringDTO.getFaultType(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getOrder())) {
            issueRecord.setOrders(dealData(issueStringDTO.getOrder(), INTEGER));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getSprintId())) {
            issueRecord.setSprintIds(dealData(issueStringDTO.getSprintId(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getSystemId())) {
            issueRecord.setSystemIds(dealData(issueStringDTO.getSystemId(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getTeamId())) {
            issueRecord.setTeamIds(dealData(issueStringDTO.getTeamId(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getModuleId())) {
            issueRecord.setModuleIds(dealData(issueStringDTO.getModuleId(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getFixedUid())) {
            issueRecord.setFixedUids(dealData(issueStringDTO.getFixedUid(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getTestUid())) {
            issueRecord.setTestUids(dealData(issueStringDTO.getTestUid(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getCreateUid())) {
            issueRecord.setCreateUids(dealData(issueStringDTO.getCreateUid(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getUpdateUid())) {
            issueRecord.setUpdateUids(dealData(issueStringDTO.getUpdateUid(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getHandler())) {
            issueRecord.setHandlers(dealData(issueStringDTO.getHandler(), LONG));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getCreateTime())) {
            issueRecord.setCreateTime(dealData(issueStringDTO.getCreateTime(), DATE));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getBeginDate())) {
            issueRecord.setBeginDate(dealData(issueStringDTO.getBeginDate(), DATE));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getEndDate())) {
            issueRecord.setEndDate(dealData(issueStringDTO.getEndDate(), DATE));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getUpdateTime())) {
            issueRecord.setUpdateTime(dealData(issueStringDTO.getUpdateTime(), DATE));
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getIsArchive())) {
            issueRecord.setIsArchive(dealData(issueStringDTO.getIsArchive(), BYTE));
        }
        // 判断是根据id还是name
        if (StringUtils.isNotEmpty(issueStringDTO.getIdOrTitle())) {
            String idOrTitle = issueStringDTO.getIdOrTitle();
            try {
                Long id = Long.valueOf(idOrTitle);
                issueRecord.setIssueId(id);
            } catch (Exception e) {
                loggr.info("idOrTitle转换异常e:{}", e);
                // 存在异常说明只能查name
                issueRecord.setTitle(idOrTitle);
            }
        }
        if (StringUtils.isNotEmpty(issueStringDTO.getQueryFlag())) {
            issueRecord.setQueryFlag(issueStringDTO.getQueryFlag());
        }
        if (CollectionUtils.isNotEmpty(issueStringDTO.getIssueIds())) {
            if (CollectionUtils.isNotEmpty(issueRecord.getIssueIds())) {
                issueRecord.getIssueIds().retainAll(issueStringDTO.getIssueIds());
            } else {
                issueRecord.setIssueIds(issueStringDTO.getIssueIds());
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void isArchive(Long issueId, Byte isArchive) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        IssueDTO issueDTO = ReflectUtil.copyProperties(issue, IssueDTO.class);
        Optional.ofNullable(issue).orElseThrow(() -> new BusinessException("工作项不存在"));
        if (StateEnum.E.toString().equals(issue.getState())) {
            throw new BusinessException("工作项不存在");
        }
        if(!StageConstant.FirstStageEnum.FINISH_STAGE.getValue().equals(issue.getStageId())){
            throw new BusinessException("非完成阶段不能归档");
        }
        if(IsAchiveEnum.ACHIVEA_FALSE.CODE.equals(isArchive) || IsAchiveEnum.ACHIVEA_TRUE.CODE.equals(issue.getIsArchive())){
            throw new BusinessException("已归档不能修改归档状态");
        }
        if(IssueTypeEnum.TYPE_EPIC.CODE.equals(issue.getIssueType())){
            Integer count = issueMapper.countIsArchive(issueId);
            if(count > NumberConstant.ZERO){
                throw new BusinessException("epic下存在未归档的feature不能归档");
            }
        }
        issue.setIsArchive(isArchive);
        issueMapper.updateByPrimaryKeySelective(issue);
        createIsArhiveHistoryRecords(isArchive,issueDTO);
    }

    /**
     * @Author maxp2
     * @Date 2021/7/14
     * @Description 是否归档增加操作记录
     * @param isArchive
     * @param issueDTO
     * @Return java.util.List<com.yusys.agile.issue.domain.IssueHistoryRecord>
     */
    private List<IssueHistoryRecord> createIsArhiveHistoryRecords(Byte isArchive,IssueDTO issueDTO) {
        List<IssueHistoryRecord> records = new ArrayList<>();
        IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(
                issueDTO.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.ISARCHIVE.getDesc());
        if (null != issueDTO.getIssueId()) {
            nameHistory.setOldValue(IsAchiveEnum.ACHIVEA_FALSE.CODE.toString());
            if (isArchive.equals(IsAchiveEnum.ACHIVEA_TRUE.CODE)) {
                nameHistory.setNewValue(IsAchiveEnum.ACHIVEA_TRUE.CODE.toString());
            }
        }
        records.add(nameHistory);
        issueFactory.dealHistory(records);
        return records;
    }

    /**
     * @return com.yusys.agile.issue.dto.IssueDTO
     * @Author yuzt
     * @Description 根据featureId获取feature及其下的story和task
     * @Date 10:05 上午 2021/7/14
     * @Param [fertureMsg]
     **/
    @Override
    public List<IssueDTO> getIssueDtoByIssueId(Issue issue) throws ExecutionException {
        issue.setState(StateEnum.U.getValue());
        issue.setIssueType(IssueTypeEnum.TYPE_FEATURE.CODE);
        List<IssueDTO> issueDTOList = issueMapper.queryForFerture(issue);
        recursionGetIssues(issueDTOList, issue.getKanbanId());
        return issueDTOList;
    }

    /**
     * @Author yuzt
     * @Description 查询feature及下到task的数据
     * @Date 3:48 上午 2021/7/15
     * @Param [id, issueDTOList]
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     **/
    private List<IssueDTO> getChild(Long id, List<IssueDTO> issueDTOList, Long kanbanId) {
        // 子菜单
        List<IssueDTO> childList = new ArrayList<>();
        for (IssueDTO issueDTO : issueDTOList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (null != issueDTO.getParentId() && issueDTO.getParentId() != 0) {
                if (issueDTO.getParentId().equals(id)) {
                    childList.add(issueDTO);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (IssueDTO issueDTO : childList) {
            //每到任务继续取子菜单
            if (!IssueTypeEnum.TYPE_TASK.CODE.equals(issueDTO.getIssueType())) {
                // 递归
                List<IssueDTO> child = getChild(issueDTO.getIssueId(), issueDTOList, kanbanId);
                issueDTO.setTaskNum(child.size());
                issueDTO.setTaskFinishNum(getIssueFinishNum(issueDTO.getIssueId(),kanbanId));
                issueDTO.setChildren(child);
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
