package com.yusys.agile.feign;

import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: IssueApi
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/06 17:10
 */
@FeignClient(name = "agile-server")
public interface IssueApi {

    @GetMapping("/agile/issue/listIssueOfProjectAndUser")
    public ControllerResponse listIssueOfProjectAndUser(@RequestParam("projectId") Long projectId, SecurityDTO securityDTO);
}
