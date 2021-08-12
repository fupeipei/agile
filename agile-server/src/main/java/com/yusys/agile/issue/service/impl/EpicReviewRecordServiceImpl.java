package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.SEpicReviewRecordMapper;
import com.yusys.agile.issue.domain.SEpicReviewRecord;
import com.yusys.agile.issue.domain.SEpicReviewRecordExample;
import com.yusys.agile.issue.dto.EpicReviewDto;
import com.yusys.agile.issue.dto.EpicReviewRecordDto;
import com.yusys.agile.issue.service.EpicReviewRecordService;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName: EpicReviewRecordServiceImpl
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/12 14:43
 */
@Service
@Slf4j
public class EpicReviewRecordServiceImpl implements EpicReviewRecordService {
    @Autowired
    private SEpicReviewRecordMapper epicReviewRecordMapper;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Override
    public void saveReview(@Valid EpicReviewDto epicReviewRDto, SecurityDTO securityDTO) {
        SEpicReviewRecord sEpicReviewRecord = new SEpicReviewRecord();
        BeanUtils.copyProperties(epicReviewRDto,sEpicReviewRecord);
        epicReviewRecordMapper.insertSelective(sEpicReviewRecord);
    }

    @Override
    public List<EpicReviewRecordDto> listEpicReview(Long issueId) {
        List<EpicReviewRecordDto> epicReviewRecordDtoList = new ArrayList<>();
        SEpicReviewRecordExample sEpicReviewRecordExample = new SEpicReviewRecordExample();
        sEpicReviewRecordExample.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andIssueIdEqualTo(issueId);
        sEpicReviewRecordExample.setOrderByClause("create_time desc");
        List<SEpicReviewRecord> sEpicReviewRecords = epicReviewRecordMapper.selectByExample(sEpicReviewRecordExample);
        List<Long> userIds = new ArrayList<>();
        sEpicReviewRecords.forEach(record->{
            EpicReviewRecordDto epicReviewRecordDto = new EpicReviewRecordDto();
            BeanUtils.copyProperties(record,epicReviewRecordDto);
            userIds.add(record.getCreateUid());
            epicReviewRecordDtoList.add(epicReviewRecordDto);
        });

        List<SsoUser> users = iFacadeUserApi.listUsersByIds(userIds);
        for (SsoUser user : users) {
            epicReviewRecordDtoList.forEach(epicReviewRecordDto->{
                if (Objects.equals(epicReviewRecordDto.getCreateUid(),user.getUserId())){
                    epicReviewRecordDto.setUserName(user.getUserName());
                }
            });
        }
        return epicReviewRecordDtoList;
    }

    @Override
    public void removeEpicReviewRecord(Long recordId, SecurityDTO securityDTO) {
        if (!Optional.ofNullable(recordId).isPresent()){
            log.info("参数错误！");
            throw new BusinessException("参数错误");
        }
        SEpicReviewRecord record = epicReviewRecordMapper.selectByPrimaryKey(recordId);
        if (!Optional.ofNullable(record).isPresent()){
            log.info("该评审记录不存在！");
            throw new BusinessException("该评审记录不存在");
        }
        if (!Objects.equals(record.getCreateUid(),securityDTO.getUserId())){
            log.info("只允许删除本人评论");
            throw new BusinessException("只允许删除本人评论");
        }
        epicReviewRecordMapper.removeEpicReviewRecord(recordId);
    }
}
