package com.yusys.agile.requirement;

import com.yusys.agile.issue.dto.IssueDTO;

import java.util.Map;

/**
 *   :
 * @Date: 2020/9/10
 * @Description: TODO
 */
public class RequirementDTO extends IssueDTO {


    Map   sysExtendField;

    public Map getSysExtendField() {
        return sysExtendField;
    }

    public void setSysExtendField(Map sysExtendField) {
        this.sysExtendField = sysExtendField;
    }
}
