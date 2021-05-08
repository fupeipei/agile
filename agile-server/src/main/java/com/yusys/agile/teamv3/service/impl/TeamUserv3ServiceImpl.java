package com.yusys.agile.teamv3.service.impl;

import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamUserMapper;
import com.yusys.agile.teamv3.service.TeamUserv3Service;
import com.yusys.agile.teamv3.service.Teamv3Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
@Service
public class TeamUserv3ServiceImpl implements TeamUserv3Service {
    @Resource
    private STeamUserMapper sTeamUserMapper;

}
