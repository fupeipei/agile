package com.yusys.agile.issue.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yusys.agile.issue.excel.ExcelUtil;
import com.yusys.agile.issue.excel.handler.SpinnerWriteHandler;
import com.yusys.agile.issue.excel.service.DownloadExcelTempletService;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  @Description: 故事模版下载
 *  @author: zhao_yd
 *  @Date: 2021/5/26 10:08 上午
 *
 */

@Slf4j
@Service("storyDownloadService")
public class StoryTemplateDownloadServiceImpl implements DownloadExcelTempletService {

    @Autowired
    private Sprintv3Service sprintv3Service;


    @Override
    public void download(HttpServletResponse response) {
        Map<Integer,String []> mapDropDown = new HashMap<>();
        String[] sprintInfo = getSprintInfo();
        sprintInfo = new String[]{"aaa", "bbbb","cccc"};
        mapDropDown.put(3,sprintInfo);
        SpinnerWriteHandler spinnerWriteHandler = new SpinnerWriteHandler(mapDropDown);
        // 模板名称，文件位置：resources/templates/excel
        try {
            String  templateFileName = "excelTemplate" +  File.separator + "storyImportTemplate.xlsx";
            // 写入excel,springboot 使用new ClassPathResource();
            ExcelWriter excelWriter = EasyExcel.write(ExcelUtil.dealResponse(UUID.randomUUID().toString(),response)).withTemplate
                    (new ClassPathResource(templateFileName).getInputStream()).registerWriteHandler(spinnerWriteHandler).build();
            //excelWriter.wait();
            excelWriter.finish();
        } catch (IOException  e) {
            log.error("导出Story模版异常:{}",e.getMessage());
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
