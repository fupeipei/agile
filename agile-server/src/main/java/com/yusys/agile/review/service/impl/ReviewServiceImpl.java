package com.yusys.agile.review.service.impl;

import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.FeatureService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueRichTextFactory;
import com.yusys.agile.review.dao.ReviewMapper;
import com.yusys.agile.review.dao.ReviewRecordMapper;
import com.yusys.agile.review.domain.Review;
import com.yusys.agile.review.domain.ReviewExample;
import com.yusys.agile.review.domain.ReviewRecord;
import com.yusys.agile.review.domain.ReviewRecordExample;
import com.yusys.agile.review.dto.ReviewDTO;
import com.yusys.agile.review.dto.ReviewRecordDTO;
import com.yusys.agile.review.dto.ReviewSetDTO;
import com.yusys.agile.review.dto.StoryCheckResultDTO;
import com.yusys.agile.review.enums.ReviewResultEnum;
import com.yusys.agile.review.enums.ReviewStatusEnum;
import com.yusys.agile.review.service.ReviewService;
import com.yusys.agile.review.service.ReviewSetService;
import com.yusys.agile.user.domain.ReqUserRlat;
import com.yusys.agile.user.enums.AgileUserRlatEnum;
import com.yusys.agile.user.service.ReqUserRlatService;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评审实现类
 *
 * @create 2020-09-08 09:55
 */
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private static final String CREATE_TIME_DESC = "create_time desc";

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ReviewRecordMapper reviewRecordMapper;

    @Autowired
    private ReqUserRlatService reqUserRlatService;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    @Autowired
    private ReviewSetService reviewSetService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private IssueRichTextFactory issueRichTextFactory;

    @Resource
    private IssueMapper issueMapper;

    /**
     * 功能描述:创建评审
     *
     * @param reviewDTO
     * @return void
     * @date 2021/3/8
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addReview(ReviewDTO reviewDTO) {
        if (null == reviewDTO || StringUtils.isBlank(reviewDTO.getReviewTheme())
                || CollectionUtils.isEmpty(reviewDTO.getUserIds())
                || null == reviewDTO.getIssueId() || null == reviewDTO.getIssueType()) {
            LOGGER.info("创建评审入参错误！");
            throw new BusinessException("创建评审入参错误！");
        }

        // 评审内容为空
        String desc = reviewDTO.getReviewDesc();
        if (StringUtils.isBlank(desc)) {
            LOGGER.info("评审内容为空！");
            throw new BusinessException("评审工作项的描述不能为空！");
        }

        // 要评审内容每次都插到库里
        Long issueId = reviewDTO.getIssueId();
        issueRichTextFactory.dealIssueRichText(issueId, desc, null,null);

        // 判断是否有未完成的评审，如果有未完成的评审，不能新增
        List<Review> reviewList = getReviewList(reviewDTO.getIssueId());
        Boolean hasNotFinishedReview = hasNotFinishedReview(reviewDTO.getProjectId(), reviewDTO.getIssueId(), reviewDTO.getIssueType(), reviewList);
        if (hasNotFinishedReview) {
            LOGGER.info("工作项{}有未完成的评审，不能创建评审！", reviewDTO.getIssueId());
            throw new BusinessException("有未完成的评审，不能创建评审！");
        }

        // 创建评审
        Review review = ReflectUtil.copyProperties(reviewDTO, Review.class);
        // 版本信息
        String version = getReviewVersion(reviewList);
        review.setVersion(version);
        review.setState(StateEnum.U.getValue());
        // 新建就是评审中
        review.setReviewStatus(ReviewStatusEnum.REVIEWING.CODE);
        reviewMapper.insert(review);
        // 关联相关人员 先不算发起人，除非发起人自己加进去
        List<Long> userIds = reviewDTO.getUserIds();
        List<ReqUserRlat> reqUserRlats = assembleReqUserRlats(userIds, reviewDTO.getProjectId(), review.getReviewId(), AgileUserRlatEnum.REVIEW.CODE, 2);
        reqUserRlatService.insertBatch(reqUserRlats);

    }

    /**
     * 功能描述: 取消评审
     *
     * @param reviewId
     * @return void
     * @date 2021/3/8
     */
    @Override
    public void cancelReview(Long reviewId, Long operatorId) {
        // 只有评审创建人才能取消评审
        if (null != operatorId) {
            Review review = reviewMapper.selectByPrimaryKey(reviewId);
            if (null != review && !operatorId.equals(review.getCreateUid())) {
                LOGGER.info("取消评审必须由评审创建人操作！operatorId={},createUid={}", operatorId, review.getCreateUid());
                throw new BusinessException("取消评审必须由评审创建人操作！");
            }
        }
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setReviewStatus(ReviewStatusEnum.CANCEL.CODE);
        reviewMapper.updateByPrimaryKeySelective(review);
    }


    /**
     * 功能描述: 进行评审
     *
     * @param reviewRecordDTO
     * @return void
     * @date 2021/3/8
     */
    @Override
    public void executeReview(ReviewRecordDTO reviewRecordDTO, Long operatorId) {
        if (null == reviewRecordDTO || null == reviewRecordDTO.getReviewId()
                || StringUtils.isBlank(reviewRecordDTO.getReviewResult())
                || null == reviewRecordDTO.getIssueId()) {
            LOGGER.info("进行评审入参错误！");
            throw new BusinessException("进行评审入参错误！");
        }
        LOGGER.info("进行评审的操作人operatorId={}", operatorId);
        Long reviewId = reviewRecordDTO.getReviewId();
        // 只有邀请的人才能进行评审
        if (null != operatorId) {
            Map<Long, String> reviewerMap = getReviewerMap(reviewId);
            if (!reviewerMap.containsKey(operatorId)) {
                LOGGER.info("进行评审操作人不是邀请参加评审用户！operatorId={}", operatorId);
                throw new BusinessException("不是邀请用户不能进行评审！");
            }

            // 已经评审过的不能评审
            if (hasReviewed(reviewId, operatorId)) {
                LOGGER.info("重复评审！");
                throw new BusinessException("不能重复评审！");
            }
        }

        // 已经有结果的就不用评审
        ReviewDTO reviewDTO = getReview(reviewId);
        if (null != reviewDTO && !StringUtils.equals(reviewDTO.getReviewStatus(), ReviewStatusEnum.REVIEWING.CODE)) {
            LOGGER.info("评审结果已出，不需要评审！");
            throw new BusinessException("评审结果已出，不需要评审！");
        }


        ReviewRecord reviewRecord = ReflectUtil.copyProperties(reviewRecordDTO, ReviewRecord.class);
        reviewRecord.setState(StateEnum.U.getValue());
        reviewRecordMapper.insert(reviewRecord);
    }

    /**
     * 功能描述:判断是否已经参与review
     *
     * @param reviewId
     * @param operatorId
     * @return boolean
     * @date 2021/3/9
     */
    private boolean hasReviewed(Long reviewId, Long operatorId) {

        ReviewRecordExample example = new ReviewRecordExample();
        ReviewRecordExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue()).andReviewIdEqualTo(reviewId).andCreateUidEqualTo(operatorId);

        List<ReviewRecord> reviewedRecords = reviewRecordMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(reviewedRecords)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能描述: 获取邀请参加评审的用户map，用于快速判断某个用户是否在邀请人中
     *
     * @param reviewId
     * @return java.util.Map<java.lang.Long, java.lang.String>
     * @date 2021/3/9
     */
    private Map<Long, String> getReviewerMap(Long reviewId) {
        Map<Long, String> map = new HashMap<>();
        List<UserDTO> userDTOList = listUserDtosByReviewId(reviewId);
        for (UserDTO tempUser : userDTOList) {
            map.put(tempUser.getUserId(), tempUser.getUserName());
        }
        return map;
    }

    /**
     * 功能描述: 获取评审下的邀请用户
     *
     * @param reviewId
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2021/3/9
     */
    private List<UserDTO> listUserDtosByReviewId(Long reviewId) {
        List<ReqUserRlat> rlats = reqUserRlatService.listRlatsBySubjectId(reviewId, AgileUserRlatEnum.REVIEW.CODE, null);
        List<UserDTO> userDTOS = reqUserRlatService.assembleUserDTOs(rlats);
        return userDTOS;

    }


    /**
     * 功能描述: 列表查询评审
     *
     * @param issueId
     * @return java.util.List<com.yusys.agile.review.dto.ReviewDTO>
     * @date 2021/3/8
     */
    @Override
    public List<ReviewDTO> listReview(Long issueId) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        Long projectId = issue.getProjectId();
        List<Review> reviews = getReviewList(issueId);
        List<ReviewDTO> reviewDTOList = Lists.newArrayList();
        for (int i = 0; i < reviews.size(); i++) {
            // 组装评审记录，只有最近的一个评审需要计算评审的状态，其他的评审直接取数据
            ReviewDTO reviewDTO = assembleReviewDTO(projectId, issueId, reviews.get(i), i);
            reviewDTOList.add(reviewDTO);
        }

        return reviewDTOList;
    }

    /**
     * 功能描述: 查询评审信息
     *
     * @param reviewId
     * @return com.yusys.agile.review.dto.ReviewDTO
     * @date 2021/3/9
     */
    @Override
    public ReviewDTO getReview(Long reviewId) {
        if (null == reviewId) {
            throw new BusinessException("查询评审信息参错误！");
        }

        Review review = reviewMapper.selectByPrimaryKey(reviewId);
        ReviewDTO reviewDTO = ReflectUtil.copyProperties(review, ReviewDTO.class);
        SsoUser ssoUserDTO = iFacadeUserApi.queryUserById(reviewDTO.getCreateUid());
        if (null != ssoUserDTO) {
            reviewDTO.setCreateUserName(ssoUserDTO.getUserName());
        }

        // 邀请人员信息
        List<UserDTO> userDTOList = listUserDtosByReviewId(reviewId);
        reviewDTO.setUsers(userDTOList);
        return reviewDTO;
    }


    /**
     * 功能描述: 组装reviewDTO对象
     *
     * @param issueId
     * @param tempReview
     * @return com.yusys.agile.review.dto.ReviewDTO
     * @date 2021/3/9
     */
    private ReviewDTO assembleReviewDTO(Long projectId, Long issueId, Review tempReview, Integer i) {
        ReviewDTO reviewDTO = ReflectUtil.copyProperties(tempReview, ReviewDTO.class);
        // 创建人
        Long createUid = tempReview.getCreateUid();
        SsoUser user = iFacadeUserApi.queryUserById(createUid);
        if (null != user) {
            reviewDTO.setCreateUserName(user.getUserName());
        }
        // 评审状态 评审状态是评审中的，就要调接口查下是否已经完成评审
        // 注意，理论上只有最近的一条才需要计算评审状态
        if (StringUtils.equals(reviewDTO.getReviewStatus(), ReviewStatusEnum.REVIEWING.CODE)) {
            LOGGER.info("需要计算评审状态的i={}", i);
            String reviewStatus = calculateReviewStatus(projectId, issueId, tempReview.getIssueType(), tempReview.getReviewId());
            reviewDTO.setReviewStatus(reviewStatus);
        }

        // 评审人
        List<UserDTO> userDTOList = listUserDtosByReviewId(tempReview.getReviewId());
        reviewDTO.setUsers(userDTOList);

        // 评审记录DTO
        List<ReviewRecordDTO> reviewRecordDTOList = listReviewRecordDTOs(tempReview.getReviewId());
        reviewDTO.setReviewRecordDTOList(reviewRecordDTOList);
        return reviewDTO;

    }

    /**
     * 功能描述: 根据评审id查询下面的评审记录
     *
     * @param reviewId
     * @return java.util.List<com.yusys.agile.review.dto.ReviewRecordDTO>
     * @date 2021/3/9
     */
    private List<ReviewRecordDTO> listReviewRecordDTOs(Long reviewId) {
        ReviewRecordExample example = new ReviewRecordExample();
        example.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andReviewIdEqualTo(reviewId);
        example.setOrderByClause(CREATE_TIME_DESC);
        List<ReviewRecordDTO> reviewRecordDTOList = reviewRecordMapper.selectDTOByExample(example);
        Map<Long, String> reviewerMap = getReviewerMap(reviewId);
        // 拼装name
        for (ReviewRecordDTO temp : reviewRecordDTOList) {
            temp.setCreatorName(reviewerMap.get(temp.getCreateUid()));
        }

        return reviewRecordDTOList;
    }

    /**
     * 功能描述: 组装关系
     *
     * @param userIds        用户集合
     * @param projectId
     * @param subjectId
     * @param userRelateType 关系类型
     * @param isConcurrent   是否负责人1是 2成员
     * @return java.util.List<com.yusys.agile.user.domain.ReqUserRlat>
     * @date 2021/3/8
     */
    private List<ReqUserRlat> assembleReqUserRlats(List<Long> userIds, Long projectId, Long subjectId, Integer userRelateType, Integer isConcurrent) {
        List<ReqUserRlat> reqUserRlats = Lists.newArrayList();
        for (Long userId : userIds) {
            ReqUserRlat reqUserRlat = new ReqUserRlat();
            reqUserRlat.setUserRelateType(userRelateType);
            reqUserRlat.setSubjectId(subjectId);
            reqUserRlat.setIsConcurrent(isConcurrent);
            reqUserRlat.setUserId(userId);
            reqUserRlat.setProjectId(projectId);
            reqUserRlat.setState(StateEnum.U.getValue());
            reqUserRlats.add(reqUserRlat);
        }

        return reqUserRlats;
    }

    /**
     * 功能描述: 获取版本
     *
     * @param reviewList
     * @return java.lang.String
     * @date 2021/3/8
     */
    private String getReviewVersion(List<Review> reviewList) {
        if (CollectionUtils.isEmpty(reviewList)) {
            return "v1";
        } else {
            String latestVersion = reviewList.get(0).getVersion();
            Integer num = Integer.parseInt(StringUtils.substringAfter(latestVersion, "v"));
            return "v" + (num + 1);
        }
    }

    /**
     * 功能描述: 查询所有的评审
     *
     * @param issueId
     * @return java.util.List<com.yusys.agile.review.domain.Review>
     * @date 2021/3/8
     */
    private List<Review> getReviewList(Long issueId) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andIssueIdEqualTo(issueId).andStateEqualTo(StateEnum.U.getValue());
        example.setOrderByClause(CREATE_TIME_DESC);
        List<Review> reviewList = reviewMapper.selectByExampleWithBLOBs(example);
        return reviewList;
    }


    /**
     * 功能描述: 判断是否有未完成的评审，如果有，不允许新增新的评审
     *
     * @param issueId
     * @param reviewList
     * @return java.lang.Boolean
     * @date 2021/3/8
     */
    private Boolean hasNotFinishedReview(Long projectId, Long issueId, Byte issueType, List<Review> reviewList) {
        // 没有评审信息
        if (CollectionUtils.isEmpty(reviewList)) {
            return false;
        }
        // 查看最近的review
        Review latestReview = reviewList.get(0);
        String reviewStatus = latestReview.getReviewStatus();
        // 计算完通过不通过或者取消都视为评审完成了
        if (!StringUtils.equals(reviewStatus, ReviewStatusEnum.REVIEWING.CODE)) {
            return false;
        }

        // 调用接口计算当前投票属于什么状态
        String reviewPassCode = calculateReviewStatus(projectId, issueId, issueType, latestReview.getReviewId());
        if (StringUtils.equals(reviewPassCode, ReviewStatusEnum.PASS.CODE) || StringUtils.equals(reviewPassCode, ReviewStatusEnum.FAIL.CODE)) {
            return false;
        }

        return true;
    }

    /**
     * 功能描述: 计算最近一次评审的结果
     * 通过的条件
     * 同意人数占比大于等于通过率
     * <p>
     * 不通过条件
     * 反对人数占比大于（1-通过率）
     * <p>
     * 待定状态条件
     * 1 同意人数占比小于通过率
     * 2 反对人数占比小于等于（1-通过率）
     *
     * @param issueId        工作项id
     * @param issueType      工作项类型
     * @param latestReviewId 最近一次评审id
     * @return java.lang.String
     * @date 2021/3/8
     */
    public String calculateReviewStatus(Long projectId, Long issueId, Byte issueType, Long latestReviewId) {


        // 总人数
        List<ReqUserRlat> ReqUserRlatList = reqUserRlatService.listRlatsBySubjectId(latestReviewId, AgileUserRlatEnum.REVIEW.CODE, null);
        Integer allNum = ReqUserRlatList.size();

        // 通过率
        ReviewSetDTO storyReviewSetDTO = getReviewSetDTO(projectId, issueType);
        Integer pass = storyReviewSetDTO.getReviewPassRate().intValue();
        double setUpPassRate = BigDecimal.valueOf((float) pass / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        // 同意的人数
        Integer passNum = getNum(latestReviewId, ReviewResultEnum.PASS.CODE);
        double passRate = BigDecimal.valueOf((float) passNum / allNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (passRate >= setUpPassRate) {
            // 通过了，改数据库状态为通过
            updateReviewByCalculate(latestReviewId, ReviewStatusEnum.PASS.CODE);
            return ReviewStatusEnum.PASS.CODE;
        } else {
            // 反对的人数
            Integer failNum = getNum(latestReviewId, ReviewResultEnum.FAIL.CODE);
            double failRate = BigDecimal.valueOf((float) failNum / allNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (failRate > (1 - setUpPassRate)) {
                // 不通过，改数据库状态为不通过
                updateReviewByCalculate(latestReviewId, ReviewStatusEnum.FAIL.CODE);
                return ReviewStatusEnum.FAIL.CODE;
            } else {
                return ReviewStatusEnum.REVIEWING.CODE;
            }
        }

    }

    /**
     * 功能描述: 根据计算的结果将评审通过还是不通过修改数据库
     *
     * @param latestReviewId
     * @param code
     * @return void
     * @date 2021/3/9
     */
    private void updateReviewByCalculate(Long latestReviewId, String code) {
        Review review = new Review();
        review.setReviewId(latestReviewId);
        review.setReviewStatus(code);
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    /**
     * 功能描述: 根据评审结果查询对应的人数
     *
     * @param latestReviewId
     * @return java.lang.Integer
     * @date 2021/3/8
     */
    private Integer getNum(Long latestReviewId, String reviewResult) {
        ReviewRecordExample example = new ReviewRecordExample();
        example.createCriteria().andReviewIdEqualTo(latestReviewId).andStateEqualTo(StateEnum.U.getValue())
                .andReviewResultEqualTo(reviewResult);
        example.setOrderByClause(CREATE_TIME_DESC);
        Long num = reviewRecordMapper.countByExample(example);
        if (null == num) {
            return 0;
        } else {
            return num.intValue();
        }
    }


    /**
     * 功能描述: 判断故事是否允许加入到迭代中
     * epic、feature、story三层
     * 开关关闭，直接过，开关打开，根据通过率判断
     *
     * @param storyId
     * @return java.lang.Boolean
     * @date 2021/3/9
     */
    @Override
    public StoryCheckResultDTO allowStoryInSprint(Long storyId, Long projectId) {

        StoryCheckResultDTO storyCheckResultDTO = new StoryCheckResultDTO();
        storyCheckResultDTO.setHasPassed(true);

        Byte switchOpen = new Byte("1");

        // story本身
        ReviewSetDTO storyReviewSetDTO = getReviewSetDTO(projectId, IssueTypeEnum.TYPE_STORY.CODE);
        // 开关打开,进行校验
        if (storyReviewSetDTO.getReviewSwitch().equals(switchOpen)) {
            Boolean storyHasPass = hasPassReview(projectId, storyId, IssueTypeEnum.TYPE_STORY.CODE);
            if (!storyHasPass) {
                LOGGER.info("故事id={},故事本身评审未通过！", storyId);
                storyCheckResultDTO.setHasPassed(false);
                storyCheckResultDTO.setMsg("故事评审未通过!工作项ID为:" + storyId);
                return storyCheckResultDTO;
            }

        }

        // feature层
        ReviewSetDTO featureReviewSetDTO = getReviewSetDTO(projectId, IssueTypeEnum.TYPE_FEATURE.CODE);
        //IssueDTO storyDTO = storyService.queryStory(storyId, projectId);
        IssueDTO storyDTO = storyService.queryStory(storyId);
        if (null == storyDTO) {
            throw new BusinessException("该故事不存在！");
        }
        // 先查询故事的上层feature的id
        Long featureId = storyDTO.getParentId();
        if (featureReviewSetDTO.getReviewSwitch().equals(switchOpen)) {
            // 上层feature必须存在才进行校验
            if (null != featureId) {
                Boolean featureHasPass = hasPassReview(projectId, featureId, IssueTypeEnum.TYPE_FEATURE.CODE);
                if (!featureHasPass) {
                    LOGGER.info("featureId={}评审未通过！", featureId);
                    storyCheckResultDTO.setHasPassed(false);
                    storyCheckResultDTO.setMsg("故事所在的研发需求的评审未通过!工作项ID为:" + featureId);
                    return storyCheckResultDTO;
                }
            }
        }

        // epic层
        ReviewSetDTO epicReviewSetDTO = getReviewSetDTO(projectId, IssueTypeEnum.TYPE_EPIC.CODE);
        // epic开关打开时才校验
        if (epicReviewSetDTO.getReviewSwitch().equals(switchOpen)) {
            // 故事的上层存在feature才会再往上找epic
            if (null != featureId) {
                //IssueDTO featureDTO = featureService.queryFeature(featureId, projectId);
                IssueDTO featureDTO = featureService.queryFeature(featureId);
                Long epicId = featureDTO.getParentId();
                if (null != epicId) {
                    Boolean epicHasPass = hasPassReview(projectId, epicId, IssueTypeEnum.TYPE_FEATURE.CODE);
                    if (!epicHasPass) {
                        LOGGER.info("epicId={}评审未通过！", epicId);
                        storyCheckResultDTO.setHasPassed(false);
                        storyCheckResultDTO.setMsg("故事所在的业务需求的评审未通过!工作项ID为:" + epicId);
                        return storyCheckResultDTO;
                    }
                }

            }

        }


        return storyCheckResultDTO;
    }

    /**
     * 功能描述: 查询评审设置结果
     *
     * @param projectId
     * @param issueType
     * @return com.yusys.agile.review.dto.ReviewSetDTO
     * @date 2021/3/10
     */
    @Override
    public ReviewSetDTO getReviewSetDTO(Long projectId, Byte issueType) {
        // 查询设置数据
        ReviewSetDTO reviewSetDTO = reviewSetService.getReviewSetInfo(projectId, issueType);
        ReviewSetDTO returnReviewSetDTO = new ReviewSetDTO();

        // 默认开关关闭
        Byte reviewSwitch = null == reviewSetDTO.getReviewSwitch() ? 0 : reviewSetDTO.getReviewSwitch();
        //Byte reviewSwitch = null == reviewSetDTO.getReviewSwitch() ? 1 : reviewSetDTO.getReviewSwitch();
        returnReviewSetDTO.setReviewSwitch(reviewSwitch);
        // 默认全部同意才算通过
        Byte reviewPassRate = null == reviewSetDTO.getReviewPassRate() ? 100 : reviewSetDTO.getReviewPassRate();
        returnReviewSetDTO.setReviewPassRate(reviewPassRate);

        return returnReviewSetDTO;

    }

    /**
     * 功能描述: 根据评审id获取工作项集合
     *
     * @param reviewIds
     * @return java.util.List<Long>
     * @date 2020/12/1
     */
    @Override
    public List<Long> listByListReviewId(List<Long> reviewIds, String tenantCode) {
        ReviewExample example = new ReviewExample();
        ReviewExample.Criteria criteria = example.createCriteria();
        criteria.andReviewIdIn(reviewIds).andStateEqualTo(StateEnum.U.getValue()).andReviewStatusEqualTo(ReviewStatusEnum.REVIEWING.CODE);
        example.setOrderByClause(CREATE_TIME_DESC);
        List<Review> reviewList = reviewMapper.selectByExampleWithBLOBs(example);

        List<Long> issueIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(reviewList)) {
            for (Review review : reviewList) {
                issueIds.add(review.getIssueId());
            }
        }
        return issueIds;
    }

    /**
     * 功能描述:  判断工作项是否通过评审
     *
     * @param issueId
     * @param issueType
     * @return java.lang.Boolean
     * @date 2021/3/9
     */
    private Boolean hasPassReview(Long projectId, Long issueId, Byte issueType) {
        // 查询评审记录
        List<Review> reviews = getReviewList(issueId);
        if (CollectionUtils.isEmpty(reviews)) {
            LOGGER.info("issueId={}没有评审记录，不通过！", issueId);
            return false;
        }

        // 以最近一次的评审记录为准
        Review latestReview = reviews.get(0);
        String reviewStatus = latestReview.getReviewStatus();
        if (StringUtils.equals(reviewStatus, ReviewStatusEnum.PASS.CODE)) {
            return true;
        } else if (StringUtils.equals(reviewStatus, ReviewStatusEnum.FAIL.CODE) || StringUtils.equals(reviewStatus, ReviewStatusEnum.CANCEL.CODE)) {
            return false;
        } else {
            // 计算是否通过评审
            String calculateReviewStatus = calculateReviewStatus(projectId, issueId, issueType, latestReview.getReviewId());
            if (StringUtils.equals(calculateReviewStatus, ReviewStatusEnum.PASS.CODE)) {
                return true;
            } else {
                return false;
            }
        }

    }
}