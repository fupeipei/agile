package com.yusys.agile.teamv3.service.impl;

import com.yusys.agile.team.dto.TeamSystemDTO;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.service.TeamSystemv3Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
@Service
public class TeamSystemv3ServiceImpl implements TeamSystemv3Service {
    @Resource
    private STeamSystemMapper sTeamSystemMapper;

    @Override
    public List<TeamSystemDTO> selectByTeamIds(List<Long> teamIds) {
        List<TeamSystemDTO> list = sTeamSystemMapper.selectByTeamIds(teamIds);
        return list;
    }
}
