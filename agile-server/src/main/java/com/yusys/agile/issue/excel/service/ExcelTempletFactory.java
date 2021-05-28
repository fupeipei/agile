package com.yusys.agile.issue.excel.service;

import com.yusys.agile.issue.excel.service.impl.StoryTemplateDownloadServiceImpl;
import com.yusys.agile.issue.excel.service.impl.TaskTemplateDownloadServiceImpl;

/**
 *  @Description: 工厂类
 *  @author: zhao_yd
 *  @Date: 2021/5/28 3:55 下午
 *
 */

public class ExcelTempletFactory {

    private static final String STORY = "Story";
    private static final String TASK = "Task";


    public static DownloadExcelTempletService get(String type){
        DownloadExcelTempletService downloadExcelTempletService;
        switch (type){
            case STORY:
                downloadExcelTempletService =  new StoryTemplateDownloadServiceImpl();
                break;
            case TASK:
                downloadExcelTempletService = new TaskTemplateDownloadServiceImpl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return downloadExcelTempletService;
    }

}
