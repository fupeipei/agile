package com.yusys.agile.versionmanager.service.impl;


import com.yusys.agile.utils.page.PageQuery;
import com.yusys.agile.versionmanager.dto.BjVersionApproveResultDTO;
import com.yusys.agile.versionmanager.service.VersionApproveResultService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class VersionApproveResultServiceImpl implements VersionApproveResultService {



    @Override
    public List<BjVersionApproveResultDTO> getAllRecordsByPaging(PageQuery<BjVersionApproveResultDTO> query) {
        List<BjVersionApproveResultDTO> resultList = new ArrayList<>();
        if (CollectionUtils.isEmpty(resultList)) {
            return Collections.emptyList();
        }
        return resultList;
    }

    @Override
    public int countAllRecords(BjVersionApproveResultDTO versionApproveResult) {
        return 0;
    }
}
