package com.yusys.agile.commission.service;

import com.yusys.agile.commission.dto.CommissionDTO;
import com.yusys.agile.commission.dto.CommissionRecordDTO;

public interface CommissionRecordService {

    /**
     * @description 保存代办记录
     * @date 2020/2/1
     * @param commissionDTO
     */
    public void saveCommissionRecord(CommissionDTO commissionDTO);

    /**
     * @description 保存代办记录
     * @date 2020/2/1
     * @param commissionRecordDTO
     */
    public void saveCommissionRecord(CommissionRecordDTO commissionRecordDTO);
}
