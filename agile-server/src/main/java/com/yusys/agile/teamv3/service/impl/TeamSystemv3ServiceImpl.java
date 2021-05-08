package com.yusys.agile.teamv3.service.impl;

import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.service.TeamSystemv3Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
@Service
public class TeamSystemv3ServiceImpl implements TeamSystemv3Service {
    @Resource
    private STeamSystemMapper sTeamSystemMapper;

}
