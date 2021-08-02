package com.yusys.agile.issue.service.impl;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.issue.dao.IssueFilterContentMapper;
import com.yusys.agile.issue.dao.IssueFilterMapper;
import com.yusys.agile.issue.dao.IssueFilterRelatedCheckedMapper;
import com.yusys.agile.issue.dto.IssueFilterContentDTO;
import com.yusys.agile.issue.dto.IssueFilterDTO;
import com.yusys.agile.issue.enums.IssueFilterCodeEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueFilterService;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.agile.set.stage.service.StageService;
import com.alibaba.fastjson.JSON;
import com.yusys.agile.issue.domain.*;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.code.ReflectUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * 自定义过滤器controller实现类
 *
 * @create 2020-06-10 16:28
 */
@Service
public class IssueFilterServiceImpl implements IssueFilterService {

    @Autowired
    private IssueFilterMapper filterMapper;
    @Autowired
    private IssueFilterContentMapper filterContentMapper;

    @Autowired
    private IssueFilterRelatedCheckedMapper relatedCheckedMapper;

    @Autowired
    private IStageService iStageService;

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";
    private static final Logger log = LoggerFactory.getLogger(IssueFilterServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ControllerResponse saveIssueFilter(IssueFilterDTO issueFilterDTO, SecurityDTO securityDTO) {
        try {
            IssueFilter issueFilter = ReflectUtil.copyProperties(issueFilterDTO, IssueFilter.class);
            Long filterId = issueFilter.getFilterId();
            String filterName = issueFilter.getName();
            Byte category = issueFilter.getCategory();
            if (!checkFilterNameUnqiue(filterId, filterName, category)) {
                return ControllerResponse.fail("过滤器名称重复!");
            }
            //
            //0、判断过滤器ID是否存在，存在则将过滤器内容
            if (Optional.ofNullable(filterId).isPresent()) {
                IssueFilter filter = filterMapper.selectByPrimaryKey(filterId);
                issueFilter.setFilterId(null);
                if (filterName.equals(filter.getName())) {
                    deleteIssueFilter(filterId, category);
                }
            }
            Long systemId = securityDTO.getSystemId();
            //1、新增过滤器
            issueFilter.setFilterType(NumberConstant.ONE.byteValue());
            issueFilter.setState(StateEnum.U.toString());
            issueFilter.setSystemId(systemId);
            issueFilter.setCreateTime(new Date());
            issueFilter.setCreateUid(securityDTO.getUserId());
            issueFilter.setCreateName(securityDTO.getUserName());
            filterMapper.insert(issueFilter);

            //2、根据类别、项目ID，人员ID，查询是否有默认的过滤器，存在，则更新为当前过滤器，不存在，则添加当前过滤器为默认过滤器
            IssueFilterRelatedCheckedExample checkedExample = new IssueFilterRelatedCheckedExample();
            IssueFilterRelatedCheckedExample.Criteria issueFilterRelatedCheckedCriteria = checkedExample.createCriteria();
            issueFilterRelatedCheckedCriteria.andCategoryEqualTo(issueFilter.getCategory())
                    .andCreateUidEqualTo(securityDTO.getUserId());
            if(null != systemId){
                issueFilterRelatedCheckedCriteria.andSystemIdEqualTo(systemId);
            }
            List<IssueFilterRelatedChecked> issueFilterRelatedCheckeds = relatedCheckedMapper.selectByExample(checkedExample);
            if (CollectionUtils.isNotEmpty(issueFilterRelatedCheckeds)) {
                IssueFilterRelatedChecked relatedChecked = issueFilterRelatedCheckeds.get(0);
                relatedChecked.setFilterId(issueFilter.getFilterId());
                relatedCheckedMapper.updateByPrimaryKeySelective(relatedChecked);
            } else {
                IssueFilterRelatedChecked relatedChecked = new IssueFilterRelatedChecked();
                relatedChecked.setFilterId(issueFilter.getFilterId());
                relatedChecked.setIdCheck(NumberConstant.ONE.byteValue());
                relatedChecked.setCategory(issueFilter.getCategory());
                relatedChecked.setSystemId(systemId);
                relatedChecked.setCreateUid(securityDTO.getUserId());
                relatedChecked.setCreateTime(new Date());
                relatedCheckedMapper.insert(relatedChecked);
            }

            //3、保存过滤器筛选内容
            List<IssueFilterContentDTO> filterContentList = issueFilterDTO.getFilterContentList();
            if (CollectionUtils.isNotEmpty(filterContentList)) {
                List<IssueFilterContent> issueFilterContents = ReflectUtil.copyProperties4List(filterContentList, IssueFilterContent.class);
                issueFilterContents.forEach(issueFilterContent -> {
                    issueFilterContent.setFilterId(issueFilter.getFilterId());
                    filterContentMapper.insert(issueFilterContent);
                });
            }
            return ControllerResponse.success("过滤器新增成功");
        } catch (Exception e) {
            log.error("过滤器新增异常:{}", e);
            return ControllerResponse.success("过滤器新增异常");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ControllerResponse deleteIssueFilter(Long filterId, Byte category) {

        IssueFilter issueFilter = filterMapper.selectByPrimaryKey(filterId);
        Optional.ofNullable(issueFilter).orElseThrow(() -> new BusinessException("过滤器不存在!"));

        Long systemId = issueFilter.getSystemId();
        Long createUid = issueFilter.getCreateUid();
        //1、删除过滤器主表内容
        filterMapper.deleteByPrimaryKey(filterId);

        //2、删除过滤器内容
        IssueFilterContentExample contentExample = new IssueFilterContentExample();
        contentExample.createCriteria().andFilterIdEqualTo(filterId);
        filterContentMapper.deleteByExample(contentExample);

        //3、判断是否删除的当前过滤起是否为默认过滤器，是则进行删除。
        IssueFilterRelatedCheckedExample relatedCheckedExample = new IssueFilterRelatedCheckedExample();
        IssueFilterRelatedCheckedExample.Criteria relatedCheckedExampleCriteria = relatedCheckedExample.createCriteria();
        relatedCheckedExampleCriteria.andFilterIdEqualTo(filterId)
                .andCategoryEqualTo(category)
                .andCreateUidEqualTo(createUid);
        if(null != systemId){
            relatedCheckedExampleCriteria.andSystemIdEqualTo(systemId);
        }
        relatedCheckedMapper.deleteByExample(relatedCheckedExample);
        return ControllerResponse.success("过滤器删除成功!");
    }

    @Override
    public ControllerResponse getIssueFilters(Byte category, SecurityDTO securityDTO) {
        try {
            //系统ID
            Long userId =  securityDTO.getUserId();
            IssueFilterExample filterExample = new IssueFilterExample();
            IssueFilterExample.Criteria criteriaT = filterExample.createCriteria();
            criteriaT.andCategoryEqualTo(category)
                    .andCreateUidEqualTo(userId).andStateEqualTo(StateEnum.U.toString());
            String  tenantCode =  securityDTO.getTenantCode();
            if(StringUtils.isNotEmpty(tenantCode)){
                criteriaT.andTenantCodeEqualTo(tenantCode);
            }
            IssueFilterExample filterExample1 = new IssueFilterExample();
            IssueFilterExample.Criteria criteria = filterExample1.createCriteria();
            criteria.andCategoryEqualTo(NumberConstant.ZERO.byteValue())
                    .andStateEqualTo(StateEnum.U.toString());

            filterExample.or(criteria);
            filterExample.setOrderByClause(CREATE_TIME_DESC);

            List<IssueFilter> issueFilters = filterMapper.selectByExample(filterExample);
            List<IssueFilterDTO> issueFilterDTOS = ReflectUtil.copyProperties4List(issueFilters, IssueFilterDTO.class);
            //组装过滤器内容
            if (CollectionUtils.isNotEmpty(issueFilterDTOS)) {
                IssueFilterRelatedChecked relatedChecked = null;
                IssueFilterRelatedCheckedExample checkedExample = new IssueFilterRelatedCheckedExample();
                checkedExample.createCriteria()
                        .andCategoryEqualTo(category)
                        .andCreateUidEqualTo(securityDTO.getUserId());
                List<IssueFilterRelatedChecked> issueFilterRelatedCheckeds = relatedCheckedMapper.selectByExample(checkedExample);
                List<StageInstance> stageList = iStageService.getStages(new Integer(category.toString()), null, null);
                if (CollectionUtils.isNotEmpty(issueFilterRelatedCheckeds)) {
                    relatedChecked = issueFilterRelatedCheckeds.get(0);
                }
                for (IssueFilterDTO filterDTO : issueFilterDTOS) {
                    if (Optional.ofNullable(relatedChecked).isPresent()
                            && filterDTO.getFilterId().equals(relatedChecked.getFilterId())) {
                        filterDTO.setIdCheck(relatedChecked.getIdCheck());
                    } else {
                        filterDTO.setIdCheck(NumberConstant.ZERO.byteValue());
                    }
                    IssueFilterContentExample contentExample = new IssueFilterContentExample();
                    contentExample.createCriteria().andFilterIdEqualTo(filterDTO.getFilterId());
                    List<IssueFilterContent> issueFilterContents = filterContentMapper.selectByExampleWithBLOBs(contentExample);
                    if (CollectionUtils.isNotEmpty(issueFilterContents)) {
                        List<IssueFilterContentDTO> issueFilterContentDTOS = ReflectUtil.copyProperties4List(issueFilterContents, IssueFilterContentDTO.class);
                        if (NumberConstant.ZERO.toString().equals(filterDTO.getFilterType().toString())) {
                            setFilterContentValues(issueFilterContentDTOS, stageList, securityDTO);
                        }
                        filterDTO.setFilterContentList(issueFilterContentDTOS);
                    }
                }
            }
            return ControllerResponse.success(issueFilterDTOS);
        } catch (Exception e) {
            log.error("获取过滤器列表数据异常:{}", e);
            return ControllerResponse.fail("获取过滤器列表数据异常!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ControllerResponse putFilterCheckStatus(Long filterId, Byte category, SecurityDTO securityDTO) {
        IssueFilter filter = filterMapper.selectByPrimaryKey(filterId);
        Optional.ofNullable(filter).orElseThrow(() -> new BusinessException("过滤器不存在!"));

        IssueFilterRelatedCheckedExample checkedExample = new IssueFilterRelatedCheckedExample();
        IssueFilterRelatedCheckedExample.Criteria issueFilterRelatedCheckedCriteria = checkedExample.createCriteria();
        issueFilterRelatedCheckedCriteria
                .andCategoryEqualTo(category)
                .andCreateUidEqualTo(securityDTO.getUserId());
        if(Optional.ofNullable(securityDTO.getSystemId()).isPresent()){
            issueFilterRelatedCheckedCriteria.andSystemIdEqualTo(securityDTO.getSystemId());
        }
        List<IssueFilterRelatedChecked> issueFilterRelatedCheckeds = relatedCheckedMapper.selectByExample(checkedExample);
        //1、判断该category类别/项目ID/人员是否有默认的选中的过滤器。存在，则更新当前过滤器，反之，则新增一条默认选中过滤器
        if (CollectionUtils.isNotEmpty(issueFilterRelatedCheckeds)) {
            IssueFilterRelatedChecked relatedChecked = issueFilterRelatedCheckeds.get(0);
            relatedChecked.setFilterId(filterId);
            relatedCheckedMapper.updateByPrimaryKeySelective(relatedChecked);
        } else {
            IssueFilterRelatedChecked relatedChecked = new IssueFilterRelatedChecked();
            relatedChecked.setFilterId(filterId);
            relatedChecked.setIdCheck(NumberConstant.ONE.byteValue());
            relatedChecked.setCategory(category);
            relatedChecked.setSystemId(securityDTO.getSystemId());
            relatedChecked.setCreateUid(securityDTO.getUserId());
            relatedChecked.setCreateTime(new Date());
            relatedCheckedMapper.insert(relatedChecked);
        }
        return ControllerResponse.success("过滤器默认设置成功!");
    }

    private void setFilterContentValues(List<IssueFilterContentDTO> issueFilterContentDTOS, List<StageInstance> stageList, SecurityDTO securityDTO) {
        Long userId = securityDTO.getUserId();
        String userName = securityDTO.getUserName();

        List<JSONObject> objects = new ArrayList<>();
        issueFilterContentDTOS.forEach(contentDTO -> {
            JSONObject jsonObject = new JSONObject();
            /**
             * 1	全部
             * 2	指派给我的
             * 3	我关注的
             * 4	我创建的
             * 5	我参与过的
             * 6	未加入迭代的
             * 7	未完成的
             * 8	已超时的
             */
            switch (contentDTO.getFilterId().intValue()) {
                case 1:
                    jsonObject.put("key", "ALL");
                    jsonObject.put("value", "全部");
                    break;
                case 2:
                    if (IssueFilterCodeEnum.HANDLER.getValue().equals(contentDTO.getFieldCode())) {
                        jsonObject.put("key", userId + "");
                        jsonObject.put("value", userName);
                    }
                    break;
                case 3:
                    if (IssueFilterCodeEnum.ISCOLLECT.getValue().equals(contentDTO.getFieldCode())) {
                        jsonObject.put("key", 1);
                        jsonObject.put("value", "收藏");
                    }
                    break;
                case 4:
                case 5:
                    if (IssueFilterCodeEnum.CREATEUID.getValue().equals(contentDTO.getFieldCode())) {
                        jsonObject.put("key", userId + "");
                        jsonObject.put("value", userName);
                    }
                    break;
                case 6:
                    if (IssueFilterCodeEnum.SPRINTID.getValue().equals(contentDTO.getFieldCode())) {
                        jsonObject.put("key", "null");
                        jsonObject.put("value", "未设置内容");
                    }
                    break;
                case 7:
                    if (IssueFilterCodeEnum.COMPLETION.getValue().equals(contentDTO.getFieldCode())) {
                        contentDTO.setFieldCode(IssueFilterCodeEnum.STAGEID.getValue());
                        contentDTO.setFieldName(IssueFilterCodeEnum.STAGEID.getName());
                        List<List<JSONObject>> fieldValues = dealStageList(stageList);
                        contentDTO.setFieldValue(JSON.toJSONString(fieldValues));
                    }
                    break;
                case 8:
                    if (IssueFilterCodeEnum.ENDDATE.getValue().equals(contentDTO.getFieldCode())) {
                        contentDTO.setFieldName(IssueFilterCodeEnum.ENDDATE.getName());
                        String dateStr = "1970-01-02";
                        LocalDate localDate = LocalDate.parse(dateStr);
                        long startMillis = LocalDateTime.of(localDate, LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();

                        JSONObject startDateJSON = new JSONObject();
                        startDateJSON.put("key", startMillis);
                        startDateJSON.put("value", dateStr);

                        LocalDate date = LocalDate.now();
                        LocalDate localDate1 = date.minusDays(1);

                        localDate = LocalDate.parse(localDate1.toString());
                        long endMillis = LocalDateTime.of(localDate, LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();

                        JSONObject endDateJSON = new JSONObject();
                        endDateJSON.put("key", endMillis);
                        endDateJSON.put("value", localDate1 + "");

                        List<Object> dateList = new ArrayList<>();
                        dateList.add(startDateJSON);
                        dateList.add(endDateJSON);
                        contentDTO.setFieldValue(JSON.toJSONString(dateList));

                    }
                    if (IssueFilterCodeEnum.COMPLETION.getValue().equals(contentDTO.getFieldCode())) {
                        contentDTO.setFieldCode(IssueFilterCodeEnum.STAGEID.getValue());
                        contentDTO.setFieldName(IssueFilterCodeEnum.STAGEID.getName());
                        List<List<JSONObject>> fieldValues = dealStageList(stageList);
                        contentDTO.setFieldValue(JSON.toJSONString(fieldValues));
                    }
                    break;
                default:
                    break;

            }
            if (!IssueFilterCodeEnum.STAGEID.getValue().equals(contentDTO.getFieldCode()) && !IssueFilterCodeEnum.ENDDATE.getValue().equals(contentDTO.getFieldCode())) {
                objects.add(jsonObject);
                String fieldValue = JSON.toJSONString(objects);
                contentDTO.setFieldValue(fieldValue);
            }
        });
    }

    /**
     * "fieldValue": "[
     * [{\"key\":1,\"value\":\"就绪阶段\",\"level\":1}],
     * [{\"key\":2,\"value\":\"分析阶段\",\"level\":1},{\"key\":104,\"value\":\"未开始\",\"level\":2},{\"key\":105,\"value\":\"进行中\",\"level\":2},{\"key\":106,\"value\":\"已完成\",\"level\":2}],
     * [{\"key\":3,\"value\":\"设计阶段\",\"level\":1},{\"key\":107,\"value\":\"未开始\",\"level\":2},{\"key\":108,\"value\":\"进行中\",\"level\":2},{\"key\":109,\"value\":\"已完成\",\"level\":2}],
     * [{\"key\":4,\"value\":\"开发阶段\",\"level\":1},{\"key\":110,\"value\":\"未开始\",\"level\":2},{\"key\":111,\"value\":\"进行中\",\"level\":2},{\"key\":112,\"value\":\"已完成\",\"level\":2}],
     * [{\"key\":5,\"value\":\"测试阶段\",\"level\":1},{\"key\":113,\"value\":\"未开始\",\"level\":2},{\"key\":114,\"value\":\"进行中\",\"level\":2},{\"key\":115,\"value\":\"已完成\",\"level\":2}],
     * [{\"key\":6,\"value\":\"上线阶段\",\"level\":1},{\"key\":116,\"value\":\"未开始\",\"level\":2},{\"key\":117,\"value\":\"进行中\",\"level\":2},{\"key\":118,\"value\":\"已完成\",\"level\":2}]
     * ]"
     *
     * @param stageList
     * @return
     */
    private List<List<JSONObject>> dealStageList(List<StageInstance> stageList) {
        List<List<JSONObject>> fieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(stageList)) {
            stageList.forEach(stageInstance -> {
                //排除stageId不等于已完成阶段7
                if (!NumberConstant.EIGHT.equals(stageInstance.getStageId())) {
                    List<JSONObject> oneStages = new ArrayList<>();
                    JSONObject oneJson = new JSONObject();
                    oneJson.put("key", stageInstance.getStageId());
                    oneJson.put("value", stageInstance.getStageName());
                    oneJson.put("level", 1);

                    List<KanbanStageInstance> secondStages = stageInstance.getSecondStages();
                    if (CollectionUtils.isNotEmpty(secondStages)) {
                        List<List<JSONObject>> centerStages = new ArrayList<>();
                        secondStages.forEach(secondStage -> {
                            List<JSONObject> twoStages = new ArrayList<>();
                            JSONObject secondJson = new JSONObject();
                            secondJson.put("key", secondStage.getStageId());
                            secondJson.put("value", secondStage.getStageName());
                            secondJson.put("level", 2);
                            JSONObject onesJson = new JSONObject();
                            onesJson.put("key", stageInstance.getStageId());
                            onesJson.put("value", stageInstance.getStageName());
                            onesJson.put("level", 1);
                            twoStages.add(onesJson);
                            twoStages.add(secondJson);
                            centerStages.add(twoStages);
                        });
                        fieldValues.addAll(centerStages);
                    } else {
                        oneStages.add(oneJson);
                        fieldValues.add(oneStages);
                    }
                }
            });
        }
        return fieldValues;
    }

    private Boolean checkFilterNameUnqiue(Long filterId, String filterName, Byte category) {
        Boolean flag = true;
        IssueFilterExample filterExample = new IssueFilterExample();
        IssueFilterExample.Criteria criteria = filterExample.createCriteria();
        criteria.andCategoryEqualTo(category)
                .andNameEqualTo(filterName);
        if (Optional.ofNullable(filterId).isPresent()) {
            criteria.andFilterIdNotEqualTo(filterId);
        }
        List<IssueFilter> issueFilters = filterMapper.selectByExample(filterExample);
        if (CollectionUtils.isNotEmpty(issueFilters)) {
            flag = false;
        }
        return flag;

    }
}

