package com.yusys.agile.user.service.impl;

import com.yusys.agile.commit.dto.CommitDTO;
import com.yusys.agile.commit.service.CommitService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.user.dto.ProjectUserDTO;
import com.yusys.agile.user.service.ProjectUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.entity.SsoUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Date: 2021/2/6
 */
@Service
public class ProjectUserServiceImpl implements ProjectUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectUserServiceImpl.class);

    @Resource
    private IssueMapper issueMapper;
    @Resource
    private CommitService commitService;

    @Override
    public PageInfo commitUserInfo(Long projectId, Long sprintId, Integer pageNum, Integer pageSize, List<SsoUser> ssoUserList) {
        Page page = new Page(pageNum, pageSize);
        List<ProjectUserDTO> projectUserDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ssoUserList)) {
            CommitDTO commitDTO = new CommitDTO();
            commitDTO.setProjectId(projectId);
            List<Long> userIds = ssoUserList.stream().map(SsoUser::getUserId).collect(Collectors.toList());
            commitDTO.setMemberIdList(userIds);
            commitDTO.setSprintId(sprintId);
            CommitDTO result = commitService.getMemberCommitRecord(commitDTO);
            LOGGER.info("invoke getMemberCommitRecord method param commitDTO:{},return result:{}", commitDTO, result);
            //提交次数
            List<Map<String, Integer>> commitTimes = result.getCommitTimes();
            //新增行数
            List<Map<String, Integer>> addLinesList = result.getAddLines();
            //删除行数
            List<Map<String, Integer>> deleteLinesList = result.getDeleteLines();
            for (SsoUser ssoUser : ssoUserList) {
                ProjectUserDTO projectUserDTO = new ProjectUserDTO();
                projectUserDTO.setProjectId(projectId);
                projectUserDTO.setUserId(ssoUser.getUserId());
                projectUserDTO.setUserName(ssoUser.getUserName());
                Integer taskNum = issueMapper.sumTaskByHandler(projectId, ssoUser.getUserId());
                Integer hourNum = issueMapper.sumWorkloadByUser(projectId, ssoUser.getUserId());
                getSubmitOnceNum(commitTimes, ssoUser, projectUserDTO);
                getAddLinesList(addLinesList, ssoUser, projectUserDTO);
                getDeleteLinesList(deleteLinesList, ssoUser, projectUserDTO);
                projectUserDTO.setTaskNum(taskNum);
                projectUserDTO.setHourNum(hourNum);
                projectUserDTOS.add(projectUserDTO);
            }
        }
        return getPageInfo(pageNum, pageSize, page, projectUserDTOS);
    }

    /**
     * @param commitTimes
     * @param ssoUser
     * @param projectUserDTO
     * @Date 2021/2/16
     * @Description 获取代码提交次数
     * @Return void
     */
    private void getSubmitOnceNum(List<Map<String, Integer>> commitTimes, SsoUser ssoUser, ProjectUserDTO projectUserDTO) {
        //LOGGER.info("getSubmitOnceNum method param commitTimes:{},ssoUser:{}", commitTimes, ssoUser);
        if (CollectionUtils.isNotEmpty(commitTimes)) {
            for (int i = 0; i < commitTimes.size(); i++) {
                Map<String, Integer> map = commitTimes.get(i);
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    //LOGGER.info("getSubmitOnceNum method param ssoUserId:{},commitUserId:{}",ssoUser.getUserId(), entry.getKey());
                    if (StringUtils.equals(ssoUser.getUserId().toString(), entry.getKey())) {
                        projectUserDTO.setSubmitOnceNum(entry.getValue());
                        return;
                    } else {
                        projectUserDTO.setSubmitOnceNum(0);
                    }
                }
            }
        } else {
            projectUserDTO.setSubmitOnceNum(0);
        }
    }

    /**
     * @param addLinesList
     * @param ssoUser
     * @param projectUserDTO
     * @Date 2021/2/20
     * @Description 获取代码提交行数
     * @Return void
     */
    private void getAddLinesList(List<Map<String, Integer>> addLinesList, SsoUser ssoUser, ProjectUserDTO projectUserDTO) {
        if (CollectionUtils.isNotEmpty(addLinesList)) {
            for (int i = 0; i < addLinesList.size(); i++) {
                Map<String, Integer> map = addLinesList.get(i);
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (StringUtils.equals(ssoUser.getUserId().toString(), entry.getKey())) {
                        projectUserDTO.setSubmitLineNum(entry.getValue());
                        return;
                    } else {
                        projectUserDTO.setSubmitLineNum(0);
                    }
                }
            }
        } else {
            projectUserDTO.setSubmitLineNum(0);
        }
    }

    /**
     * @param deleteLinesList
     * @param ssoUser
     * @param projectUserDTO
     * @Date 2021/2/20
     * @Description 获取代码减少行数
     * @Return void
     */
    private void getDeleteLinesList(List<Map<String, Integer>> deleteLinesList, SsoUser ssoUser, ProjectUserDTO projectUserDTO) {
        if (CollectionUtils.isNotEmpty(deleteLinesList)) {
            for (int i = 0; i < deleteLinesList.size(); i++) {
                Map<String, Integer> map = deleteLinesList.get(i);
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (StringUtils.equals(ssoUser.getUserId().toString(), entry.getKey())) {
                        projectUserDTO.setReduceLineNum(entry.getValue());
                        return;
                    } else {
                        projectUserDTO.setReduceLineNum(0);
                    }
                }
            }
        } else {
            projectUserDTO.setReduceLineNum(0);
        }
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param page
     * @param projectUserDTOS
     * @Date 2021/2/10
     * @Description 为处理好的list增加分页
     * @Return com.github.pagehelper.PageInfo
     */
    private PageInfo getPageInfo(Integer pageNum, Integer pageSize, Page page, List<ProjectUserDTO> projectUserDTOS) {
        //为Page类中的total属性赋值
        int total = projectUserDTOS.size();
        page.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(projectUserDTOS.subList(startIndex, endIndex));
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page);
        return pageInfo;
    }
}
