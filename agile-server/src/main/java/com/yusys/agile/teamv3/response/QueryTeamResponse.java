package com.yusys.agile.teamv3.response;

import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamUser;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询团队响应
 *
 * @date 2021/04/29
 */
@Data
public class QueryTeamResponse {
    private STeam sTeam;
    private List<SsoSystem> systems;
    private List<TeamUserDTO> teamUsers;
    private List<TeamUserDTO> teamPoS;
    private List<TeamUserDTO> teamSmS;
}
