package com.yusys.agile.easyexcel.service;

import com.yusys.agile.easyexcel.vo.ExcelCommentFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  @Description: 模版下载Service
 *  @author: zhao_yd
 *  @Date: 2021/5/26 9:27 上午
 *
 */

public interface DownloadExcelTempletService {

    void download(HttpServletResponse response, ExcelCommentFile filed);

    Map<Integer,String []> getDropDownInfo(ExcelCommentFile filed);
}
