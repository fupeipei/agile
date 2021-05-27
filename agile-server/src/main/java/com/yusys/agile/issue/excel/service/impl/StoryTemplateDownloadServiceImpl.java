package com.yusys.agile.issue.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public void download() {
        Map<Integer,String []> mapDropDown = new HashMap<>();
        String[] sprintInfo = getSprintInfo();
        mapDropDown.put(3,sprintInfo);
        SpinnerWriteHandler spinnerWriteHandler = new SpinnerWriteHandler(mapDropDown);

        ClassPathResource classPathResource = new ClassPathResource("excelTemplate/storyImportTemplate.xlsx");
        File file = null;
        try {
            file = classPathResource.getFile();
            EasyExcel.write(file).excelType(ExcelTypeEnum.XLSX).registerWriteHandler(spinnerWriteHandler);
        } catch (IOException e) {
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
