package com.yusys.agile.customfield.service;


import com.yusys.agile.AgileApplication;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class CustomFieldPoolServiceTest {

    @Autowired
    private CustomFieldPoolService customFieldPoolService;

    /**
     * 初始化数据
     *
     * @return {@link CustomFieldDTO}
     */
    public CustomFieldDTO initData() {
        CustomFieldDTO customFieldDTO = new CustomFieldDTO();
        customFieldDTO.setFieldId(30L);
        customFieldDTO.setFieldName("测试字段");
        customFieldDTO.setFieldContent("{\"name\":\"测试字段\",\"type\":\"input\",\"comment\":\"这是是一条测试数据\",\"required\":false,\"optionList\":[{\"key\":\"\",\"value\":\"\"}]}");
        //字段类型 0单选 1多选 2数字 3日期 4文本 5成员
        customFieldDTO.setFieldType(1);
        customFieldDTO.setComment("这是是一条测试数据");
        customFieldDTO.setSystemId(847157263800733696L);
        return customFieldDTO;
    }

    /**
     * 添加自定义字段
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void addCustomField() {
        CustomFieldDTO customFieldDTO = initData();
        customFieldDTO.setFieldId(null);
        String s = customFieldPoolService.addCustomField(customFieldDTO);
        Assert.assertNotNull(s, true);
    }

    /**
     * 编辑自定义字段
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void editCustomField() {
        CustomFieldDTO customFieldDTO = initData();
        String s = customFieldPoolService.editCustomField(customFieldDTO);
        Assert.assertNotNull(s);
    }

    @Test
    public void deleteCustomField() {
    }

    @Test
    public void listAllCustomFields() {
    }

    @Test
    public void getCustomField() {
    }

    @Test
    public void listAllCustomFieldsByTenantCode() {
    }
}