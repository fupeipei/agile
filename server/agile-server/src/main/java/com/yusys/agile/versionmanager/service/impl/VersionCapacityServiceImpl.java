package com.yusys.agile.versionmanager.service.impl;

import com.yusys.agile.versionmanager.dao.VersionCapacityMapper;
import com.yusys.agile.versionmanager.service.VersionCapacityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName VersionCapacityServiceImpl
 * @Description TODO
 * @Date 2020/8/19 13:34
 * @Version 1.0
 */
@Service
public class VersionCapacityServiceImpl implements VersionCapacityService {
    @Resource
    private VersionCapacityMapper versionCapacityMapper;

}
