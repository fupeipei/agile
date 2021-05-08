package com.yusys.agile.teamv3.service.impl;

import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.service.TeamSystemv3Service;
import com.yusys.agile.teamv3.service.TeamUserv3Service;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
@Service
public class Teamv3ServiceImpl implements Teamv3Service {
    @Resource
    private STeamMapper sTeamMapper;

    @Autowired
    private TeamUserv3Service teamUserv3Service;
    @Autowired
    private TeamSystemv3Service teamSystemv3Service;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;

    @Override
    public List<TeamListDTO> listTeam(TeamQueryDTO dto, SecurityDTO security) {
        return null;
    }

    @Override
    public List<TeamListDTO> list() {
        return null;
    }
}
