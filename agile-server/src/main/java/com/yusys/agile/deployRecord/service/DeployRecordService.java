package com.yusys.agile.deployRecord.service;

import com.github.pagehelper.PageInfo;


public interface DeployRecordService {


    PageInfo queryDeployRecord(long issueId, Byte issueType, Integer pageNum, Integer pageSize) ;
}
