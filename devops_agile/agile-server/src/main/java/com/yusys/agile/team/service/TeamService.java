package com.yusys.agile.team.service;

import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.entity.SsoUser;

import java.util.List;

/**

 * @Date 2020/4/17
 */
public interface TeamService {

    /**

     * @Date 2020/4/17 17:38
     * @Description 获取项目下所有团队
      * @param projectId
     * @Return java.util.List<com.yusys.agile.team.dto.TeamDTO>
     */
    List<TeamDTO> getListTeam(Long projectId);

    /**

     * @Date 2020/4/17 17:39
     * @Description 获取项目中的人员信息
      * @param subjectId
     * @param userRelateType
     * @Return java.util.List<com.yusys.portal.model.facade.entity.SsoUser>
     */
    List<SsoUser> listMemberUsers(Long subjectId, Integer userRelateType);

    /**

     * @Date 2020/4/17 17:39
     * @Description 增加成员迭代时长信息
      * @param userSprintHourDTOS
     * @param userSprintHours
     * @param iFacadeUserApi
     * @Return void
     */
    void getUserSprintHour(List<UserSprintHourDTO> userSprintHourDTOS, List<UserSprintHour> userSprintHours, IFacadeUserApi iFacadeUserApi);
}
