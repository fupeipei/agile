package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueDTO;

import java.util.List;

public interface FeatureService {

    /**
     *
     * @Date: 18:03
     * @Description: 删除研发需求
     * @Param: * @param featureId
    * @param deleteChild
     * @Return: int
     */
    //void deleteFeature(Long featureId, Boolean deleteChild,Long projectId);
    void deleteFeature(Long featureId, Boolean deleteChild);

    /**
     *
     * @Date: 18:04
     * @Description: 查询研发需求
     * @Param: * @param featureId
     * @Return: com.yusys.agile.issue.dto.IssueDTO
     */
    //IssueDTO queryFeature(Long featureId, Long projectId);
    IssueDTO queryFeature(Long featureId);

    /**
     *
     * @Date: 18:04
     * @Description: 编辑研发需求
     * @Param: * @param issueDTO
    * @param
     * @Return: void
     */
    void editFeature(IssueDTO issueDTO);

    /**
     *
     * @Date: 18:04
     * @Description: 创建研发需求
     * @Param: * @param issueDTO
     * @Return: Long issueId
     */
    Long createFeature(IssueDTO issueDTO);

    /**
     *
     * @Date: 9:30
     * @Description: 复制研发需求
     * @Param: * @param featureId
    * @param projectId
     * @Return: Long
     */
    Long copyFeature(Long featureId, Long projectId);

    /**
     *
     * @Date: 13:23
     * @Description: 查询未关联的研发需求
     * @Param: * @param projectId
     * @param pageNum
     * @param pageSize
     * @param title
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryUnlinkedFeature(Long projectId,Integer pageNum,Integer pageSize,String title);
    /**
     *
     * @Date: 2021/2/3 14:57
     * @Description: 分页查询所有研发需求
     * @Param: * @param projectId
    * @param pageNum
    * @param pageSize
     * @param title
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryAllFeature(Long projectId, Integer pageNum, Integer pageSize,String title);

    /**
     *
     * @Date: 2021/2/9 9:31
     * @Description: 查询某epic下所有的feature
     * @Param: * @param projectId
    * @param epicId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryFeatureForEpic(Long projectId, Long epicId);


    /**
     *功能描述 根据epicId列表查询所有Feature列表
     *
     * @date 2021/2/4
      * @param epicList
     * @return java.util.List<com.yusys.agile.issue.domain.Issue>
     */
    List<Issue> queryFeatureByEpicIds(List<Long> epicList);
}
