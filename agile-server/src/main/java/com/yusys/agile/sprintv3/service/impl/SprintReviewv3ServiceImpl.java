package com.yusys.agile.sprintv3.service.impl;

import com.yusys.agile.file.domain.FileInfo;
import com.yusys.agile.file.service.FileService;
import com.yusys.agile.sprintV3.dto.SSprintAttachmentDTO;
import com.yusys.agile.sprintV3.dto.SprintReviewDTO;
import com.yusys.agile.sprintv3.dao.SSprintAttachmentMapper;
import com.yusys.agile.sprintv3.dao.SSprintReviewMapper;
import com.yusys.agile.sprintv3.domain.SSprintAttachment;
import com.yusys.agile.sprintv3.domain.SSprintAttachmentExample;
import com.yusys.agile.sprintv3.domain.SSprintReview;
import com.yusys.agile.sprintv3.domain.SSprintReviewExample;
import com.yusys.agile.sprintv3.service.SprintReviewv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author maxp2
 * @Date: 2021/6/8
 */
@Service
public class SprintReviewv3ServiceImpl implements SprintReviewv3Service {

    private static final Logger log = LoggerFactory.getLogger(SprintReviewv3ServiceImpl.class);

    @Resource
    private SSprintReviewMapper sSprintReviewMapper;
    @Resource
    private SSprintAttachmentMapper sSprintAttachmentMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private FileService fileService;

    @Override
    public int createSprintReview(SprintReviewDTO sprintReviewDTO) {
        SSprintReview sprintReview = ReflectUtil.copyProperties(sprintReviewDTO, SSprintReview.class);
        Long sprintId = sprintReviewDTO.getSprintId();
        if (null == sprintId) {
            throw new BusinessException("新建迭代回顾的迭代id为空！");
        }
        if (null == sprintReviewDTO.getProposeUid()) {
            Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
            sprintReview.setProposeUid(loginUserId);
        }
        return sSprintReviewMapper.insertSelective(sprintReview);
    }

    @Override
    public List<SprintReviewDTO> getSprintReviewList(Long sprintId) {
        SSprintReviewExample sprintReviewExample = new SSprintReviewExample();
        SSprintReviewExample.Criteria criteria = sprintReviewExample.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        List<SSprintReview> sprintReviewList = sSprintReviewMapper.selectByExample(sprintReviewExample);
        List<SprintReviewDTO> reviewDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sprintReviewList)) {
            for (SSprintReview sprintReview : sprintReviewList) {
                SprintReviewDTO reviewDTO = ReflectUtil.copyProperties(sprintReview, SprintReviewDTO.class);
                reviewDTO.setProposeName(getUserName(reviewDTO.getProposeUid()));
                reviewDTOS.add(reviewDTO);
            }
        }
        return reviewDTOS;
    }

    @Override
    public int editSprintReview(SprintReviewDTO sprintReviewDTO) {
        SSprintReview sprintReview = ReflectUtil.copyProperties(sprintReviewDTO, SSprintReview.class);
        Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
        sprintReview.setReviewId(sprintReviewDTO.getReviewId());
        sprintReview.setUpdateUid(loginUserId);
        sprintReview.setUpdateTime(new Date());
        return sSprintReviewMapper.updateByPrimaryKeySelective(sprintReview);
    }

    @Override
    public int deleteSprintReview(Long reviewId) {
        return sSprintReviewMapper.deleteByPrimaryKey(reviewId);
    }

    @Override
    public SSprintAttachment uploadAttachment(MultipartFile file, Long sprintId) {
        SSprintAttachment sprintAttachment = new SSprintAttachment();
        try {
            FileInfo fileInfo = fileService.upload(file);

            sprintAttachment.setSprintId(sprintId);
            sprintAttachment.setFileName(fileInfo.getFileName());
            sprintAttachment.setFileUri(fileInfo.getFileUri());
            sprintAttachment.setFileSize((int) fileInfo.getSize());
            sprintAttachment.setUploadTime(new Date());
            Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
            sprintAttachment.setUploadUid(loginUserId);
            sSprintAttachmentMapper.insert(sprintAttachment);
        } catch (Exception e) {
            log.error("文件上传失败：{}", e);
            throw new BusinessException("文件上传失败：{}", e.getMessage());
        }
        return sprintAttachment;
    }

    @Override
    public void deleteAttachmentBySprintId(Long sprintId) {
        SSprintAttachmentExample sprintAttachmentExample = new SSprintAttachmentExample();
        SSprintAttachmentExample.Criteria criteria = sprintAttachmentExample.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        sSprintAttachmentMapper.deleteByExample(sprintAttachmentExample);
    }

    @Override
    public List<SSprintAttachmentDTO> getSprintAttachmentList(Long sprintId) {
        SSprintAttachmentExample sprintAttachmentExample = new SSprintAttachmentExample();
        SSprintAttachmentExample.Criteria criteria1 = sprintAttachmentExample.createCriteria();
        criteria1.andSprintIdEqualTo(sprintId);
        List<SSprintAttachment> attachments = sSprintAttachmentMapper.selectByExample(sprintAttachmentExample);
        List<SSprintAttachmentDTO> dtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(attachments)) {
            try {
                dtos = ReflectUtil.copyProperties4List(attachments, SSprintAttachmentDTO.class);
            } catch (Exception e) {
                log.error("列表转换出错{}", e.getMessage());
            }
        }
        return dtos;
    }

    @Override
    public void deleteSprintReviewBySprintId(Long sprintId) {
        SSprintReviewExample sprintReviewExample = new SSprintReviewExample();
        SSprintReviewExample.Criteria criteria = sprintReviewExample.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        sSprintReviewMapper.deleteByExample(sprintReviewExample);
    }

    @Override
    public int deleteAttachment(Long attachmentId) {
        return sSprintAttachmentMapper.deleteByPrimaryKey(attachmentId);
    }

    /**
     * @param userId
     * @Date 2021/2/25
     * @Description 根据用户id获取用户名
     * @Return java.lang.String
     */
    private String getUserName(Long userId) {
        if (null == userId) {
            return null;
        }

        SsoUser user = iFacadeUserApi.queryUserById(userId);
        if (null != user) {
            return user.getUserName();
        }

        return null;
    }
}
