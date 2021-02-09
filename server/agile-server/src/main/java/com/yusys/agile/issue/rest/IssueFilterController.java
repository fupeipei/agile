package com.yusys.agile.issue.rest;


import com.yusys.agile.issue.dto.IssueFilterDTO;
import com.yusys.agile.issue.service.IssueFilterService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 过滤器操作
 * @Date 2020/06/08 15:00
 */
@RestController
@RequestMapping("/issue/filter")
public class IssueFilterController {

    @Autowired
    private IssueFilterService issueFilterService;


    /**
     * 保存过滤器
     *
     * @param issueFilterDTO
     * @param securityDTO
     * @return
     */
    @PostMapping("/save")
    public ControllerResponse saveIssueFilter(@RequestBody IssueFilterDTO issueFilterDTO, SecurityDTO securityDTO) {
        return issueFilterService.saveIssueFilter(issueFilterDTO, securityDTO);
    }

    /**
     * 根据filterId删除过滤器
     *
     * @param filterId 过滤器主键ID
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @DeleteMapping("/{filterId}/{category}")
    public ControllerResponse deleteIssueFilter(@PathVariable("filterId") Long filterId, @PathVariable("category") Byte category) {
        return issueFilterService.deleteIssueFilter(filterId, category);
    }


    /**
     * 根据项目ID和登录人员的ID获取过滤器列表数据
     *
     * @param securityDTO
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/list")
    public ControllerResponse getIssueFilters(@RequestParam("category") Byte category, SecurityDTO securityDTO) {
        return issueFilterService.getIssueFilters(category, securityDTO);
    }

    /**
     * 更新为当前过滤器ID为默认选中状态
     *
     * @param filterId 过滤器ID
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/update/{filterId}/{category}")
    public ControllerResponse putFilterCheckStatus(@PathVariable("filterId") Long filterId, @PathVariable("category") Byte category, SecurityDTO securityDTO) {
        return issueFilterService.putFilterCheckStatus(filterId, category, securityDTO);
    }

}
