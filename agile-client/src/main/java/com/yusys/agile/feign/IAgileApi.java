package com.yusys.agile.feign;

import com.yusys.agile.issue.dto.IssueStageIdCountDTO;
import com.yusys.agile.team.dto.TeamListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "agile-server")
public interface IAgileApi {
    @GetMapping("/agile/issue/stage/countForSso")
    List<IssueStageIdCountDTO> countIssueByStageId(@RequestParam("projectId") Long projectId);

    @PostMapping("/agile/v3/team/queryTeamList")
    List<TeamListDTO>  queryTeamList(@RequestParam(name = "teamName", required = false) String teamName,
                                      @RequestParam(name = "teamIds") List<Long> teamIds,
                                      @RequestParam(name = "pageNum") Integer pageNum,
                                      @RequestParam(name = "pageSize") Integer pageSize);

}



