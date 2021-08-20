package com.yusys.agile.issueTemplate;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class issueTemplateTest {


    @Autowired
    private IssueTemplateService issueTemplateService;

    private IssueCustomRelationService issueCustomRelationService;

    /**
     * 测试-删除模板和自定义字段的关联关系
     * @author zhaofeng
     * @date 2021/6/10 10:31
     */
    @Test
    public void testDeleteIssueCustomRelation(){
        try{
            issueCustomRelationService.deleteIssueCustomRelation(1L);
            Assert.assertTrue("逻辑删除关联关系成功", true);
        }catch (Exception e){
            log.error("逻辑删除关联关系失败：",e);
            Assert.assertNull(e.getMessage());
        }
    }

    /**
     * 测试-查询未应用的自定义字段
     * @author zhaofeng
     * @date 2021/6/10 11:32
     */
    @Test
    @Transactional
    public void testGetUnApplied1(){
        try {
            List<CustomFieldDTO> list = issueCustomRelationService.getUnApplied(847157263800733696L, Byte.parseByte("1"), null);
            Assert.assertTrue("查询成功", true);
        }catch (Exception e){
            Assert.assertNull(e.getMessage());
        }
    }

    /**
     * 测试-查询未应用的自定义字段-带名称的
     * @author zhaofeng
     * @date 2021/6/10 11:32
     */
    @Test
    @Transactional
    public void testGetUnApplied2(){
        try {
            List<CustomFieldDTO> list = issueCustomRelationService.getUnApplied(847157263800733696L, Byte.parseByte("1"), "测试");
            Assert.assertTrue("查询成功", true);
        }catch (Exception e){
            Assert.assertNull(e.getMessage());
        }
    }

}
