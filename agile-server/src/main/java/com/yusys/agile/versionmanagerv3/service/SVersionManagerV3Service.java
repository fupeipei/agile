package com.yusys.agile.versionmanagerv3.service;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.versionmanagerV3.SVersionIssueRelateDTO;
import com.yusys.agile.versionmanagerV3.SVersionManagerDTO;

import java.util.List;


public interface SVersionManagerV3Service {

    void saveVersionManager(SVersionManagerDTO sVersionManagerDTO);

    void updateVersionManager(SVersionManagerDTO sVersionManagerDTO);

    void deleteVersionManager(Long id);

    PageInfo<SVersionManagerDTO> queryVersionManagerList(Integer pageNum, Integer pageSize, String searchKey) throws Exception;

    PageInfo<SVersionIssueRelateDTO> queryRequirementRelList(Integer pageNum, Integer pageSize, String searchKey, Long teamId, String operateType, List<Long> issueIds) throws Exception;
}
