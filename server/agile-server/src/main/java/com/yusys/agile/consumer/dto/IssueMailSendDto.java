package com.yusys.agile.consumer.dto;

import com.yusys.agile.issue.domain.Issue;
import com.yusys.portal.model.facade.dto.SecurityDTO;

/**
 * @ClassName IssueMailSendDto
 * @Description 邮件发送封装类
 * @Date 2021/2/1
 * @Version 1.0
 */
public class IssueMailSendDto {
    private Issue issue;
    private Integer operationType;
    private SecurityDTO securityDTO;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public SecurityDTO getSecurityDTO() {
        return securityDTO;
    }

    public void setSecurityDTO(SecurityDTO securityDTO) {
        this.securityDTO = securityDTO;
    }

    public IssueMailSendDto() {
    }

    public IssueMailSendDto(Issue issue, Integer operationType, SecurityDTO securityDTO) {
        this.issue = issue;
        this.operationType = operationType;
        this.securityDTO = securityDTO;
    }
}
