package com.yusys.agile.issueTemplate.server;


import com.yusys.agile.issueTemplate.domain.IssueTemplateV3;

import java.util.List;

public interface IssueTemplateV3Service {


    int deleteByPrimaryKey(Long issueTemplateId);

    int insert(IssueTemplateV3 record);

    int insertOrUpdate(IssueTemplateV3 record);

    int insertOrUpdateSelective(IssueTemplateV3 record);

    int insertSelective(IssueTemplateV3 record);

    IssueTemplateV3 selectByPrimaryKey(Long issueTemplateId);

    int updateByPrimaryKeySelective(IssueTemplateV3 record);

    int updateByPrimaryKey(IssueTemplateV3 record);

    int updateBatch(List<IssueTemplateV3> list);

    int updateBatchSelective(List<IssueTemplateV3> list);

    int batchInsert(List<IssueTemplateV3> list);

    /**
     * 查询需求模板
     *
     * @param systemId  系统标识
     * @param issueType 问题类型
     * @return {@link IssueTemplateV3}
     * @author 张宇
     */
    IssueTemplateV3 queryIssueTemplate(Long systemId, int issueType);

    /**
     * 新建需求模板
     *
     * @param systemId  系统标识
     * @param issueType 需求类型
     * @author 张宇
     */
    void creatIssueTemplate(Long systemId, int issueType);

    /**
     * 编辑需求模板
     *
     * @param issueTemplateV3 需求模板v3
     * @author 张宇
     */
    void editIssueTemplate(IssueTemplateV3 issueTemplateV3);

}
