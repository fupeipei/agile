package com.yusys.agile.issueTemplate.server.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yusys.agile.issueTemplate.domain.IssueCustomRelationV3;
import com.yusys.agile.issueTemplate.domain.IssueTemplateV3;
import com.yusys.agile.issueTemplate.dao.IssueCustomRelationV3Mapper;
import com.yusys.agile.issueTemplate.dao.IssueTemplateV3Mapper;
import com.yusys.agile.issueTemplate.server.IssueTemplateV3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueTemplateV3ServiceImpl implements IssueTemplateV3Service {

    @Resource
    private IssueTemplateV3Mapper templateMapper;
    @Resource
    private IssueCustomRelationV3Mapper customRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long issueTemplateId) {
        return templateMapper.deleteByPrimaryKey(issueTemplateId);
    }

    @Override
    public int insert(IssueTemplateV3 record) {
        return templateMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(IssueTemplateV3 record) {
        return templateMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(IssueTemplateV3 record) {
        return templateMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(IssueTemplateV3 record) {
        return templateMapper.insertSelective(record);
    }

    @Override
    public IssueTemplateV3 selectByPrimaryKey(Long issueTemplateId) {
        return templateMapper.selectByPrimaryKey(issueTemplateId);
    }

    @Override
    public int updateByPrimaryKeySelective(IssueTemplateV3 record) {
        return templateMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IssueTemplateV3 record) {
        return templateMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<IssueTemplateV3> list) {
        return templateMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<IssueTemplateV3> list) {
        return templateMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<IssueTemplateV3> list) {
        return templateMapper.batchInsert(list);
    }

    /**
     * 查询需求模板
     *
     * @param systemId  系统标识
     * @param issueType 问题类型
     * @return {@link IssueTemplateV3}
     * @author 张宇
     */
    @Override
    public IssueTemplateV3 queryIssueTemplate(Long systemId, int issueType) {
        IssueTemplateV3 issueTemplateV3;
        issueTemplateV3 = templateMapper.queryIssueTemplateBySystemIdAndIssueType(systemId, issueType);
        if (ObjectUtil.isEmpty(issueTemplateV3)) {
            creatIssueTemplate(systemId, issueType);
            issueTemplateV3 = templateMapper.queryIssueTemplateBySystemIdAndIssueType(systemId, issueType);
            return issueTemplateV3;
        }
        issueTemplateV3.setIssueCustomRelations(customRelationMapper.queryTemplateCustomField(systemId, issueType));
        return issueTemplateV3;
    }

    /**
     * 新建需求模板
     *
     * @param systemId  系统标识
     * @param issueType 问题类型
     * @author 张宇
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void creatIssueTemplate(Long systemId, int issueType) {
        String[] strings = {"<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt;</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>",
                "<p><span style=\"font-weight: bold;\">[缺陷描述]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[重现步骤]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[期望结果]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[实际结果]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[原因定位]：</span></p><p><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-weight: bold;\">[修复建议]：</span></p>"};
        for (int i = 0; i < 5; i++) {
            IssueTemplateV3 issueTemplateV3 = new IssueTemplateV3();
            issueTemplateV3.setSystemId(systemId);
            issueTemplateV3.setApply(1);
            issueTemplateV3.setTemplateName("默认模板");
            // 类型 1:epic 2:feature 3:story 4:task 5:fault
            issueTemplateV3.setIssueType(i + 1);
            issueTemplateV3.setDescription(strings[i]);
            templateMapper.insert(issueTemplateV3);
        }
    }

    /**
     * 编辑需求模板
     *
     * @param issueTemplateV3 需求模板
     * @author 张宇
     */
    @Override
    public void editIssueTemplate(IssueTemplateV3 issueTemplateV3) {
        templateMapper.updateByPrimaryKey(issueTemplateV3);
        //解除之前所有的模板和自定义字段的绑定,不清楚自定义字段在issue层输入的内容,只解除关联关系
        customRelationMapper.removeBinding(issueTemplateV3.getSystemId(), issueTemplateV3.getIssueType());
        //重新绑定模板和自定义自定义字段
        customRelationMapper.batchInsert(issueTemplateV3.getIssueCustomRelations());
    }


}
