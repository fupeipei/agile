package com.yusys.agile.sysextendfieldservice;

import com.yusys.agile.sysextendfield.domain.SysExtendField;
import com.yusys.agile.sysextendfield.domain.SysField;
import com.yusys.agile.sysextendfield.service.SysExtendFieldService;
import com.yusys.agile.sysextendfield.service.SysFieldService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysExtendFieldServiceTest {
    @Autowired
    SysExtendFieldService sysExtendFieldService;

    @Test
    public void getAllSysFieldTest(){
        Byte issueType = 3;
        List<SysExtendField>  sysFields = sysExtendFieldService.getAllSysExtendField(issueType);
        Assert.assertTrue(true);
    }
    @Test
    public void getFieldTest(){
        Byte issueType = 3;
        List<SysExtendField>  sysFields = sysExtendFieldService.getField(issueType);
        Assert.assertTrue(true);
    }
}
