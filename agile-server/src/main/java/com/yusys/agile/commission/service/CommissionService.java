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
     * @param commissionDTO
     * @return
     * @description 保存代办
     * @date 2021/2/1
     */
    void saveCommission(CommissionDTO commissionDTO);

    /**
     * @param type          1:工作项编号 2:代办主键
     * @param commissionDTO
     * @return
     * @description 更新代办
     * @date 2021/2/1
     */
    void updateCommission(int type, CommissionDTO commissionDTO);

    /**
     * @param currentHandler
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询与我相关的代办
     * @date 2021/2/1
     */
    PageInfo<List<CommissionDTO>> getCommissionList(Long currentHandler, String title, Integer pageNum, Integer pageSize);

    /**
     * @param commissionId
     * @return
     * @description 根据代办编号查询代办记录
     * @date 2021/2/1
     */
    CommissionDTO getCommissionById(Long commissionId);

    /**
     * @param issueId
     * @return
     * @description 根据工作项编号查询代办记录
     * @date 2021/2/1
     */
    Commission getCommissionByIssueId(Long issueId);

    /**
     * @param commissionDTO
     * @description 更新代办
     * @date 2021/2/1
     */
    void updateCommission(CommissionDTO commissionDTO);

    /**
     * @param exist         代办是否存在
     * @param commissionDTO
     * @param issueId
     * @description 保存或更新代办
     * @date 2021/2/1
     */
    void saveOrUpdateCommission(boolean exist, CommissionDTO commissionDTO, Long issueId);

    /**
     * @param issueId
     * @param state
     * @description 更新代办状态
     * @date 2021/2/1
     */
    void updateCommissionState(Long issueId, String state);
}
