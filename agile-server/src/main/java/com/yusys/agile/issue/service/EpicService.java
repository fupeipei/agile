package com.yusys.agile.issue.service;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueStageIdCountDTO;

import java.util.List;

public interface EpicService {

    /**
     * @Date: 18:03
     * @Description: 创建业务需求
     * @Param: * @param issueDTO
     * @Return: Long issueId
     */
    Long createEpic(IssueDTO issueDTO);

    /**
     * @Date: 18:03
     * @Description: 删除业务需求
     * @Param: * @param issueId
     * @Return: int
     */
    void deleteEpic(Long issueId);

    /**
     * @Date: 18:03
     * @Description: 查询业务需求
     * @Param: * @param issueId
     * @Return: com.yusys.agile.issue.dto.IssueDTO
     */
    //IssueDTO queryEpic(Long issueId, Long projectId);
    IssueDTO queryEpic(Long issueId);


    /**
     * @param
     * @Date: 18:02
     * @Description: 编辑业务需求
     * @Param: * @param issueDTO
     * @Return: void
     */
    void editEpic(IssueDTO issueDTO);

    /**
     * @param projectId
     * @Date: 9:30
     * @Description: 复制业务需求
     * @Param: * @param epicId
     * @Return: void
     */
    Long copyEpic(Long epicId, Long projectId);

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 2021/2/3 14:56
     * @Description: 分页查询所有业务需求
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryAllEpic(Long projectId, Integer pageNum, Integer pageSize, String title);

    /**
     * @param projectId :
     * @Date: 2021/3/29
     * @Description: 按版本统计系统各个阶段需求个数
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueStageIdCountDTO>
     */
    List<IssueStageIdCountDTO> queryAllEpicCountByVersionId(Long projectId);

    /**
     * 功能描述:根据epicId查询下面所有的featureId
     *
     * @param epicId
     * @return java.util.List<java.lang.Long>
     * @date 2020/10/13
     */
    List<Long> queryFeatureIdsByEpicId(Long epicId);


}
