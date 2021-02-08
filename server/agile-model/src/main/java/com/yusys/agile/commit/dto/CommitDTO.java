package com.yusys.agile.commit.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @descripton 人员代码提交实体类
 *  
 * @date 2020/07/14
 *
 */
public class CommitDTO implements Serializable {

    private static final long serialVersionUID = -2654457780624482505L;

    /**
     * 项目编号
     */
    private Long projectId;

    /**
     * 迭代编号
     */
    private Long sprintId;

    /**
     * 成员编号
     */
    private List<Long> memberIdList;

    /**
     * 提交次数
     */
    private List<Map<String, Integer>> commitTimes;

    /**
     * 新增行数
     */
    private List<Map<String, Integer>> addLines;

    /**
     * 删除行数
     */
    private List<Map<String, Integer>> deleteLines;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public List<Long> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<Long> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public List<Map<String, Integer>> getCommitTimes() {
        return commitTimes;
    }

    public void setCommitTimes(List<Map<String, Integer>> commitTimes) {
        this.commitTimes = commitTimes;
    }

    public List<Map<String, Integer>> getAddLines() {
        return addLines;
    }

    public void setAddLines(List<Map<String, Integer>> addLines) {
        this.addLines = addLines;
    }

    public List<Map<String, Integer>> getDeleteLines() {
        return deleteLines;
    }

    public void setDeleteLines(List<Map<String, Integer>> deleteLines) {
        this.deleteLines = deleteLines;
    }

    @Override
    public String toString() {
        return "CommitDTO{" +
                "projectId=" + projectId +
                ", sprintId=" + sprintId +
                ", memberIdList=" + memberIdList +
                ", commitTimes=" + commitTimes +
                ", addLines=" + addLines +
                ", deleteLines=" + deleteLines +
                '}';
    }
}
