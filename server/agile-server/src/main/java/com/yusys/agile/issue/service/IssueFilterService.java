package com.yusys.agile.issue.service;

import com.yusys.agile.issue.dto.IssueFilterDTO;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;

public interface IssueFilterService {

    /**
     * 过滤器保存
     * @param issueFilterDTO
     * @param securityDTO
     * @return
     */
    ControllerResponse saveIssueFilter(IssueFilterDTO issueFilterDTO, SecurityDTO securityDTO);

    /**
     *根据filterId删除过滤器
     * @param filterId 过滤器主键ID
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    ControllerResponse deleteIssueFilter(Long filterId,Byte category);

    /**
     * 根据项目ID和登录人员的ID获取过滤器列表数据。
     * @param securityDTO
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    ControllerResponse getIssueFilters(Byte category, SecurityDTO securityDTO);

    /**
     * 更新为当前过滤器ID为默认选中状态
     * @param filterId  过滤器ID
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    ControllerResponse putFilterCheckStatus(Long filterId, Byte category, SecurityDTO securityDTO);
}
