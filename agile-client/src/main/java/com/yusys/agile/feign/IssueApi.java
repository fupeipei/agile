package com.yusys.agile.feign;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.projectmanager.dto.StageNameAndValueDto;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName: IssueApi
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/06 17:10
 */
@FeignClient(name = "agile-server")
public interface IssueApi {

    @GetMapping("/agile/issue/listIssueOfProjectAndUser")
    List<IssueDTO> listIssueOfProjectAndUser(@RequestParam(name = "systemIds")List<Long> systemIds,@RequestParam(name = "userId") Long userId);


    @GetMapping("/agile/issue/getCollectIssueDataBySystemId")
    List<StageNameAndValueDto> getCollectIssueDataBySystemId(@RequestParam(name = "systemId")Long systemId);

    @GetMapping("/agile/issue/queryIssueListBySystemIds")
    List<IssueDTO> queryIssueListBySystemIds(@RequestParam(name = "systemIds")List<Long> systemIds,@RequestParam(name = "issueType") int issueType);


}
