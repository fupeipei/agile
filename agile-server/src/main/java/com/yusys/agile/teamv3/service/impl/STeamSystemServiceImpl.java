package com.yusys.agile.teamv3.service.impl;

import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.STeamSystem;
import com.yusys.agile.teamv3.domain.STeamSystemExample;
import com.yusys.agile.teamv3.service.STeamSystemService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author shenfeng
 * @description
 * @date 2021/6/1
 */
@Service
public class STeamSystemServiceImpl implements STeamSystemService {
    @Resource
    private STeamSystemMapper teamSystemMapper;

    @Override
    public List<STeamSystem> listTeamBySystem(Long systemId) {
        STeamSystemExample sTeamSystemExample = new STeamSystemExample();
        sTeamSystemExample.createCriteria().andSystemIdEqualTo(systemId);
        return teamSystemMapper.selectByExample(sTeamSystemExample);
    }
}
