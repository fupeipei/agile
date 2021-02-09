package com.yusys.agile.businesskanban.service;

import com.yusys.agile.businesskanban.domain.BusinessWithBLOBs;
import com.yusys.agile.businesskanban.dto.BusinessDTO;
import com.yusys.agile.businesskanban.dto.BusinessHistoryRecordDTO;
import com.yusys.agile.businesskanban.dto.BusinessResultDTO;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Date: 2021/2/1
 * @Description:
 */
public interface BusinessService {

    /**
     * @return
     * @Date: 2021/2/1
     * @Description: 创建事务
     * @Param: [businessDTO]
     * @Return: int
     */
    BusinessDTO createBusiness(BusinessDTO businessDTO);

    /**
     * @Date: 2021/2/1
     * @Description: 删除事务
     * @Param: [businessId]
     * @Return: int
     */
    int deleteBusiness(Long businessId);

    /**
     * @Date: 2021/2/1
     * @Description: 修改事务
     * @Param: [businessDTO]
     * @Return: int
     */
    BusinessWithBLOBs updateBusiness(BusinessDTO businessDTO) throws Exception;

    /**
     * @Date: 2021/2/1
     * @Description: 根据事务id获取历史记录
     * @Param: [businessId]
     * @Return: java.util.List<com.yusys.agile.businesskanban.dto.BusinessHistoryRecordDTO>
     */
    List<BusinessHistoryRecordDTO> getByBusinessId(Long businessId, Integer pageNum, Integer pageSize);

    /**
     * @Date: 2021/2/1
     * @Description: 查询事务信息
     * @Param: [businessDTO]
     * @Return: com.alibaba.fastjson.JSONObject
     */
    List<BusinessResultDTO> getBusinessInfo(BusinessDTO businessDTO) throws Exception;

    /**
     * @Date: 2021/2/1
     * @Description: 列表形式展示
     * @Param: [businessDTO]
     * @Return: java.util.List<com.yusys.agile.businesskanban.dto.BusinessDTO>
     */
    List<BusinessDTO> getBusinessInfList(BusinessDTO businessDTO);


    /**
     * @Date: 2021/2/1
     * @Description: 获取事务的相关枚举
     * @Param: []
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getFixedIterms();


    /**
     * @Date 2021/2/1
     * @Description 根据登入用户获取代办事项
     * @Return PageInfo
     */
    PageInfo getBusinessCommissionOwner(Integer pageNum, Integer pageSize);

}
