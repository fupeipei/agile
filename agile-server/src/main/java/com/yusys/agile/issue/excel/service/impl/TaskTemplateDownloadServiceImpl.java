package com.yusys.agile.issue.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yusys.agile.issue.excel.ExcelUtil;
import com.yusys.agile.issue.excel.handler.SpinnerWriteHandler;
import com.yusys.agile.issue.excel.service.DownloadExcelTempletService;
import com.yusys.agile.issue.excel.vo.StoryExcelModul;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  @Description: 任务模版下载
 *  @author: zhao_yd
 *  @Date: 2021/5/28 3:56 下午
 *
 */

@Slf4j
@Service("taskDownloadService")
public class TaskTemplateDownloadServiceImpl implements DownloadExcelTempletService {


    @Autowired
    private Sprintv3Service sprintv3Service;

    @Override
    public void download(HttpServletResponse response) {
        //todo 下拉填充数据
        Map<Integer,String []> mapDropDown = new HashMap<>();
        String[] sprintInfo = getSprintInfo();
        mapDropDown.put(3,sprintInfo);
        SpinnerWriteHandler spinnerWriteHandler = new SpinnerWriteHandler(mapDropDown);
        try {
            EasyExcel.write(ExcelUtil.dealResponse(UUID.randomUUID().toString(),response), StoryExcelModul.class)
                    .autoCloseStream(Boolean.TRUE)
                    .sheet("tasks")
                    .registerWriteHandler(spinnerWriteHandler)
                    .doWrite(new ArrayList());
        } catch (IOException e) {
            log.error("导出task模版异常:{}",e.getMessage());
        }
    }


    private String[] getSprintInfo(){
        Long systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
        List<SprintListDTO> sprintListDTOS = sprintv3Service.getEffectiveSprintsBySystemId(systemId);
        if(CollectionUtils.isNotEmpty(sprintListDTOS)){
            Set<String> collect = sprintListDTOS.stream().
                    map(s-> s.getSprintId() + "+" + s.getSprintName()).collect(Collectors.toSet());
            return collect.toArray(new String[0]);
        }
        return new String[]{};
    }
}
