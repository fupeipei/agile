package com.yusys.agile.commission.service;

import com.yusys.agile.commission.dto.SCommissionDTO;
import com.yusys.agile.commission.dto.SCommissionRecordDTO;

public interface CommissionRecordService {

    /**
     * @param sCommissionDTO
     * @description 保存代办记录
     * @date 2021/2/1
     */
    public void saveCommissionRecord(SCommissionDTO sCommissionDTO);

    /**
     * @param sCommissionRecordDTO
     * @description 保存代办记录
     * @date 2021/2/1
     */
    public void saveCommissionRecord(SCommissionRecordDTO sCommissionRecordDTO);
}
