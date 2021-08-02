package com.yusys.agile.easyexcel.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *  @Description: 工厂类
 *  @author: zhao_yd
 *  @Date: 2021/5/28 3:55 下午
 *
 */
@Service
public class ExcelTempletFactory implements ApplicationContextAware {

    private static final String Epic = "Epic";
    private static final String STORY = "Story";
    private static final String TASK = "Task";

    private static ApplicationContext applicationContext;


    public static DownloadExcelTempletService get(String type){
        DownloadExcelTempletService downloadExcelTempletService;
        switch (type){
            case Epic:
                downloadExcelTempletService = (DownloadExcelTempletService) applicationContext.getBean("epicTemplateDownloadService");
                break;
            case STORY:
                downloadExcelTempletService = (DownloadExcelTempletService) applicationContext.getBean("storyTemplateDownloadService");
                break;
            case TASK:
                downloadExcelTempletService = (DownloadExcelTempletService) applicationContext.getBean("taskTemplateDownloadService");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return downloadExcelTempletService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ExcelTempletFactory.applicationContext=applicationContext;
    }
}
