package com.yusys.agile.sprintv3.rest;

import com.yusys.agile.sprintv3.service.Sprintv3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:50
 */
@RestController
@RequestMapping("/v3/sprint")
public class Sprintv3Controller {

    @Autowired
    private Sprintv3Service sprintv3Service;
    
}
