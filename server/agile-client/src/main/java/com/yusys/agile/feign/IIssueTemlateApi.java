package com.yusys.agile.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="agile-server")
public interface IIssueTemlateApi {



        /**
         * @description 初始工作项模板
         *      
         * @param projectId
         * @return
         */
        @GetMapping("/agile/issueTemplate/init")
        public void initIssueTemplate(@RequestParam("projectId") Long  projectId);

    }
