package com.yusys.agile.utils;

import com.alibaba.druid.util.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FileUtil {
    private FileUtil() {
    }

    public static String getFileName(HttpServletRequest request, String fileName) throws IOException {
        if (isIE(request)) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF8");
        } else if (request.getHeader("USER-AGENT") != null
                && request.getHeader("USER-AGENT").toLowerCase().contains("firefox")) {
            fileName = "=?UTF-8?B?" + (new String(Base64.byteArrayToBase64(fileName.getBytes("utf-8"))))
                    + "?=";
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        return fileName;
    }

    private static boolean isIE(HttpServletRequest request) {
        return request.getHeader("USER-AGENT").toLowerCase().contains("msie")
                || request.getHeader("USER-AGENT").toLowerCase().contains("rv:11.0")
                || request.getHeader("USER-AGENT").toLowerCase().contains("edge");
    }
}
