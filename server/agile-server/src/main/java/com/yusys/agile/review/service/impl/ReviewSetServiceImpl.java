package com.yusys.agile.review.service.impl;

import com.yusys.agile.review.dao.ReviewSetMapper;
import com.yusys.agile.review.domain.ReviewSet;
import com.yusys.agile.review.domain.ReviewSetExample;
import com.yusys.agile.review.dto.ReviewSetDTO;
import com.yusys.agile.review.service.ReviewSetService;
import com.alibaba.fastjson.JSONObject;
import com.yusys.portal.common.component.RedisCacheComponent;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description 评论设置业务类
 *  
 * @date 2020/09/08
 */
@Service
public class ReviewSetServiceImpl implements ReviewSetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewSetServiceImpl.class);

    private static final String REVIEW_SET_KEY_PREFIX = "agile:review:set_";

    private static final String SEPARATOR_UNDERLINE = "_";

    private static final long TTL = 24 * 60 * 60 * 1000;

    @Autowired
    private RedisCacheComponent redisCacheComponent;

    @Autowired
    private ReviewSetMapper reviewSetMapper;

    /**
     * @description 查询评审设置信息
     *  
     * @date 2020/09/09
     * @param projectId
     * @param issueType
     * @return
     */
    @Override
    public ReviewSetDTO getReviewSetInfo(Long projectId, Byte issueType) {
        ReviewSetDTO reviewSetDTO = new ReviewSetDTO();
        String key = splitReviewRedisKey(projectId, issueType);
        JSONObject jsonObject = (JSONObject) redisCacheComponent.get(key);
        if (null != jsonObject) {
            reviewSetDTO = jsonObject.toJavaObject(ReviewSetDTO.class);
        } else {
            ReviewSetExample reviewSetExample = splitReviewSetExample(projectId, issueType);
            List<ReviewSet> reviewSets = reviewSetMapper.selectByExample(reviewSetExample);
            if (CollectionUtils.isNotEmpty(reviewSets)) {
                ReviewSet reviewSet = reviewSets.get(0);
                reviewSetDTO = ReflectUtil.copyProperties(reviewSet, ReviewSetDTO.class);
                redisCacheComponent.set(key, reviewSet, TTL);
            }
        }
        return reviewSetDTO;
    }

    /**
     * @description 拼接评审设置信息Key
     *  
     * @date 2020/09/09
     * @param projectId
     * @param issueType
     * @return
     */
    private String splitReviewRedisKey(Long projectId, Byte issueType) {
        StringBuilder key = new StringBuilder();
        key.append(REVIEW_SET_KEY_PREFIX).append(projectId).append(SEPARATOR_UNDERLINE).append(issueType);
        return key.toString();
    }

    /**
     * @description 拼接评审设置信息Example
     *  
     * @date 2020/09/09
     * @param projectId
     * @param issueType
     * @return
     */
    private ReviewSetExample splitReviewSetExample(Long projectId, Byte issueType) {
        ReviewSetExample reviewSetExample = new ReviewSetExample();
        reviewSetExample.setOrderByClause("id asc");
        reviewSetExample.createCriteria()
            .andProjectIdEqualTo(projectId)
                .andIssueTypeEqualTo(issueType)
                    .andStateEqualTo(StateEnum.U.getValue());
        return reviewSetExample;
    }

    /**
     * @description 编辑评审设置信息
     *  
     * @date 2020/09/09
     * @param reviewSetDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int editReviewSetInfo(ReviewSetDTO reviewSetDTO) {
        LOGGER.info("editReviewSetInfo param editReviewSetInfo:{}", reviewSetDTO);
        int count = 0;
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        ReviewSet reviewSet = ReflectUtil.copyProperties(reviewSetDTO, ReviewSet.class);
        Long id = reviewSetDTO.getId();
        if (null != id) {
            reviewSet.setUpdateUid(userId);
            reviewSet.setUpdateTime(new Date());
            count = reviewSetMapper.updateByPrimaryKeySelective(reviewSet);
            if (count != 1) {
                throw new RuntimeException("编辑评审设置信息失败");
            }
        } else {
            reviewSet.setCreateUid(userId);
            reviewSet.setCreateTime(new Date());
            reviewSet.setState(StateEnum.U.getValue());
            reviewSet.setTenantCode(UserThreadLocalUtil.getTenantCode());
            count = reviewSetMapper.insert(reviewSet);
            if (count != 1) {
                throw new RuntimeException("新增评审设置信息失败");
            }
        }
        String key = splitReviewRedisKey(reviewSetDTO.getProjectId(), reviewSetDTO.getIssueType());
        redisCacheComponent.set(key, reviewSet, TTL);
        return count;
    }
}
