package com.yusys.agile.commission.service.impl;

import com.yusys.agile.commission.dao.CommissionRecordMapper;
import com.yusys.agile.commission.domain.SCommission;
import com.yusys.agile.commission.domain.SCommissionRecord;
import com.yusys.agile.commission.dto.SCommissionDTO;
import com.yusys.agile.commission.dto.SCommissionRecordDTO;
import com.yusys.agile.commission.service.CommissionRecordService;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description 代办记录业务类
 * @date 2021/2/1
 */
public class CommissionRecordServiceImpl implements CommissionRecordService {

    @Resource
    private CommissionRecordMapper commissionRecordMapper;

    @Override
    public void saveCommissionRecord(SCommissionDTO sCommissionDTO) {
        SCommission sCommission = ReflectUtil.copyProperties(sCommissionDTO, SCommission.class);
        SCommissionRecord sCommissionRecord = dealCommissionRecordData(sCommission);
        int count = commissionRecordMapper.insert(sCommissionRecord);
        if (count != 1) {
            throw new RuntimeException("保存代办记录异常");
        }
    }

    @Override
    public void saveCommissionRecord(SCommissionRecordDTO sCommissionRecordDTO) {
        SCommissionRecord sCommissionRecord = ReflectUtil.copyProperties(sCommissionRecordDTO, SCommissionRecord.class);
        int count = commissionRecordMapper.insert(sCommissionRecord);
        if (count != 1) {
            throw new RuntimeException("保存代办记录异常");
        }
    }

    /**
     * @param sCommission
     * @return
     * @description 处理代办记录数据
     */
    private SCommissionRecord dealCommissionRecordData(SCommission sCommission) {
        SCommissionRecord sCommissionRecord = new SCommissionRecord();
        sCommissionRecord.setCommissonId(sCommission.getId());
        sCommissionRecord.setTitle(sCommission.getTitle());
        sCommissionRecord.setType(sCommission.getType());
        sCommissionRecord.setHandler(sCommission.getCurrentHandler());
        sCommissionRecord.setIssueId(sCommission.getIssueId());
        sCommissionRecord.setProjectId(sCommission.getProjectId());
        sCommissionRecord.setStageId(sCommission.getStageId());
        sCommissionRecord.setLaneId(sCommission.getLaneId());
        sCommissionRecord.setState(StateEnum.U.getValue());
        sCommissionRecord.setCreateUid(sCommission.getCreateUid());
        sCommissionRecord.setCreateTime(new Date());
        sCommissionRecord.setTenantCode(sCommission.getTenantCode());
        return sCommissionRecord;
    }
}
