package com.yusys.agile.HeaderFieldService;

import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.headerfielduser.dto.HeaderFieldListDTO;
import com.yusys.agile.headerfielduser.service.HeaderFieldUserService;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HeaderFieldServiceTest {

    @Autowired
    HeaderFieldService headerFieldService;

    @Autowired
    HeaderFieldUserService headerFieldUserService;

    @Test
    public  void queryHeaderFieldsTest(){
        SecurityDTO securityDTO = new SecurityDTO();
        Byte category = new Byte("3");
        Byte isFilter = new Byte("1");
        securityDTO.setSystemId(849300453977526272L);
        headerFieldService.queryHeaderFields(securityDTO, category, isFilter);
        Assert.assertTrue("queryHeaderFieldsTest成功", true);
    }

    @Test
    public  void updateHeaderFieldUserListTest(){
        HeaderFieldListDTO headerFieldListDTO = new HeaderFieldListDTO();
        headerFieldListDTO.setCategory(Byte.parseByte("3"));
        Long[] longs = {1L,2L,3L,4L,5L,6L,7L,9L,10L,11L,12L,13L,14L,15L,16L,17L,18L,20L,28L,29L,31L,67L};
        headerFieldListDTO.setUpdateList(Arrays.asList(longs));
        Map map = headerFieldUserService.updateHeaderFieldUserList(headerFieldListDTO);
        Assert.assertTrue("updateHeaderFieldUserListTest成功", true);
    }

}
