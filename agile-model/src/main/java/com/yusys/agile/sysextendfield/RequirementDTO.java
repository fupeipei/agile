package com.yusys.agile.sysextendfield;

import com.yusys.agile.issue.dto.IssueDTO;

import java.util.Map;

/**
 * :
 *
 * @Date: 2021/3/10
 * @Description: TODO
 */
public class RequirementDTO extends IssueDTO {


    public Map sysExtendField;

    public Map getSysExtendField() {
        return sysExtendField;
    }

    public void setSysExtendField(Map sysExtendField) {
        this.sysExtendField = sysExtendField;
    }
}
