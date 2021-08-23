package com.yusys.agile.sysextendfield;

import com.yusys.agile.issue.dto.IssueDTO;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RequirementDTO that = (RequirementDTO) o;
        return Objects.equals(sysExtendField, that.sysExtendField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sysExtendField);
    }
}
