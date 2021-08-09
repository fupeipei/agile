package com.yusys.agile.feign;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.issue.dto.IssueConditionDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.projectmanager.dto.StageNameAndValueDto;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/agile/issue/listIssueDtoOfProjectAndUser")
    public List<IssueDTO> listIssueDtoOfProjectAndUser(@RequestParam("projectId") Long projectId, SecurityDTO securityDTO);


    @GetMapping("/agile/issue/getCollectIssueDataBySystemId")
    List<StageNameAndValueDto> getCollectIssueDataBySystemId(@RequestParam(name = "systemId")Long systemId);

    @GetMapping("/agile/issue/queryIssueListBySystemIds")
    List<IssueDTO> queryIssueListBySystemIds(@RequestParam(name = "systemIds")List<Long> systemIds,@RequestParam(name = "issueType") int issueType);

    /**
     * Post 请求防止集合中条件元素过多报错
     * @param issueConditionDTO
     * @return
     */
    @PostMapping("/agile/issue/queryEpicList")
    PageInfo<IssueDTO> queryIssueList(@RequestBody IssueConditionDTO issueConditionDTO);


}
