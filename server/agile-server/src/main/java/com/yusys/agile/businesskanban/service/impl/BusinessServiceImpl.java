package com.yusys.agile.businesskanban.service.impl;


import com.yusys.agile.businesskanban.dao.BusinessAttachmentMapper;
import com.yusys.agile.businesskanban.dao.BusinessHistoryRecordMapper;
import com.yusys.agile.businesskanban.dao.BusinessMapper;
import com.yusys.agile.businesskanban.dto.BusinessAttachmentDTO;
import com.yusys.agile.businesskanban.dto.BusinessDTO;
import com.yusys.agile.businesskanban.dto.BusinessHistoryRecordDTO;
import com.yusys.agile.businesskanban.dto.BusinessResultDTO;
import com.yusys.agile.businesskanban.service.BusinessService;
import com.yusys.agile.businesskanban.utils.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yusys.agile.businesskanban.domain.*;
import com.yusys.agile.businesskanban.enums.*;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Date: 2021/2/1
 * @Description: 事务服务
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger loggr = LoggerFactory.getLogger(BusinessServiceImpl.class);
    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private BusinessHistoryRecordMapper businessHistoryRecordMapper;
    @Resource
    private BusinessAttachmentMapper businessAttachmentMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessDTO createBusiness(BusinessDTO businessDTO) {
        BusinessWithBLOBs business = ReflectUtil.copyProperties(businessDTO,BusinessWithBLOBs.class);
        int result = businessMapper.insert(business);
        loggr.info("BusinessServiceImpl method createBusiness count :{}",result);
        //上传附件
        List<BusinessAttachmentDTO> attachmentDtos = businessDTO.getAttachmentDTOS();
        if (attachmentDtos != null && !attachmentDtos.isEmpty()) {
            List<BusinessAttachment> attachments = Lists.newArrayList();
            ReflectUtil.copyProperties(attachmentDtos,attachments);
            for (BusinessAttachment attachment : attachments) {
                attachment.setBusinessId(business.getBusinessId());
            }
            businessAttachmentMapper.batchCreate(attachments);
        }
        BusinessWithBLOBs bloBs = businessMapper.selectByPrimaryKey(business.getBusinessId());
        BusinessDTO businessResult = ReflectUtil.copyProperties(bloBs, BusinessDTO.class);
        return businessResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBusiness(Long businessId) {
        BusinessWithBLOBs bloBs = businessMapper.selectByPrimaryKey(businessId);
        if(bloBs == null){
            return -1;
        }
        bloBs.setState(StateEnum.E.getValue());
        int result = businessMapper.updateByPrimaryKeyWithBLOBs(bloBs);
        //删除历史记录
        BusinessHistoryRecordExample historyRecordExample = new BusinessHistoryRecordExample();
        historyRecordExample.createCriteria().andBusinessIdEqualTo(businessId);
        businessHistoryRecordMapper.deleteByExample(historyRecordExample);
        //删除附件
        BusinessAttachmentExample attachmentExample = new BusinessAttachmentExample();
        attachmentExample.createCriteria().andBusinessIdEqualTo(businessId);
        businessAttachmentMapper.deleteByExample(attachmentExample);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessWithBLOBs updateBusiness(BusinessDTO businessDTO) throws Exception {
        BusinessWithBLOBs oldBusinessWithBLOBs = businessMapper.selectByPrimaryKey(businessDTO.getBusinessId());
        BusinessDTO oldBusinessDTO = new BusinessDTO();
        ReflectUtil.copyProperties(oldBusinessWithBLOBs,oldBusinessDTO);
        List<BusinessHistoryRecordWithBLOBs> historic;
        try {
            historic = generateHistory(businessDTO, oldBusinessDTO);
        }catch (Exception e){
            loggr.error("BusinessServiceImpl method updateBusiness generate history error :{}",e.getMessage());
            throw new BusinessException("生成历史记录过程中异常!");
        }
        // attachment
        boolean isEdit = false;
        List<BusinessAttachmentDTO> attachmentDTOS = businessDTO.getAttachmentDTOS();
        if(CollectionUtils.isNotEmpty(attachmentDTOS)){
            List<BusinessAttachment> attachments = ReflectUtil.copyProperties4List(attachmentDTOS, BusinessAttachment.class);

            BusinessAttachmentExample attachmentExample = new BusinessAttachmentExample();
            attachmentExample.createCriteria().andBusinessIdEqualTo(businessDTO.getBusinessId());
            List<BusinessAttachment> oldAttachments = businessAttachmentMapper.selectByExample(attachmentExample);
            if (CollectionUtils.isNotEmpty(attachments)) {
                List<BusinessAttachment> newAttachments = new ArrayList<>();
                for (BusinessAttachment temp : oldAttachments) {
                    if (!isContain(attachments, temp)) {
                        isEdit = true;
                        businessAttachmentMapper.deleteByPrimaryKey(temp.getAttachmentId());
                    }
                }
                for (BusinessAttachment sa : attachments) {
                    if (sa.getAttachmentId() == null) {
                        sa.setBusinessId(businessDTO.getBusinessId());
                        newAttachments.add(sa);
                    }
                }
                if (CollectionUtils.isNotEmpty(newAttachments)) {
                    isEdit = true;
                    businessAttachmentMapper.batchCreate(newAttachments);
                }
            } else {
                if (CollectionUtils.isNotEmpty(oldAttachments)) {
                    isEdit = true;
                    businessAttachmentMapper.deleteByExample(attachmentExample);
                }
            }
            if (isEdit) {
                BusinessHistoryRecordWithBLOBs attachmentRecord = new BusinessHistoryRecordWithBLOBs("附件", businessDTO.getBusinessId(),UserThreadLocalUtil.getUserInfo().getUserId(), (byte)0);
                attachmentRecord.setNewValue(JSON.toJSONString(attachments));
                attachmentRecord.setOldValue(JSON.toJSONString(oldAttachments));
                historic.add(attachmentRecord);
            }
        }
        if(CollectionUtils.isNotEmpty(historic)){
            businessHistoryRecordMapper.batchCreate(historic);
        }
        //更新事务
        BusinessWithBLOBs businessWithBLOBs = ReflectUtil.copyProperties(businessDTO,BusinessWithBLOBs.class);
        businessWithBLOBs.setState(StateEnum.U.getValue());
        Long businessOwner = businessWithBLOBs.getBusinessOwner();
        Byte businessState = businessWithBLOBs.getBusinessState();
        Date startTime = oldBusinessWithBLOBs.getStartTime();
        //1、判断实际开始日期为空且事务状态不等于未领取，则设置实际开始日期
        if(!Optional.ofNullable(startTime).isPresent()
                && !Byte.valueOf(BusinessState.UNCLAIMED.getNodeCode().toString()).equals(businessState)){
            businessWithBLOBs.setStartTime(new Date());
        }

        //判断到已完成状态，如果实际开始结束则等于当前日期
        if(Optional.ofNullable(businessOwner).isPresent() && Byte.valueOf(BusinessState.COMPLETE.getNodeCode().toString()).equals(businessState)){
            businessWithBLOBs.setEndTime(new Date());
        }else{
            //回退到未领取状态，清空开始时间和结束时间
            if(Byte.valueOf(BusinessState.UNCLAIMED.getNodeCode().toString()).equals(businessState)){
                businessWithBLOBs.setStartTime(null);
                businessWithBLOBs.setEndTime(null);
            }else{
                //回退到已完成外状态，清空结束时间
                businessWithBLOBs.setEndTime(null);
            }
        }

        businessMapper.updateByPrimaryKeyWithBLOBs(businessWithBLOBs);
        return businessWithBLOBs;
    }

    private boolean isContain(List<BusinessAttachment> attachments, BusinessAttachment attachment) {
        boolean res = false;
        for (BusinessAttachment sa : attachments) {
            if (attachment.getAttachmentId().equals(sa.getAttachmentId())) {
                res = true;
            }
        }
        return res;
    }


    /**
     * @Date: 2021/2/1
     * @Description: 修改事务成历史记录
     * @Param: [businessDTO, oldBusinessDTO]
     * @Return: java.util.List<com.yusys.agile.businesskanban.domain.BusinessHistoryRecordWithBLOBs>
     *
     */
    private List<BusinessHistoryRecordWithBLOBs> generateHistory(BusinessDTO businessDTO, BusinessDTO oldBusinessDTO) {
        List<BusinessHistoryRecordWithBLOBs> records = Lists.newArrayList();
        if(null == businessDTO || null == oldBusinessDTO){
            return records;
        }
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //标题
        if (!ObjectUtil.equals(businessDTO.getBusinessName(), oldBusinessDTO.getBusinessName())) {
            BusinessHistoryRecordWithBLOBs nameHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.BUSINESSNAME.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            nameHistory.setOldValue(oldBusinessDTO.getBusinessName());
            nameHistory.setNewValue(businessDTO.getBusinessName());
            records.add(nameHistory);
        }

        //描述信息
        if (!ObjectUtil.equals(businessDTO.getBusinessDesc(), oldBusinessDTO.getBusinessDesc())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.BUSINESSDESC.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getBusinessDesc());
            descHistory.setNewValue(businessDTO.getBusinessDesc());
            records.add(descHistory);
        }

        //类型
        Map<Long,String> businessTypeMap = BusinessType.getBusinessTypeMap();
        if (!ObjectUtil.equals(businessDTO.getBusinessType(), oldBusinessDTO.getBusinessType())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.BUSINESSTYPE.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(businessTypeMap.get(oldBusinessDTO.getBusinessType()));
            descHistory.setNewValue(businessTypeMap.get(businessDTO.getBusinessType()));
            records.add(descHistory);
        }

        //状态
        Map<Byte,String> businessStateMap = BusinessState.getBusinessStateMap();
        if (!ObjectUtil.equals(businessDTO.getBusinessState(), oldBusinessDTO.getBusinessState())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.BUSINESSTATE.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(businessStateMap.get(oldBusinessDTO.getBusinessState()));
            descHistory.setNewValue(businessStateMap.get(businessDTO.getBusinessState()));
            records.add(descHistory);
        }

        //领取人
        if (!ObjectUtil.equals(businessDTO.getBusinessOwnerName(), oldBusinessDTO.getBusinessOwnerName())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.BUSINESSOWNER.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getBusinessOwnerName());
            descHistory.setNewValue(businessDTO.getBusinessOwnerName());
            records.add(descHistory);
        }

        //优先级
        Map<Byte,String> levelMap = BusinessLevel.getBusinessLevelMap();
        if (!ObjectUtil.equals(businessDTO.getBusinessLevel(), oldBusinessDTO.getBusinessLevel())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.BUSINESSLEVEL.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(levelMap.get(oldBusinessDTO.getBusinessLevel()));
            descHistory.setNewValue(levelMap.get(businessDTO.getBusinessLevel()));
            records.add(descHistory);
        }

        //重要程度
        Map<Byte,String> importanceMap = BusinessImportance.getBusinessImportanceMap();
        if (!ObjectUtil.equals(businessDTO.getBusinessImportance(), oldBusinessDTO.getBusinessImportance())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.IMPORTANCE.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(importanceMap.get(oldBusinessDTO.getBusinessImportance()));
            descHistory.setNewValue(importanceMap.get(businessDTO.getBusinessImportance()));
            records.add(descHistory);
        }

        //实际开始日期
        if (!ObjectUtil.equals(businessDTO.getStartTime(), oldBusinessDTO.getStartTime())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.STARTTIME.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getStartTime() == null ?
                    null:format.format(oldBusinessDTO.getStartTime()));
            descHistory.setNewValue(businessDTO.getStartTime() == null?
                    null:format.format(businessDTO.getStartTime()));
            records.add(descHistory);
        }

        //实际结束日期
        if (!ObjectUtil.equals(businessDTO.getEndTime(), oldBusinessDTO.getEndTime())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.ENDTIME.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getEndTime() == null ?
                    null:format.format(oldBusinessDTO.getEndTime()));
            descHistory.setNewValue(businessDTO.getEndTime() == null?
                    null:format.format(businessDTO.getEndTime()));
            records.add(descHistory);
        }

        //预计工时
        if (!ObjectUtil.equals(businessDTO.getPlanWorkload(), oldBusinessDTO.getPlanWorkload())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.PLANWORKLOAD.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getPlanWorkload() == null ? "0" : oldBusinessDTO.getPlanWorkload().toString());
            descHistory.setNewValue(businessDTO.getPlanWorkload() == null ? "0" : businessDTO.getPlanWorkload().toString());
            records.add(descHistory);
        }

        //实际工时
        if (!ObjectUtil.equals(businessDTO.getActualWorkload(), oldBusinessDTO.getActualWorkload())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.ACTUALWORKLOAD.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getActualWorkload() == null? "0":oldBusinessDTO.getActualWorkload().toString());
            descHistory.setNewValue(businessDTO.getActualWorkload() == null ? "0":businessDTO.getActualWorkload().toString());
            records.add(descHistory);
        }

        //预计开始实际
        if (!ObjectUtil.equals(businessDTO.getPlanStartTime(), oldBusinessDTO.getPlanStartTime())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.PLANSTARTTIME.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getPlanStartTime() == null ?
                    null:format.format(oldBusinessDTO.getPlanStartTime()));
            descHistory.setNewValue(businessDTO.getPlanStartTime() == null?
                    null:format.format(businessDTO.getPlanStartTime()));
            records.add(descHistory);
        }

        //预计结束时间
        if (!ObjectUtil.equals(businessDTO.getPlanEndTime(), oldBusinessDTO.getPlanEndTime())) {
            BusinessHistoryRecordWithBLOBs descHistory =
                    new BusinessHistoryRecordWithBLOBs(
                            BusinessField.PLANENDTIME.getDesc(),businessDTO.getBusinessId(),userId,(byte) 0);
            descHistory.setOldValue(oldBusinessDTO.getPlanEndTime() == null ?
                    null:format.format(oldBusinessDTO.getPlanEndTime()));
            descHistory.setNewValue(businessDTO.getPlanEndTime() == null?
                    null:format.format(businessDTO.getPlanEndTime()));
            records.add(descHistory);
        }
        return records;
    }

    @Override
    public List<BusinessHistoryRecordDTO> getByBusinessId(Long businessId, Integer pageNum, Integer pageSize) {

        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum,pageSize);
        }
        List<BusinessHistoryRecordDTO> historyRecordDTOS = Lists.newArrayList();
        BusinessHistoryRecordExample  historyRecordExample = new BusinessHistoryRecordExample();
        historyRecordExample.createCriteria().andBusinessIdEqualTo(businessId);
        List<BusinessHistoryRecordWithBLOBs> businessHistoryRecordWithBLOBs = businessHistoryRecordMapper.selectByExampleWithBLOBs(historyRecordExample);
        if(businessHistoryRecordWithBLOBs.isEmpty()){
            return historyRecordDTOS;
        }
        ReflectUtil.copyProperties(businessHistoryRecordWithBLOBs,historyRecordDTOS);
        return historyRecordDTOS;
    }

    @Override
    public List<BusinessResultDTO> getBusinessInfo(BusinessDTO businessDTO) throws Exception {
        BusinessExample example = new BusinessExample();
        BusinessExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue());

        if(null != businessDTO.getKanbanId()){
            criteria.andKanbanIdEqualTo(businessDTO.getKanbanId());
        }

        if(businessDTO.getBusinessName() != null){
            criteria.andBusinessNameEqualTo(businessDTO.getBusinessName());
        }
        if(businessDTO.getBusinessType() != null){
            criteria.andBusinessTypeEqualTo(businessDTO.getBusinessType());
        }
        if(businessDTO.getBusinessState() != null){
            criteria.andBusinessStateEqualTo(businessDTO.getBusinessState());
        }
        if(businessDTO.getBusinessOwner() != null){
            criteria.andBusinessOwnerEqualTo(businessDTO.getBusinessOwner());
        }
        if(businessDTO.getPlanStartTime() != null && businessDTO.getPlanStartTimeEnd() != null){
            criteria.andPlanStartTimeBetween(businessDTO.getPlanStartTime(), businessDTO.getPlanStartTimeEnd());
        }
        if(businessDTO.getPlanEndTime() != null && businessDTO.getPlanEndTimeEnd() != null){
            criteria.andPlanEndTimeBetween(businessDTO.getPlanEndTime(), businessDTO.getPlanEndTimeEnd());
        }

        example.setOrderByClause("update_time desc,create_time desc");
        List<BusinessWithBLOBs> business = businessMapper.selectByExampleWithBLOBs(example);
        List<BusinessResultDTO> array = Lists.newArrayList();
        if(CollectionUtils.isEmpty(business)){
            return array;
        }
        //处理人员
        List<Long> userIds = Lists.newArrayList();
        business.forEach(bloBs->{
            if(bloBs.getBusinessOwner() != null){
                if(!userIds.contains(bloBs.getBusinessOwner())){
                    userIds.add(bloBs.getBusinessOwner());
                }
            }
        });
        Map<Long,String> userMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(userIds)){
            Collections.sort(userIds);
            List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIds);
            ssoUsers.forEach(user->{
                userMap.put(user.getUserId(),user.getUserName());
            });
        }
        //拼装数据
        //如果按照类型排序
        if(businessDTO.getSelectType()==0){
            BusinessType[] values = BusinessType.values();
            for(BusinessType type:values){
                List<BusinessWithBLOBs>  bsList = new ArrayList<>();
                BusinessResultDTO result = new BusinessResultDTO();
                for(BusinessWithBLOBs bloBs: business){
                    if(type.getNodeCode().equals(bloBs.getBusinessType())){
                        bloBs.setBusinessOwnerName(userMap.get(bloBs.getBusinessOwner()));
                        bsList.add(bloBs);
                    }
                }
                if(CollectionUtils.isNotEmpty(bsList)){
                    result.setId(type.getNodeCode());
                    result.setTitle(type.getNodeMsg());
                    List<BusinessDTO> businessDTOs = ReflectUtil.copyProperties4List(bsList,BusinessDTO.class);
                    result.setChildren(businessDTOs);
                    array.add(result);
                }
            }
            //根据人员
        }else if(businessDTO.getSelectType()==1){
            //找到未领取的事务
            List<BusinessWithBLOBs> unclaimed = new ArrayList<>();
            for(BusinessWithBLOBs bloBs:business){
                if(bloBs.getBusinessOwner() == null || bloBs.getBusinessOwner() == -1){
                    unclaimed.add(bloBs);
                }
            }
            BusinessResultDTO result = new BusinessResultDTO();
            result.setId(-1L);
            result.setTitle(BusinessState.UNCLAIMED.getNodeMsg());
            List<BusinessDTO> businessDTOs = Lists.newArrayList();
            ReflectUtil.copyProperties(unclaimed,businessDTOs);
            result.setChildren(businessDTOs);
            array.add(result);
            if(unclaimed.size() == business.size()){
                return array;
            }
            //根据人员排事务
            //已领取的事务
            for(Long userId : userIds){
                List<BusinessWithBLOBs> claimed = Lists.newArrayList();
                for(BusinessWithBLOBs bloBs:business){
                    if(userId.equals(bloBs.getBusinessOwner())){
                        bloBs.setBusinessOwnerName(userMap.get(userId));
                        claimed.add(bloBs);
                    }
                }
                if(CollectionUtils.isNotEmpty(claimed)){
                    String userName = userMap.get(userId);
                    BusinessResultDTO businessResultDTO = new BusinessResultDTO();
                    businessResultDTO.setId(userId);
                    businessResultDTO.setTitle(userName);
                    List<BusinessDTO> businessDTOS = ReflectUtil.copyProperties4List(claimed,BusinessDTO.class);
                    businessResultDTO.setChildren(businessDTOS);
                    array.add(businessResultDTO);
                }
            }
        }
        return array;
    }

    @Override
    public List<BusinessDTO> getBusinessInfList(BusinessDTO businessDTO) {

        if (null != businessDTO.getPageNum() && null != businessDTO.getPageSize()) {
            PageHelper.startPage(businessDTO.getPageNum(),businessDTO.getPageSize());
        }
        BusinessExample example = new BusinessExample();
        BusinessExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue());
        if(null != businessDTO.getBusinessName()){
            criteria.andBusinessNameEqualTo(businessDTO.getBusinessName());
        }
        if(null != businessDTO.getBusinessType()){
            criteria.andBusinessTypeEqualTo(businessDTO.getBusinessType());
        }
        if(null != businessDTO.getBusinessState()){
            criteria.andBusinessStateEqualTo(businessDTO.getBusinessState());
        }
        if(null != businessDTO.getBusinessOwner()){
            criteria.andBusinessOwnerEqualTo(businessDTO.getBusinessOwner());
        }
        if(null != businessDTO.getPlanStartTime() && null != businessDTO.getPlanStartTimeEnd()){
            criteria.andPlanStartTimeBetween(businessDTO.getPlanStartTime(), businessDTO.getPlanStartTimeEnd());
        }
        if(null != businessDTO.getPlanEndTime() &&  null != businessDTO.getPlanEndTimeEnd()){
            criteria.andPlanEndTimeBetween(businessDTO.getPlanEndTime(), businessDTO.getPlanEndTimeEnd());
        }

        if(null != businessDTO.getKanbanId()){
            criteria.andKanbanIdEqualTo(businessDTO.getKanbanId());
        }

        example.setOrderByClause("create_time desc");
        List<BusinessWithBLOBs> business = businessMapper.selectByExampleWithBLOBs(example);
        List<BusinessDTO> businessDTOList = Lists.newArrayList();
        try{
            businessDTOList = ReflectUtil.copyProperties4List(business,BusinessDTO.class);
        }catch(Exception e){
            loggr.error("事务属性拷贝失败！{}",e.getMessage());
            throw new BusinessException("事务属性拷贝失败！");
        }
        return businessDTOList;
    }

    @Override
    public JSONObject getFixedIterms() {
        JSONObject jsonResult = new  JSONObject();
        jsonResult.put("importance",dealMap(BusinessImportance.getBusinessImportanceMap(),null));
        jsonResult.put("level",dealMap(BusinessLevel.getBusinessLevelMap(),null));
        jsonResult.put("state",dealMap(BusinessState.getBusinessStateMap(),null));
        jsonResult.put("type",dealMap(null,BusinessType.getBusinessTypeMap()));
        return jsonResult;
    }

    /**
     * @Date 2021/2/1
     * @Description 根据登入用户获取代办事项
     * @Return PageInfo
     */
    @Override
    public PageInfo getBusinessCommissionOwner(Integer pageNum, Integer pageSize) {
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        //获取登入用户id
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        String tenantCode = UserThreadLocalUtil.getUserInfo().getTenantCode();

        BusinessExample example = new BusinessExample();
        BusinessExample.Criteria criteria = example.createCriteria();
        criteria.andBusinessOwnerEqualTo(userId).andStateEqualTo(StateEnum.U.getValue());
        if(tenantCode != null && !tenantCode.isEmpty()){
            example.createCriteria().andTenantCodeEqualTo(tenantCode);
        }
        example.setOrderByClause("create_time desc");

        List<BusinessWithBLOBs> business = businessMapper.selectByExampleWithBLOBs(example);
        List<BusinessDTO> businessDTOList = Lists.newArrayList();
        try{
            if(CollectionUtils.isNotEmpty(business)){
                for (BusinessWithBLOBs businessWithBLOBs : business){
                    Map businessTypeMap = new HashMap<String, String>();
                    BusinessDTO businessDTO = ReflectUtil.copyProperties(businessWithBLOBs,BusinessDTO.class);
                    businessTypeMap.put("name",BusinessType.getNodeMsg(businessDTO.getBusinessType()));
                    businessTypeMap.put("id",businessDTO.getBusinessType());
                    businessDTO.setBusinessTypeMap(businessTypeMap);
                    businessDTOList.add(businessDTO);
                }
            }
        }catch(Exception e){
            loggr.error("事务属性拷贝失败！{}",e.getMessage());
            throw new BusinessException("事务属性拷贝失败！");
        }

        PageInfo pageInfo = new PageInfo<>(businessDTOList);
        return pageInfo;
    }

    /**
     * @Date 2021/2/1
     * @Description 获取iness枚举的名称
     * @Return IssueListDTO
     */
    private BusinessDTO getBusinessTypeEnumName(BusinessWithBLOBs businessWithBLOBs,BusinessDTO businessDTO){
        return null ;
    }


    public JSONArray dealMap (Map<Byte,String> byteMap,Map<Long,String> longMap){
        return getObjects(byteMap,longMap);
    }

    public static JSONArray getObjects(Map<Byte,String> byteMap,Map<Long,String> longMap) {
        JSONArray jsonArray = new JSONArray();
        if(null == byteMap && null == longMap){
            return jsonArray;
        }
        if(null != byteMap){
            for (Byte key:byteMap.keySet()) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("key",key);
                jsonObject1.put("value",byteMap.get(key));
                jsonArray.add(jsonObject1);
            }
        }
        if(null != longMap){
            for (Long key:longMap.keySet()) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("key",key);
                jsonObject1.put("value",longMap.get(key));
                jsonArray.add(jsonObject1);
            }
        }
        return  jsonArray;
    }


}
