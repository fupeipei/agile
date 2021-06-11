package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueTemplateMapper;
import com.yusys.agile.issue.domain.IssueTemplate;
import com.yusys.agile.issue.domain.IssueTemplateExample;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueTemplateService;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * :
 *
 * @Date: 2021/2/31
 * @Description: 项目模板
 */
@Service
public class IssueTemplateServiceImpl implements IssueTemplateService {

    private static final String TEMPLETNAME = "默认模板";

    @Resource
    IssueTemplateMapper issueTemplateMapper;
    @Resource
    IssueCustomRelationService issueCustomRelationService;

    /**
     * 功能描述   新建项目初始化模板
     *
     * @param systemId
     * @return void
     * @date 2021/2/31
     */
    @Override
    public void initIssueTemplate(Long systemId) {

        String[] strings = {"<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p><span style=\"font-weight: bold;\">[缺陷描述]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[重现步骤]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[期望结果]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[实际结果]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[原因定位]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[修复建议]：</span></p>"};
        for (int i = 0; i < 5; i++) {
            IssueTemplate issueTemplate = new IssueTemplate();
            issueTemplate.setSystemId(systemId);
            issueTemplate.setApply(Byte.parseByte("1"));
            issueTemplate.setTemplateName(TEMPLETNAME);
            issueTemplate.setIssueType(Byte.parseByte(String.valueOf(i + 1)));
            issueTemplate.setDescription(strings[i]);
            issueTemplateMapper.insert(issueTemplate);
        }
    }

    /**
     * 功能描述  工作项与模板初始化查询接口
     *
     * @param issueType
     * @param securityDTO
     * @return java.util.Map
     * @date 2020/8/3
     */

    @Override
    public Map query(Byte issueType, SecurityDTO securityDTO) {
        Map map = new HashMap();
        IssueTemplateExample issueTemplateExample = new IssueTemplateExample();
        IssueTemplateExample.Criteria criteria = issueTemplateExample.createCriteria();
        criteria.andApplyEqualTo(Byte.parseByte("1"))
                .andIssueTypeEqualTo(issueType);
                if(securityDTO.getProjectId()!=null){
                    criteria.andSystemIdEqualTo(securityDTO.getProjectId());
                }
        if (issueTemplateMapper.selectByExampleWithBLOBs(issueTemplateExample).isEmpty()) {
           //暂时先注释掉
             initIssueTemplate(securityDTO.getSystemId());
        }
        map.put("issueTemplateData", issueTemplateMapper.selectByExampleWithBLOBs(issueTemplateExample));
        map.put("issueCustomRelationData", issueCustomRelationService.getIssueCustomRelations(securityDTO.getSystemId(), issueType));
        return map;
    }

    /**
     * 模板更新接口
     * 功能描述
     *
     * @param securityDTO
     * @param issueTemplate
     * @return java.lang.Integer
     * @date 2020/8/3
     */
    @Override
    public Integer editIssueCustomRelation(SecurityDTO securityDTO, IssueTemplate issueTemplate) {
        return issueTemplateMapper.updateByPrimaryKeySelective(issueTemplate);
    }

    @Override
    public IssueTemplate getTemplateByProjectAndType(Long systemId, Byte issueType) {
        return issueTemplateMapper.getTemplateByProjectAndType(systemId, issueType);
    }
}
