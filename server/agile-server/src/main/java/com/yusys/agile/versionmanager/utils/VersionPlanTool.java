package com.yusys.agile.versionmanager.utils;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.requirement.domain.SysExtendFieldDetail;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.enums.OperateTypeEnum;
import com.yusys.agile.versionmanager.enums.SyncResultEnum;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VersionPlanTool {
    /**
     * 功能描述
     *    xuhao
     * @date 2020/4/21
     * @description 获得版本计划中已经绑定的业务需求的ID
     * @param versionPlanList
     * @return java.util.List<java.lang.Long>
     **/
    public static List<Long> getVersionPlanBindingRequirementIds(List<VersionManagerDTO> versionPlanList) {
        List<Long> requirementIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(versionPlanList)) {
            return requirementIds;
        }
        for (VersionManagerDTO versionPlan : versionPlanList) {
            for (IssueDTO bizBacklog : versionPlan.getRelateIssueList()) {
                requirementIds.add(bizBacklog.getIssueId());
            }
        }
        return requirementIds;
    }

}
