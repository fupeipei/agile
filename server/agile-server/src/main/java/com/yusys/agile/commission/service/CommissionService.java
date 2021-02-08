package com.yusys.agile.commission.service;

import com.yusys.agile.commission.domain.Commission;
import com.yusys.agile.commission.dto.CommissionDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description 代办业务类
 */
public interface CommissionService {

    /**
     * @description 保存代办
     * @date 2020/2/1
     * @param commissionDTO
     * @return
     */
    void saveCommission(CommissionDTO commissionDTO);

    /**
     * @description 更新代办
     * @date 2020/2/1
     * @param type 1:工作项编号 2:代办主键
     * @param commissionDTO
     * @return
     */
    void updateCommission(int type, CommissionDTO commissionDTO);

    /**
     * @description 查询与我相关的代办
     * @date 2020/2/1
     * @param currentHandler
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<List<CommissionDTO>> getCommissionList(Long currentHandler, String title, Integer pageNum, Integer pageSize);

    /**
     * @description 根据代办编号查询代办记录
     * @date 2020/2/1
     * @param commissionId
     * @return
     */
    CommissionDTO getCommissionById(Long commissionId);

    /**
     * @description 根据工作项编号查询代办记录
     * @date 2020/2/1
     * @param issueId
     * @return
     */
    Commission getCommissionByIssueId(Long issueId);

    /**
     * @description 更新代办
     * @date 2020/2/1
     * @param commissionDTO
     */
    void updateCommission(CommissionDTO commissionDTO);

    /**
     * @description 保存或更新代办
     * @date 2020/2/1
     * @param exist 代办是否存在
     * @param commissionDTO
     * @param issueId
     */
    void saveOrUpdateCommission(boolean exist, CommissionDTO commissionDTO, Long issueId);

    /**
     * @description 更新代办状态
     * @date 2020/2/1
     * @param issueId
     * @param state
     */
    void updateCommissionState(Long issueId, String state);
}
