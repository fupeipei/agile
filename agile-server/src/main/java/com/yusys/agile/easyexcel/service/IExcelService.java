package com.yusys.agile.easyexcel.service;

import javax.servlet.http.HttpServletResponse;

/**
 *  @Description: excel服务
 *  @author: zhao_yd
 *  @Date: 2021/5/25 8:43 下午
 *
 */

public interface IExcelService {

    void downLoadTemplate(Byte excelType, HttpServletResponse response);

}
