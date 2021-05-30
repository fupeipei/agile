package com.yusys.agile.module.service.impl;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.module.service.ModuleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class ModuleServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(ModuleServiceImplTest.class);
    @Autowired
    private ModuleService moduleService;

    @Test
    public void listModuleBySystemIds() {
        List<Long> systemIds = new ArrayList<>();
        systemIds.add(711877419501215744L);
        moduleService.listModuleBySystemIds(systemIds);
        Assert.assertTrue("listModuleBySystemIds成功", true);
    }
}