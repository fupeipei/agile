package com.yusys.agile.commission.service.impl;

import com.yusys.agile.commission.dao.CommissionRecordMapper;
import com.yusys.agile.commission.domain.Commission;
import com.yusys.agile.commission.domain.CommissionRecord;
import com.yusys.agile.commission.dto.CommissionDTO;
import com.yusys.agile.commission.dto.CommissionRecordDTO;
import com.yusys.agile.commission.service.CommissionRecordService;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description 代办记录业务类
 * @date 2020/2/1
 */
public class CommissionRecordServiceImpl implements CommissionRecordService {

    @Resource
    private CommissionRecordMapper commissionRecordMapper;

    @Override
    public void saveCommissionRecord(CommissionDTO commissionDTO) {
        Commission commission = ReflectUtil.copyProperties(commissionDTO, Commission.class);
        CommissionRecord commissionRecord = dealCommissionRecordData(commission);
        int count = commissionRecordMapper.insert(commissionRecord);
        if (count != 1) {
            throw new RuntimeException("保存代办记录异常");
        }
    }

    @Override
    public void saveCommissionRecord(CommissionRecordDTO commissionRecordDTO) {
        CommissionRecord commissionRecord = ReflectUtil.copyProperties(commissionRecordDTO, CommissionRecord.class);
        int count = commissionRecordMapper.insert(commissionRecord);
        if (count != 1) {
            throw new RuntimeException("保存代办记录异常");
        }
    }

    /**
     * @description 处理代办记录数据
     * @param commission
     * @return
     */
    private CommissionRecord dealCommissionRecordData(Commission commission) {
        CommissionRecord commissionRecord = new CommissionRecord();
        commissionRecord.setCommissonId(commission.getId());
        commissionRecord.setTitle(commission.getTitle());
        commissionRecord.setType(commission.getType());
        commissionRecord.setHandler(commission.getCurrentHandler());
        commissionRecord.setIssueId(commission.getIssueId());
        commissionRecord.setProjectId(commission.getProjectId());
        commissionRecord.setStageId(commission.getStageId());
        commissionRecord.setLaneId(commission.getLaneId());
        commissionRecord.setState(StateEnum.U.getValue());
        commissionRecord.setCreateUid(commission.getCreateUid());
        commissionRecord.setCreateTime(new Date());
        commissionRecord.setTenantCode(commission.getTenantCode());
        return commissionRecord;
    }
}
