package com.yusys.agile.feign;


import com.yusys.agile.issue.dto.CustomizeIssueDTO;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description 提供cmp工作项有关接口
 *  
 * @date 2020/09/17
 */
@FeignClient(name="agile-server")
public interface ICustomizeIssueApi {

    @PostMapping("/agile/customizeIssue/task/getRelatedIssues")
    public ControllerResponse<List<CustomizeIssueDTO>> getRelatedIssues(@RequestBody List<Long> issueIds);

    @GetMapping("/agile/customizeIssue/feature/getFeatureByTaskOrFaultId")
    public ControllerResponse<CustomizeIssueDTO> getFeatureByTaskOrFaultId(@RequestParam("issueId") Long issueId);
}
