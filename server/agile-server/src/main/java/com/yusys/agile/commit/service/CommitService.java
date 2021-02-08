package com.yusys.agile.commit.service;

import com.yusys.agile.commit.dto.CommitDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CommitService {

    CommitDTO getMemberCommitRecord(CommitDTO commitDTO);

    //PageInfo<List<com.ai.cicd.model.cms.dto.CommitDTO>> getIssueCommitRecord(Long issueId, Byte issueType, Integer pageNumber, Integer pageSize);
}
