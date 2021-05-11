package com.yusys.agile.teamv3.service.impl;

import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.dao.STeamUserMapper;
import com.yusys.agile.teamv3.service.TeamUserv3Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
@Service
public class TeamUserv3ServiceImpl implements TeamUserv3Service {
    @Resource
    private STeamUserMapper sTeamUserMapper;

    @Override
    public List<TeamUserDTO> selectByTeamIds(List<Long> teamIds) {
        List<TeamUserDTO> list = sTeamUserMapper.selectByTeamIds(teamIds);
        return list;
    }
}
