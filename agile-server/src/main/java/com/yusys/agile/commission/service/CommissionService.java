package com.yusys.agile.commission.service;

import com.yusys.agile.commission.domain.SCommission;
import com.yusys.agile.commission.dto.SCommissionDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description 代办业务类
 */
public interface CommissionService {

    /**
     * @param sCommissionDTO
     * @return
     * @description 保存代办
     * @date 2021/2/1
     */
    void saveCommission(SCommissionDTO sCommissionDTO);

    /**
     * @param type          1:工作项编号 2:代办主键
     * @param sCommissionDTO
     * @return
     * @description 更新代办
     * @date 2021/2/1
     */
    void updateCommission(int type, SCommissionDTO sCommissionDTO);

    /**
     * @param currentHandler
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询与我相关的代办
     * @date 2021/2/1
     */
    PageInfo<List<SCommissionDTO>> getCommissionList(Long currentHandler, String title, Integer pageNum, Integer pageSize);

    /**
     * @param commissionId
     * @return
     * @description 根据代办编号查询代办记录
     * @date 2021/2/1
     */
    SCommissionDTO getCommissionById(Long commissionId);

    /**
     * @param issueId
     * @return
     * @description 根据工作项编号查询代办记录
     * @date 2021/2/1
     */
    SCommission getCommissionByIssueId(Long issueId);

    /**
     * @param sCommissionDTO
     * @description 更新代办
     * @date 2021/2/1
     */
    void updateCommission(SCommissionDTO sCommissionDTO);

    /**
     * @param exist         代办是否存在
     * @param sCommissionDTO
     * @param issueId
     * @description 保存或更新代办
     * @date 2021/2/1
     */
    void saveOrUpdateCommission(boolean exist, SCommissionDTO sCommissionDTO, Long issueId);

    /**
     * @param issueId
     * @description
     * @date 2021/2/1
     */
    void deleteCommission(Long issueId);

    /**
     * @param issueId
     * @param state
     * @description 更新代办状态
     * @date 2021/2/1
     */
    void updateCommissionState(Long issueId, String state);
}
