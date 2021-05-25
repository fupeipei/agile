package com.yusys.agile.HeaderFieldService;

import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeaderFieldServiceTest {

    @Autowired
    HeaderFieldService headerFieldService;

    @Test
    public  void queryHeaderFieldsTest(){
        SecurityDTO securityDTO = new SecurityDTO();
        Byte category = new Byte("1");
        Byte isFilter = new Byte("1");
        securityDTO.setUserId(815897630505963520L);
        Map map = headerFieldService.queryHeaderFields(securityDTO, category, isFilter);
    }
}
