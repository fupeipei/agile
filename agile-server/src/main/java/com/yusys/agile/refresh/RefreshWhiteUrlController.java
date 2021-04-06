package com.yusys.agile.refresh;

import com.yusys.portal.common.config.FilterConfig;
import com.yusys.portal.common.register.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenfeng
 * @description
 * @date 2021/3/3
 */
@RestController
public class RefreshWhiteUrlController {
    @Autowired
    private FilterConfig filterConfig;

    @GetMapping("/refreshWhiteUrl")
    public void queryApplicationByProjectId(@RequestParam(name = "projectId", required = false) Long projectId) {
        DynamicDataSource.setDataSourceType("base");
        filterConfig.refresh();
        System.out.println("login---"+ FilterConfig.LOGIN_EXCLUSIONS);
    }

}
