package com.yusys.agile.commission.service;

import com.yusys.agile.commission.dto.CommissionDTO;
import com.yusys.agile.commission.dto.CommissionRecordDTO;

public interface CommissionRecordService {

    /**
     * @param commissionDTO
     * @description 保存代办记录
     * @date 2021/2/1
     */
    public void saveCommissionRecord(CommissionDTO commissionDTO);

    /**
     * @param commissionRecordDTO
     * @description 保存代办记录
     * @date 2021/2/1
     */
    public void saveCommissionRecord(CommissionRecordDTO commissionRecordDTO);
}
