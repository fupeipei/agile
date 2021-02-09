package com.yusys.agile.buildrecords.service.impl;

import com.yusys.agile.buildrecords.service.BuildDeployService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 流水线构建部署业务实现类
 * @date 2021/2/1
 */
@Service
public class BuildDeployServiceImpl implements BuildDeployService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildDeployServiceImpl.class);

   /* @Autowired
    private ICmsChangeApi iCmsChangeApi;*/

    @Resource
    private IssueMapper issueMapper;

    /**
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询流水线构建记录
     */
    @Override
    public PageInfo queryBuildRecord(Long issueId, Integer pageNum, Integer pageSize) {
        Assert.notNull(issueId, "issueId不能为空");
        LOGGER.info("queryBuildRecord param issueId:{}, pageNum:{}, pageSize:{}", issueId, pageNum, pageSize);
        PageInfo pageInfo = null;
        pageNum = null == pageNum ? 1 : pageNum;
        pageSize = null == pageSize ? 20 : pageSize;
        Issue issue = getIssueByPrimaryKey(issueId);
        Byte issueType = issue.getIssueType();
        /*if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            List<String> taskIds = getTaskIds(issueId);
            if (CollectionUtils.isNotEmpty(taskIds)) {
                TaskIdsDTO taskIdsDTO = assembleTaskIdsDTO(pageNum, pageSize, taskIds);
                pageInfo = iCmsChangeApi.queryBuildInstanceByTaskIds(taskIdsDTO);
            } else {
                pageInfo = new PageInfo();
                pageInfo.setPageNum(pageNum);
                pageInfo.setPageSize(pageSize);
            }
        } else {
            pageInfo = iCmsChangeApi.queryBuildInstanceByTaskId(issueId.toString(), pageNum, pageSize);
        }*/
        return pageInfo;
    }

    /**
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询流水线部署记录
     */
    @Override
    public PageInfo queryDeployRecord(Long issueId, Integer pageNum, Integer pageSize) {
        Assert.notNull(issueId, "issueId不能为空");
        LOGGER.info("queryDeployRecord param issueId:{}, pageNum:{}, pageSize:{}", issueId, pageNum, pageSize);
        PageInfo pageInfo = null;
        pageNum = null == pageNum ? 1 : pageNum;
        pageSize = null == pageSize ? 20 : pageSize;
        Issue issue = getIssueByPrimaryKey(issueId);
        Byte issueType = issue.getIssueType();
        /*if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            List<String> taskIds = getTaskIds(issueId);
            if (CollectionUtils.isNotEmpty(taskIds)) {
                TaskIdsDTO taskIdsDTO = assembleTaskIdsDTO(pageNum, pageSize, taskIds);
                pageInfo = iCmsChangeApi.queryDeployInstanceByTaskIds(taskIdsDTO);
            } else {
                pageInfo = new PageInfo();
                pageInfo.setPageNum(pageNum);
                pageInfo.setPageSize(pageSize);
            }
        } else {
            pageInfo = iCmsChangeApi.queryDeployInstanceByTaskId(issueId.toString(), pageNum, pageSize);
        }*/
        return pageInfo;
    }

    /**
     * @param issueId
     * @return
     * @description 根据issueId查询issue
     * @date 2021/2/1
     */
    private Issue getIssueByPrimaryKey(Long issueId) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        if (null == issue) {
            throw new RuntimeException("issueId:{}对应的issue不存在");
        }
        return issue;
    }

    /**
     * @param storyId
     * @return
     * @description 根据故事id查询故事下所有任务id
     * @date 2021/2/1
     */
    private List<String> getTaskIds(Long storyId) {
        List<String> taskIds = Lists.newArrayList();
        IssueExample example = new IssueExample();
        example.createCriteria()
                .andParentIdEqualTo(storyId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_TASK.CODE);
        List<Issue> issueList = issueMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(issueList)) {
            issueList.forEach(task -> {
                taskIds.add(String.valueOf(task.getIssueId()));
            });
        }
        return taskIds;
    }

    /**
     * @description 组装TaskIdsDTO对象
     * @date 2021/2/1
     * @param pageNum
     * @param pageSize
     * @param taskIds
     * @return
     */
   /* private TaskIdsDTO assembleTaskIdsDTO(Integer pageNum, Integer pageSize, List<String> taskIds) {
        TaskIdsDTO taskIdsDTO = new TaskIdsDTO();
        taskIdsDTO.setPageNum(pageNum);
        taskIdsDTO.setPageSize(pageSize);
        taskIdsDTO.setTaskIds(taskIds);
        return taskIdsDTO;
    }*/
}
