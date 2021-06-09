package com.yusys.agile.client;


import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName IToolsChangeClient
 * @Description 构建记录Feign
 * @Date 2021/2/1
 * @Version 1.0
 */
@FeignClient(name = "cicd-tools")
public interface IToolsChangeClient {
    /**
     * 根据工作项ID获取构建记录
     *
     * @param taskId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping({"/tools/feign/queryBuildInstanceByTaskId"})
    PageInfo queryBuildInstanceByTaskId(@RequestParam("taskId") String taskId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据工作项ID获取部署记录
     *
     * @param taskId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping({"/tools/feign/queryDeployInstanceByTaskId"})
    PageInfo queryDeployInstanceByTaskId(@RequestParam("taskId") String taskId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

}
