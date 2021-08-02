package com.yusys.agile.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.easyexcel.ExcelUtil;
import com.yusys.agile.easyexcel.handler.SpinnerWriteHandler;
import com.yusys.agile.easyexcel.service.DownloadExcelTempletService;
import com.yusys.agile.easyexcel.vo.ExcelCommentField;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author maxp2
 * @Date 2021/8/2
 * @Description epic模版下载
 */
@Slf4j
@Service("epicTemplateDownloadService")
public class EpicTemplateDownloadServiceImpl implements DownloadExcelTempletService {

    @Override
    public void download(HttpServletResponse response, ExcelCommentField field) {
        Map<Integer, String[]> mapDropDown = getDropDownInfo(field);
        SpinnerWriteHandler spinnerWriteHandler = new SpinnerWriteHandler(mapDropDown);
        try {
            ClassPathResource classPathResource = new ClassPathResource("excelTemplate/epicImportTemplate.xlsx");
            EasyExcel.write(ExcelUtil.dealResponse("epicImportTemplate",response))
                    .withTemplate(classPathResource.getInputStream())
                    .autoCloseStream(Boolean.TRUE)
                    .sheet("epics")
                    .registerWriteHandler(spinnerWriteHandler)
                    .doWrite(new ArrayList());
        } catch (IOException  e) {
            log.error("导出Epic模版异常:{}",e.getMessage());
        }
    }

    @Override
    public Map<Integer, String[]> getDropDownInfo(ExcelCommentField field) {
        Map<Integer,String []> mapDropDown = new HashMap<>();
        String[] epicPriority = getEpicPriority();
        String[] epicImportance = getEpicImportance();
        mapDropDown.put(4,epicPriority);
        mapDropDown.put(6,epicImportance);
        return mapDropDown;
    }


    /**
     * 优先级下拉项
     * @return
     */
    private String[] getEpicPriority(){
        List<String> list = Lists.newArrayList();
        int count = 100;
        for(int i = 0;i< 100; i++){
            list.add(String.valueOf(count-i));
        }
        return list.toArray(new String[0]);
    }

    /**
     * 重要程度
     * @return
     */
    private String[] getEpicImportance(){
        return new String[]{"严重","重要","一般","信息"};
    }

}
