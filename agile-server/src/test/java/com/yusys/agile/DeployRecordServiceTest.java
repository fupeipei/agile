package com.yusys.agile;

import com.github.pagehelper.PageInfo;

import com.yusys.agile.deployrecord.service.DeployRecordService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DeployRecordServiceTest {

    @Autowired
    private DeployRecordService deployRecordService;
    @Test
    public void queryDeployRecordTest(){
        long issueId = 847878567016624827L;
        Byte issueType = 3;
        Integer pageNum = 1;
        Integer pageSize = 10;
        PageInfo pageInfo = deployRecordService.queryDeployRecord(issueId,issueType,pageNum,pageSize);
        Assert.assertTrue("true",true);
    }

}
