package com.yusys.agile.sprint.service.impl;

import com.yusys.agile.file.domain.FileInfo;
import com.yusys.agile.file.service.FileService;
import com.yusys.agile.sprint.dao.SprintAttachmentMapper;
import com.yusys.agile.sprint.dao.SprintReviewMapper;
import com.yusys.agile.sprint.domain.SprintAttachment;
import com.yusys.agile.sprint.domain.SprintAttachmentExample;
import com.yusys.agile.sprint.domain.SprintReview;
import com.yusys.agile.sprint.domain.SprintReviewExample;
import com.yusys.agile.sprint.dto.SprintAttachmentDTO;
import com.yusys.agile.sprint.dto.SprintReviewDTO;
import com.yusys.agile.sprint.service.SprintReviewService;
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

@Service
public class SprintReviewServiceImpl implements SprintReviewService {

    private static final Logger log = LoggerFactory.getLogger(SprintReviewServiceImpl.class);

    @Resource
    private SprintReviewMapper sprintReviewMapper;
    @Resource
    private SprintAttachmentMapper sprintAttachmentMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private FileService fileService;

    @Override
    public int createSprintReview(SprintReviewDTO sprintReviewDTO) {
        SprintReview sprintReview = ReflectUtil.copyProperties(sprintReviewDTO, SprintReview.class);
        Long sprintId = sprintReviewDTO.getSprintId();
        if (null == sprintId) {
            throw new BusinessException("新建迭代回顾的迭代id为空！");
        }
        if (null == sprintReviewDTO.getProposeUid()) {
            Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
            sprintReview.setProposeUid(loginUserId);
        }
        return sprintReviewMapper.insertSelective(sprintReview);
    }

    @Override
    public List<SprintReviewDTO> getSprintReviewList(Long sprintId) {
        SprintReviewExample sprintReviewExample = new SprintReviewExample();
        SprintReviewExample.Criteria criteria = sprintReviewExample.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        List<SprintReview> sprintReviewList = sprintReviewMapper.selectByExample(sprintReviewExample);
        List<SprintReviewDTO> reviewDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sprintReviewList)) {
            for (SprintReview sprintReview : sprintReviewList) {
                SprintReviewDTO reviewDTO = ReflectUtil.copyProperties(sprintReview, SprintReviewDTO.class);
                reviewDTO.setProposeName(getUserName(reviewDTO.getProposeUid()));
                reviewDTOS.add(reviewDTO);
            }
        }
        return reviewDTOS;
    }

    @Override
    public int editSprintReview(SprintReviewDTO sprintReviewDTO) {
        SprintReview sprintReview = ReflectUtil.copyProperties(sprintReviewDTO, SprintReview.class);
        Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
        sprintReview.setReviewId(sprintReviewDTO.getReviewId());
        sprintReview.setUpdateUid(loginUserId);
        sprintReview.setUpdateTime(new Date());
        return sprintReviewMapper.updateByPrimaryKeySelective(sprintReview);
    }

    @Override
    public int deleteSprintReview(Long reviewId) {
        return sprintReviewMapper.deleteByPrimaryKey(reviewId);
    }

    @Override
    public SprintAttachment uploadAttachment(MultipartFile file, Long sprintId) {
        SprintAttachment sprintAttachment = new SprintAttachment();
        try {
            FileInfo fileInfo = fileService.upload(file);

            sprintAttachment.setSprintId(sprintId);
            sprintAttachment.setFileName(fileInfo.getFileName());
            sprintAttachment.setFileUri(fileInfo.getFileUri());
            sprintAttachment.setFileSize((int) fileInfo.getSize());
            sprintAttachment.setUploadTime(new Date());
            Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
            sprintAttachment.setUploadUid(loginUserId);
            sprintAttachmentMapper.insert(sprintAttachment);
        } catch (Exception e) {
            log.error("文件上传失败：{}", e);
            throw new BusinessException("文件上传失败：{}", e.getMessage());
        }
        return sprintAttachment;
    }

    @Override
    public void deleteAttachmentBySprintId(Long sprintId) {
        SprintAttachmentExample sprintAttachmentExample = new SprintAttachmentExample();
        SprintAttachmentExample.Criteria criteria = sprintAttachmentExample.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        sprintAttachmentMapper.deleteByExample(sprintAttachmentExample);
    }

    @Override
    public List<SprintAttachmentDTO> getSprintAttachmentList(Long sprintId) {
        SprintAttachmentExample sprintAttachmentExample = new SprintAttachmentExample();
        SprintAttachmentExample.Criteria criteria1 = sprintAttachmentExample.createCriteria();
        criteria1.andSprintIdEqualTo(sprintId);
        List<SprintAttachment> attachments = sprintAttachmentMapper.selectByExample(sprintAttachmentExample);
        List<SprintAttachmentDTO> attachmentDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(attachments)) {
            try {
                attachmentDTOS = ReflectUtil.copyProperties4List(attachments, SprintAttachmentDTO.class);
            } catch (Exception e) {
                log.error("列表转换出错{}", e.getMessage());
            }
        }
        return attachmentDTOS;
    }

    @Override
    public void deleteSprintReviewBySprintId(Long sprintId) {
        SprintReviewExample sprintReviewExample = new SprintReviewExample();
        SprintReviewExample.Criteria criteria = sprintReviewExample.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        sprintReviewMapper.deleteByExample(sprintReviewExample);
    }

    @Override
    public int deleteAttachment(Long attachmentId) {
        return sprintAttachmentMapper.deleteByPrimaryKey(attachmentId);
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
