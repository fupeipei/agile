package com.yusys.agile.feign;

import com.yusys.agile.issue.dto.IssueStageIdCountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "agile-server")
public interface IAgileApi {
    @GetMapping("/agile/issue/stage/countForSso")
    List<IssueStageIdCountDTO> countIssueByStageId(@RequestParam("projectId") Long projectId);


}
