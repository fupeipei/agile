package com.yusys.agile.fault.service;

import com.yusys.agile.fault.domain.FaultLevel;
import com.yusys.agile.fault.domain.FaultType;
import com.yusys.agile.fault.dto.FaultStatusDTO;
import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.issue.dto.IssueDTO;

import java.util.List;
import java.util.Map;

/**
 * 缺陷service
 *
 * @create 2020-04-10 16:27
 */
public interface FaultService {

    /**
     * 功能描述: 新增缺陷
     *
     * @param issueDTO 缺陷dto
     * @return void
     * @date 2020/4/11
     */
    void addFault(IssueDTO issueDTO);

    /**
     * 功能描述: 删除缺陷
     *
     * @param issueId 缺陷id
     * @return void
     * @date 2020/4/11
     */
    void deleteFault(Long issueId);


    /**
     * 功能描述: 根据缺陷id查询详情
     *
     * @param issueId
     * @return com.yusys.agile.issue.dto.IssueDTO
     * @date 2020/4/15
     */
    IssueDTO getFault(Long issueId);

    /**
     * 功能描述:
     *
     * @param faultDTO
     * @return void
     * @date 2020/4/13
     */
    void updateFault(IssueDTO faultDTO) throws Exception;

    /**
     * 功能描述: 查询所有缺陷级别
     *
     * @param
     * @return java.util.List<com.yusys.agile.fault.domain.FaultLevel>
     * @date 2020/4/11
     */
    List<FaultLevel> listAllFaultLevel();

    /**
     * 功能描述: 查询所有缺陷类型
     *
     * @param
     * @return java.util.List<com.yusys.agile.fault.domain.FaultType>
     * @date 2020/4/11
     */
    List<FaultType> listAllFaultType();

    /**
     * 功能描述: 查询项目下所有的缺陷提出人
     *
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/14
     */
    List<UserDTO> listAllCreateUsers(Long projectId);

    /**
     * 功能描述: 查询项目下所有的缺陷修复人
     *
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/14
     */
    List<UserDTO> listAllFixedUsers(Long projectId);

    /**
     * 功能描述: 查询项目下所有的缺陷验收人
     *
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/14
     */
    List<UserDTO> listAllTestUsers(Long projectId);

    /**
     * 功能描述: 列表分页查询缺陷
     *
     * @param idOrName   缺陷id或name
     * @param faultLevel 缺陷级别
     * @param faultType  缺陷类型
     * @param stageId    状态id
     * @param sprintId   迭代id
     * @param createUid  创建人id
     * @param fixedUid   修复人id
     * @param testUid    验证人id
     * @param pageNum    每页数量
     * @param pageSize   页数
     * @return IssueDTO
     * @date 2020/4/14
     */
    List<IssueDTO> listFaults(String idOrName, Long faultLevel, Long faultType, Long stageId, Long sprintId, Long createUid, String createDate, Long fixedUid, Long testUid, Long projectId, Integer pageNum, Integer pageSize) throws Exception;

    /**
     * 功能描述: 列表展示未关联迭代的缺陷
     *
     * @param filter
     * @param issueType
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @return java.util.List<IssueDTO>
     * @date 2020/4/14
     */
    List<IssueDTO> listFaultsOrStorysNotLinkSprint(String filter, Byte issueType, Long projectId, Integer pageNum, Integer pageSize);

    /**
     * 功能描述: 缺陷所有状态
     *
     * @param
     * @return List
     * @date 2020/4/15
     */
    List<FaultStatusDTO> listAllStatus();


    /**
     * 功能描述: 拖动缺陷卡片
     *
     * @param issueDTO
     * @return void
     * @date 2020/4/26
     */
    void dragFault(IssueDTO issueDTO) throws Exception;

    /**
     * 功能描述: 根据key获取对应的url地址
     *
     * @param key
     * @return java.lang.String

     * @date 2020/4/28
     */
    //String getPostUrl(String key);

    /**
     * 功能描述: 获取项目下所有的用户数据
     * <p>
     * 不考虑本地缓存，如果对用户信息进行了修改，本地缓存查询出的数据会出错。
     * 考虑性能，避免循环调fiegn服务，由于绝大多数用户都是在项目中采用一次性查出所有用户然后进行转换
     * 目前确认人员都是项目中 如果发现用户数据很多不是来自项目，就采用查询三次的方式，把create、fix、test用户信息拉过来
     *
     * @param projectId
     * @return java.util.Map<java.lang.Long, com.yusys.portal.model.facade.entity.SsoUser>
     * @date 2020/4/14
     */
    Map<Long, String> getUserMapByProjectId(Long projectId);

    /**
     * 功能描述: 根据用户集合获取所有的用户数据
     *
     * @param UserIdList
     * @return java.util.Map<java.lang.Long, com.yusys.portal.model.facade.entity.SsoUser>
     * @date 2020/10/21
     */
    Map<Long, String> getUserMapByUserIdList(List<Long> UserIdList);

    /**
     * 功能描述: 缺陷设置阻塞状态
     *
     * @param issueDTO
     * @return void
     * @date 2021/2/1
     */
    void editBlockState(IssueDTO issueDTO);

    /**
     * @param projectId
     * @param sprintId
     * @return
     * @description 查询未绑定故事且状态未关闭的迭代下缺陷
     * @date 2020/08/31
     */
    List<IssueDTO> getUnBindStoryAndUnFinishedFaultList(Long projectId, Long sprintId);

    /**
     * @param projectId
     * @return
     * @description 统计缺陷待修复个数，修复中个数，已修复个数，已关闭个数，总数，修复率
     * @date 2020/09/07
     */
    Map<String, Object> statisticFaults(Long projectId);
}
