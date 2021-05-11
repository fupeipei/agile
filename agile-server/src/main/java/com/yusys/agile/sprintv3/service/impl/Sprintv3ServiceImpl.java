package com.yusys.agile.sprintv3.service.impl;

import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:48
 */
@Service
public class Sprintv3ServiceImpl implements Sprintv3Service {

    @Resource
    private SSprintMapper ssprintMapper;
    @Resource
    private SSprintUserHourMapper ssprintUserHourMapper;


}
