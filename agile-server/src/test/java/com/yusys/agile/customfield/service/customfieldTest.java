package com.yusys.agile.customfield.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.customfield.dao.SCustomFieldPoolMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class customfieldTest {


    @Autowired
    private SCustomFieldPoolMapper customFieldPoolMapper;


}
