package com.yusys.agile.issue.service;

import com.yusys.agile.commit.dto.CommitDTO;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.DemandPlanDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueListDTO;
import com.yusys.agile.issue.dto.PanoramasEpicDTO;
import com.yusys.agile.servicemanager.dto.CustomizePageInfoDTO;
import com.yusys.agile.servicemanager.dto.ServiceManageIssueDTO;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IssueService {


    /**
     * 功能描述 列表查询接口 1:epic 2:feature 3:story 4:task 5:fault
     *
     * @param map
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     * @date 2020/4/16
     */

    PageInfo getIssueList(Map<String, Object> map);

    /**
     * @param issueId
     * @Date: 18:22
     * @Description: 建立关联关系
     * @Param: * @param parentId
     * @Return: void
     */
    void createRelation(Long parentId, Long issueId);

    /**
     * @param issueId
     * @Date: 18:22
     * @Description: 删除关联关系
     * @Param: * @param parentId
     * @Return: void
     */
    void deleteRelation(Long parentId, Long issueId);

    /**
     * 功能描述  查询当前Issue
     *
     * @param issueId
     * @param issueQuery 1:不查询child，2：查询child
     * @param noLogin    免登录标识，"true"标识免登录调用，other非免登录调用
     * @return com.yusys.agile.requirement.SysExtendFieldDetailDTO;
     * @date 2020/4/21
     */
    IssueListDTO getIssue(Long issueId, Byte issueQuery, String noLogin);

    /**
     * 功能描述  根据issueId查询当前Issue
     *
     * @param issueId
     * @param projectId
     * @return Map
     * @date 2020/10/15
     */
    Map getIssueByIssueId(Long issueId, Long projectId) throws Exception;

    /**
     * 功能描述  添加、取消Issue的收藏
     *
     * @param issueId
     * @param isCollect 0：非收藏 1：收藏
     * @return void
     * @date 2020/4/22
     */
    void isCollect(Long issueId, Byte isCollect, SecurityDTO securityDTO);

    /**
     * @param listIssueId
     * @Date: 9:08
     * @Description: 批量建立关联关系
     * @Param: * @param parentId
     * @Return: void
     */
    void createBatchRelation(Long parentId, List<Long> listIssueId, Long userId);

    /**
     * @Date: 10:52
     * @Description: 批量建立工作项
     * @Param: * @param listIssue
     * @Return: void
     */
    void createBatchIssue(String listIssue);

    /**
     * 功能描述
     *
     * @param rootIds
     * @return java.util.List<com.yusys.agile.requirement.SysExtendFieldDetailDTO ;>
     * @date 2020/4/30
     */
    List<IssueListDTO> issueListByIds(String rootIds, Long projectId) throws Exception;

    Map getOptionList(String filedCodeValue, String filedCode, Map<String, HashMap<String, String>> hashMapMap);

    /**
     * @param handler
     * @Date: 2021/2/26 16:28
     * @Description: 更新处理人
     * @Param: * @param issueId
     * @Return: void
     */
    void updateHandler(Long issueId, Long handler);

    /**
     * @param issueId
     * @param pageNum  分页页数
     * @param pageSize 分页条数
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * :
     * @Date:2021/2/1 11:05
     * @Description:根据issueId，查询操作历史分页数据
     */
    ControllerResponse recordHistories(Long issueId, Integer pageNum, Integer pageSize, SecurityDTO securityDTO);

    ControllerResponse getRecordRichText(Long recordId);

    /**
     * @param issueId
     * @param issueType
     * @Date: 2021/2/3 10:20
     * @Description: 详情显示工作项关联关系列表
     * @Param: projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    //List<IssueDTO> listRelation(Long issueId, Byte issueType, Long projectId);
    List<IssueDTO> listRelation(Long issueId, Byte issueType);

    /**
     * @param issueType
     * @Date: 2021/2/10 10:20
     * @Description: 查询模板父工作项列表
     * @Param: projectId
     * @Return: java.util.List<java.lang.String>
     */
    List<String> getTemplateParentIssueList(Long projectId, Byte issueType);


    /**
     * 功能描述 高级搜索列表查询接口 1:epic 2:feature 3:story 4:task 5:fault
     * 根据查询条件，返回List<Issue>对象
     *
     * @param map
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     * @date 2020/06/15
     */

    List<Issue> queryIssueList(Map<String, Object> map) throws Exception;

    /**
     * @param projectId
     * @Date 2021/2/22
     * @Description 项目概览页面统计各个阶段的需求个数
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueStageIdCountDTO>
     */
    PageInfo countIssueByStageId(Long projectId, Integer pageNum, Integer pageSize);

    /**
     * 需求规划获取列表数据
     *
     * @param title  标题
     * @param stages 阶段ID
     * @return DemandPlanDTO
     */
    DemandPlanDTO getDemandPlans(String title, String stages, SecurityDTO securityDTO);

    /**
     * 需求规划中卡片拖拽。可修改迭代编码，父工作项编码
     *
     * @param issueId  工作项ID
     * @param sprintId 迭代编号
     * @param parentId 父工作项编码
     * @return
     */
    void dragDemand(Long issueId, Long sprintId, Long parentId, Long projectId);

    /**
     * @param commitDTO
     * @return
     * @description 查询项目下成员任务总数
     */
    long getProjectMemberTaskTotal(CommitDTO commitDTO);

    /**
     * @param commitDTO
     * @return
     * @description 查询项目下成员任务列表
     */
    List<Issue> getProjectMemberTaskList(CommitDTO commitDTO);

    /**
     * @param commitDTO
     * @param startIndex 起始下标
     * @param pageSize   每页记录数
     * @return
     * @description 查询项目下人员任务列表
     */
    List<Issue> getProjectMemberTaskList(CommitDTO commitDTO, long startIndex, long pageSize);

    /**
     * @param projectId
     * @param sprintId
     * @param queryStr
     * @param pageNumber
     * @param pageSize
     * @return
     * @description 查询迭代下提交任务列表
     */
    PageInfo<List<Issue>> getSprintRelatedCommitTaskList(Long projectId, Long sprintId, String queryStr, Integer pageNumber, Integer pageSize);

    List<Long> selectIssueIdByProjectId(Long projectId, String title);

    Issue selectIssueInfo(Long issueId, int type);

    /**
     * 租户下的缓存数据
     * 功能描述
     *
     * @param noLogin   免登录标识，"true"标识免登录调用，other非免登录调用
     * @return java.util.Map
     */
    Map issueMap( String noLogin);

    IssueListDTO arrangeIssueListDTO(Issue issue, IssueListDTO issueListDTO, Map<String, Map> mapMap);


    void sortIssueDTO(Byte queryType, String rootIds, IssueListDTO issueListDTO, Map<String, Map> mapMap);

    /**
     * @param queryType
     * @param rootIds
     * @param issueListDTO
     * @param mapMap
     * @Date 2021/2/16
     * @Description 功能描述  版本管理中递归查找Issue的children
     * @Return void
     */
    void sortVersionIssueDTO(Byte queryType, String rootIds, IssueListDTO issueListDTO, Map<String, Map> mapMap);

    /**
     * 功能描述 根据bizNum获取需求信息
     *
     * @param bizNums
     * @return java.util.List<com.yusys.agile.issue.domain.Issue>
     * @date 2021/3/18
     */
    List<Issue> getIssueByBizNums(List<String> bizNums, Byte issueType);

    /**
     * 功能描述 获取基础和扩展信息
     *
     * @param bizBacklogId
     * @return com.yusys.agile.issue.dto.IssueDTO
     * @date 2021/3/23
     */
    IssueDTO selectIssueAndExtends(Long bizBacklogId);

    List<IssueDTO> selectIssueAndExtendsByIssueIds(List<Long> duplicatedBindingReqIds);

    /**
     * 功能描述
     *
     * @param issueIdList
     * @param getChildren 是否获取子工作项
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     * @date 2021/3/19
     */
    List<IssueDTO> getIssueListByIssueIds(List<Long> issueIdList, boolean getChildren);

    /**
     * 功能描述 查询issue信息
     *
     * @param issueId
     * @return com.yusys.agile.issue.domain.Issue
     * @date 2021/2/5
     */
    Issue selectIssueByIssueId(Long issueId);

    /**
     * @param parentId
     * @return
     * @description 查询子工作项待上线状态
     * @date 2020/10/09
     */
    Map<Boolean, List<Long>> getChildIssueWaitOnlineState(Long parentId);

    /**
     * @param issueId
     * @param issueType
     * @return
     * @description 更新工作项上线状态
     * @date 2020/10/09
     */
    void updateIssueLaunchState(Long issueId, Byte issueType);

    /**
     * @Date 2020/10/21
     * @Description 根据登入用户获取代办事项
     * @Return PageInfo
     */
    PageInfo getIssueCommissionByHandler(Integer pageNum, Integer pageSize);

    /**
     * 功能描述 显示全景图
     *
     * @param issueId
     * @param noLogin
     * @return com.yusys.agile.issue.dto.PanoramasEpicDTO
     * @date 2020/10/28
     */
    PanoramasEpicDTO getIssuePanoramas(String issueId, String planDeployDate, String noLogin) throws Exception;

    /**
     * @param serviceManageIssueDTO
     * @return
     * @description 服务治理平台查询接口
     * @date 2020/10/26
     */
    CustomizePageInfoDTO queryIssueList(ServiceManageIssueDTO serviceManageIssueDTO);


    /**
     * 功能描述   翻译扩展字段对应的名称
     *
     * @param fieldId
     * @param value
     * @return java.lang.String
     * @date 2021/2/16
     */


    String translateExtendFieldMap(String fieldId, String value);


    Map queryIssueEpic(Long issueId, Byte issueType);

    /**
     * 功能描述  根据局方需求编号获取分期【planDeployDate】
     *
     * @param formalReqCode
     * @return java.util.List<java.lang.String>
     * @date 2021/2/18
     */
    List<String> queryPlanDeployDate(String formalReqCode);

    /**
     * 功能描述 根据局方需求编号获取客户需求编号
     *
     * @param formalReqCode
     * @return java.util.List<java.lang.String>
     * @date 2021/2/7
     */
    List<String> queryBizNumList(String formalReqCode);

    /**
     * 功能描述 根据IssueIdList查询出未取消的需求
     *
     * @param issueIdList
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/20
     */
    List<Long> getNotCanceledAndOnlineIssueByIssueIdList(List<Long> issueIdList);

    /**
     * @param issueId
     * @param issueType
     * @param actualOnlineTime
     * @description 更新工作项已上线状态带时间(临时方法)
     * @date 2021/2/20
     */
    void updateIssueLaunchStateWithDate(Long issueId, Byte issueType, String actualOnlineTime);

    /**
     * 功能描述 查询未上线的Epic
     *
     * @param
     * @return java.util.List<java.lang.Long>
     * @date 2020/12/8
     */

    List<Long> getNotOnlineEpic();

    /**
     * 功能描述 获取没有需求排期或需求排期为空或需求排期为null的Epic信息
     *
     * @param
     * @return java.util.List<java.lang.Long>
     * @date 2020/12/8
     */
    List<Long> noReqSchedulingEpicIdList();

    /**
     * 功能描述  根据epicId查询所有故事上的功能测试负责人
     *
     * @param epicId
     * @return void
     * @date 2020/12/11
     */
    List<String> getAllTaskFunTester(Long epicId);

    void noDeployChangeStage();

    /**
     * 功能描述 根据工作项ID列表获取有效状态的工作项列表
     *
     * @param epicIdList
     * @return java.util.List<com.yusys.agile.issue.domain.Issue>
     * @date 2020/12/30
     */
    List<Issue> getValidIssue(List<Long> epicIdList);

    /**
     * 功能描述 根据工作项Id查询其有效的子工作项Id
     *
     * @param parentId
     * @return java.util.List<com.yusys.agile.issue.domain.Issue>
     * @date 2020/12/30
     */
    List<Long> getIssueIds(List<Long> parentId);


    /**
     * 根据看板Id和issue类型获取节点下所有集合
     *
     * @param kanbanId
     * @param issueType
     * @return
     * @throws Exception
     */
    List<IssueDTO> getIssueTrees(Long kanbanId,Byte issueType) throws Exception;

    /**
     *功能描述 校验是否存在子工作项
     * @author shenfeng
     * @date 2021/6/15
      * @param issueId
     * @return boolean
     */
    boolean checkHasChildren(Long issueId);



    /**
     * 精益看板拖拽卡片
     *
     * @param issueId
     * @param stageId
     * @param laneId
     * @return
     */
    IssueDTO dragIssueCard(Long issueId,Long stageId, Long laneId) throws ExecutionException;

    void orgIssueExtendFields(Long epicId, Map<String, Object> map);

    boolean checkIssueState(Long issueId,Long fromStageId, Long fromLaneId);


    /**
     * 汇总任务状态
     *
     * @param issueId   任务Id
     * @param kanbanId   看板Id(可以通过teamId去获取)
     */
    void updateTaskParentStatus(Long issueId,Long kanbanId);
}
