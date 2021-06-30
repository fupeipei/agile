package com.yusys.agile.utils;


import com.yusys.agile.businesskanban.dao.BusinessKanbanMapper;
import com.yusys.agile.businesskanban.dao.BusinessMapper;
import com.yusys.agile.businesskanban.domain.Business;
import com.yusys.agile.businesskanban.domain.BusinessExample;
import com.yusys.agile.businesskanban.domain.BusinessKanban;
import com.yusys.agile.businesskanban.enums.BusinessState;
import com.yusys.agile.businesskanban.enums.BusinessType;
import com.yusys.agile.constant.EmailOperationTypeEnum;
import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.fault.enums.FaultStatusEnum;
import com.yusys.agile.issue.dao.IssueHistoryRecordMapper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.domain.IssueHistoryRecordExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskStatusEnum;
import com.yusys.agile.noticesettings.MailSwitchEnum;
import com.yusys.agile.noticesettings.dao.MailSwitchMapper;
import com.yusys.agile.noticesettings.domain.MailSwitch;
import com.yusys.agile.noticesettings.domain.MailSwitchExample;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintUserHourExample;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoProject;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.model.ms.dto.MailSendDTO;
import com.yusys.portal.model.ms.enums.MailTemplateTypeEnum;
import com.yusys.portal.model.ms.enums.MailTypeEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MailSendUtil {
    @Resource
    private SSprintUserHourMapper sprintHourMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private IFacadeProjectApi iFacadeProjectApi;
    @Resource
    private MailSwitchMapper mailSwitchMapper;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private IssueHistoryRecordMapper issueHistoryRecordMapper;
    @Resource
    private KanbanStageInstanceMapper stageInstanceMapper;
    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private BusinessKanbanMapper businessKanbanMapper;

    private static final Logger log = LoggerFactory.getLogger(MailSendUtil.class);
    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Value("${YuDO.url}")
    private String YuDOWebIp;
    @Value("${project.location}")
    private String projectLocation;

    /**
     * @param issue
     * @param operationType 0 新增 1修改 2删除 3拖拽
     * @param securityDTO
     */
    public void sendMailContent(Issue issue, Integer operationType, SecurityDTO securityDTO) {
        String operationName = EmailOperationTypeEnum.getName(operationType);
        log.info("工作项:{},开始封装邮件内容,操作方式：{}", issue.getTitle(), operationName);
        try {
            Long lanedId = issue.getLaneId();
            Long projectId = securityDTO.getProjectId();
            String userName = securityDTO.getUserName();
            Byte issueType = issue.getIssueType();
            Long stageId = Optional.ofNullable(lanedId).orElse(issue.getStageId());
            String stageStatus = "";
            KanbanStageInstanceExample instanceExample = new KanbanStageInstanceExample();
            instanceExample.createCriteria().andStateEqualTo(StateEnum.U.toString())
                    .andStageIdEqualTo(stageId)
                    .andProjectIdEqualTo(projectId);
            List<KanbanStageInstance> stageInstances = stageInstanceMapper.selectByExample(instanceExample);
            if (CollectionUtils.isNotEmpty(stageInstances)) {
                stageStatus = stageInstances.get(0).getStageName();
            }
            switch (IssueTypeEnum.getByCode(issueType)) {
                case TYPE_TASK:
                    stageStatus = TaskStatusEnum.getName(stageId);
                    break;
                case TYPE_FAULT:
                    stageStatus = FaultStatusEnum.getMsg(stageId);
                    break;
                default:
                    break;
            }

            String mailSubject;
            String mailContent;
            if (EmailOperationTypeEnum.ADD.CODE.equals(operationType)) {
                mailSubject = MailContent.ISSUE_CREATE_SUBJECT.replace("userName", userName)
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle());

                mailContent = MailContent.ISSUE_CREATE_CONTENT.replace("userName", userName)
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle());
            } else if (EmailOperationTypeEnum.MODIFY.CODE.equals(operationType)) {
                mailSubject = MailContent.ISSUE_CHANGE_SUBJECT.replace("userName", userName)
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle());
                mailContent = MailContent.ISSUE_CHANGE_CONTENT.replace("userName", userName)
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle())
                        .replace("stageStatus", stageStatus);
            } else if (EmailOperationTypeEnum.DELETE.CODE.equals(operationType)) {
                mailSubject = MailContent.ISSUE_DELETE_SUBJECT.replace("userName", userName)
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle());
                mailContent = MailContent.ISSUE_DELETE_CONTENT.replace("userName", userName)
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle());
            } else {
                mailSubject = MailContent.ISSUE_DRAG_CHANGE_SUBJECT.replace("userName", userName)
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle());
                mailContent = MailContent.ISSUE_DRAG_CHANGE_CONTENT
                        .replace("issueTypeName", IssueTypeEnum.getDesc(issueType))
                        .replace("title", issue.getTitle())
                        .replace("stageStatus", stageStatus);
            }

            sendMail(operationName, issue, mailSubject, mailContent, securityDTO);
        } catch (Exception e) {
            log.info("邮件发送封装数据异常：{}", e);
        }
    }

    /**
     * 业务需求工作项邮件发送接口
     *
     * @param issue       工作项信息
     * @param content     邮件内容，业务操作封装
     * @param securityDTO 操作人员信息
     */
    private void sendMail(String operationName, Issue issue, String mailSubject, String content, SecurityDTO securityDTO) {
        log.info("工作项:{},开始发送邮件内容,操作方式：{},邮件标题：{},内容：{}", issue.getTitle(), operationName, mailSubject, content);
        try {
            Long sprintId = issue.getSprintId();
            Long issueId = issue.getIssueId();
            String title = issue.getTitle();
            String dealUserName = securityDTO.getUserName();
            Long projectId = securityDTO.getProjectId();
            //类型 1:epic 2:feature 3:story 4:task 5:bug
            Byte issueType = issue.getIssueType();

            //邮件接收人邮箱
            List<String> mailList = new ArrayList<>();
            //邮件接收人集合
            List<Long> userIds = new ArrayList<>();
            Long handler = issue.getHandler();
            Long createUid = issue.getCreateUid();

            //0、封装要发送邮件的人，将处理人和创建人添加进去
            if (Optional.ofNullable(handler).isPresent()) {
                userIds.add(handler);
            }
            userIds.add(createUid);
            //1、如果迭代不为空，则将迭代团队人员添加发送邮件集合中
            if (Optional.ofNullable(sprintId).isPresent()) {
                SSprintUserHourExample hourExample = new SSprintUserHourExample();
                hourExample.createCriteria().andSprintIdEqualTo(sprintId);
                List<SSprintUserHour> userSprintHours = sprintHourMapper.selectByExample(hourExample);
                if (CollectionUtils.isNotEmpty(userSprintHours)) {
                    userSprintHours.forEach(userSprintHour -> {
                        userIds.add(userSprintHour.getUserId());
                    });
                }
            }
            //3、人员去重
            List<Long> singleUserIds = userIds.stream().distinct().collect(Collectors.toList());
            //3、根据业务类型/项目编码/人员编码查询通知设置、判断人员是否发送邮件
            MailSwitchExample switchExample = new MailSwitchExample();
            switchExample.createCriteria()
                    .andTypeEqualTo(NumberConstant.ONE.byteValue())
                    .andProjectIdEqualTo(projectId)
                    .andMainSwitchEqualTo(NumberConstant.ONE.byteValue())
                    .andUserIdIn(singleUserIds).andMailTypeEqualTo(issueType);
            List<MailSwitch> mailSwitches = mailSwitchMapper.selectByExample(switchExample);

            List<Long> sendReceivers = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(mailSwitches)) {
                String oneConstant = NumberConstant.ONE.toString();
                mailSwitches.forEach(mailSwitch -> {
                    Byte subSwitch = mailSwitch.getSubSwitch();
                    Long mailUserId = mailSwitch.getUserId();
                    /**
                     * 1、判断人员是否接收邮件， 以及仅接收与自己相关的邮件
                     * 判断业务类型邮件开发是否开启，未开启将人员从邮件发送集合中进行移除
                     * 反之，则判断是否开启仅接收与自己相关的邮件。 及mailUserId 等于处理人handler 或 创建人 createUid
                     * 如果不相等，则将人员从邮件发送集合中进行移除
                     */
                    if (oneConstant.equals(subSwitch.toString())) {
                        if (mailUserId.equals(handler)) {
                            sendReceivers.add(mailUserId);
                        }
                        if (mailUserId.equals(createUid)) {
                            sendReceivers.add(mailUserId);
                        }
                    } else {
                        sendReceivers.add(mailUserId);
                    }

                });
            }
            if (CollectionUtils.isEmpty(sendReceivers)) {
                log.info("工作项:{},邮件发送,接收邮件人员为空，直接return停止邮件发送");
                return;
            }
            //4、查询邮件发送人员的邮箱
            List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(sendReceivers);
            if (CollectionUtils.isNotEmpty(ssoUsers)) {
                ssoUsers.forEach(ssoUser -> {
                    if (Optional.ofNullable(ssoUser.getUserMail()).isPresent()) {
                        mailList.add(ssoUser.getUserMail());
                    }
                });
            }
            //5、查询项目名称
            String businessType = IssueTypeEnum.getName(issueType);
            String businessDesc = IssueTypeEnum.getDesc(issueType);
            SsoProject ssoProject = iFacadeProjectApi.getProjectInfoById(projectId);
            String projectName = ssoProject.getProjectName();

            String business = StringUtils.join(businessDesc, "-", businessType);
            // 邮件主题格式： 【项目名称】【业务类型】【标题】内容变更。
            // String mailSubject = StringUtils.join("[",projectName,"]","[",business,"]","[",title,"]","内容变更");

            //6、封装邮件发送内容
            MailSendDTO mailSendDTO = new MailSendDTO();

            mailSendDTO.setMailSubject(mailSubject);
            Map<String, String> map = new HashedMap();
            map.put("projectName", projectName);
            map.put("issueId", issueId.toString());
            map.put("title", title);
            map.put("business", business);
            map.put("dealUserName", dealUserName);
            map.put("content", content);
            map.put("YuDOWebIp", YuDOWebIp);
            map.put("projectLocation", getProjectName(projectLocation));
            mailSendDTO.setTemplateData(map);
            mailSendDTO.setMailType(MailTypeEnum.MAIL_TYPE_2.getType());
            mailSendDTO.setMailTemplateType(MailTemplateTypeEnum.ISSUE.getValue());
            mailList.forEach(mailAddress -> {
                mailSendDTO.setMailReceivers(mailAddress);
                //rabbitTemplate.convertAndSend(FlowConstant.Queue.MAIL_SEND_QUEUE, mailSendDTO);
                log.info("工作项:{},web服务地址:{},平台名称：{},邮件发送success,接收邮件人员邮箱：{}", title, YuDOWebIp, getProjectName(projectLocation), mailAddress);
            });

        } catch (Exception e) {
            log.error("【{}】工作项:{},邮件发送异常：{}", operationName, issue.getTitle(), e);
        }
        log.info("【{}】工作项:{},邮件发送成功", operationName, issue.getTitle());
    }

    /**
     * 定时任务：业务超期/超时发送邮件
     */
    public void sendOverDueMail(Byte mailType) {
        String type = "超时";
        if (MailSwitchEnum.OVERDUE.getMailType().equals(mailType)) {
            type = "超期";
        }
        //1、查询类型时工作项提醒、且时超期提醒、并且开启接收开关的人。
        List<MailSwitch> mailSwitches = mailSwitchMapper.selectMailSwtchByMailType(mailType);
        if (CollectionUtils.isEmpty(mailSwitches)) {
            log.error("未开启邮件{}配置,直接return,终止邮件发送", type);
            return;
        }
        //2、遍历通知设置，获取所有的projectId 以及Map<Long,Map<Long,MailSwitch>> mailSwitchMap --》Map<ProjectId,Map<userId,MailSwitch>>
        List<Long> projectIds = new ArrayList<>();
        Map<Long, Map<Long, MailSwitch>> mailSwitchMap = new HashMap<>();
        mailSwitches.forEach(mailSwitch -> {
            Long projectId = mailSwitch.getProjectId();
            Long userId = mailSwitch.getUserId();
            projectIds.add(projectId);

            if (mailSwitchMap.containsKey(projectId)) {
                Map<Long, MailSwitch> longMailSwitchMap = mailSwitchMap.get(projectId);
                longMailSwitchMap.put(userId, mailSwitch);
            } else {
                Map<Long, MailSwitch> subMap = new HashMap<>();
                subMap.put(userId, mailSwitch);
                mailSwitchMap.put(projectId, subMap);
            }
        });

        //2.1、根据项目编码/阶段ID不等于7、103、1004，并且endDate小于等于当前时间的工作项列表数据
        List<Long> stageIdState = new ArrayList<Long>() {
            {
                add(TaskStatusEnum.TYPE_CLOSED_STATE.CODE);
                add(StageConstant.FirstStageEnum.READY_STAGE.getValue());
                add(StageConstant.FirstStageEnum.FINISH_STAGE.getValue());
                add(FaultStatusEnum.CLOSED.CODE);
            }
        };

        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andProjectIdIn(projectIds)
                .andStageIdNotIn(stageIdState)
                .andStateEqualTo(StateEnum.U.toString());

        if (MailSwitchEnum.OVERDUE.getMailType().equals(mailType)) {
            criteria.andEndDateLessThanOrEqualTo(new Date());
        }

        issueExample.setOrderByClause("issue_type desc, create_time desc");
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        //2.2 封装迭代数据，格式Map<Long,List<Issue>>
        Map<Long, List<Issue>> sprintMapData = new HashMap<>();
        if (CollectionUtils.isNotEmpty(issues)) {
            issues.forEach(issue -> {
                Long projectId = issue.getProjectId();
                if (sprintMapData.containsKey(projectId)) {
                    List<Issue> issueList = sprintMapData.get(projectId);
                    issueList.add(issue);
                } else {
                    List<Issue> issueList = new ArrayList<>();
                    issueList.add(issue);
                    sprintMapData.put(projectId, issueList);
                }
            });
        }
        Map<Long, Map<String, Integer>> stageProInstanceMap = getKanbanInstance(projectIds);
        sendSprintDataMail(sprintMapData, mailSwitchMap, mailType, stageProInstanceMap);
    }

    private Map<Long, Map<String, Integer>> getKanbanInstance(List<Long> projectIds) {
        //Map<>
        Map<Long, Map<String, Integer>> stageProInstanceMap = new HashMap<>();
        KanbanStageInstanceExample instanceExample = new KanbanStageInstanceExample();
        instanceExample.createCriteria().andStateEqualTo(StateEnum.U.toString())
                .andProjectIdIn(projectIds);
        List<KanbanStageInstance> stageInstances = stageInstanceMapper.selectByExample(instanceExample);
        if (CollectionUtils.isNotEmpty(stageInstances)) {
            stageInstances.forEach(stageInstance -> {
                Long projectId = stageInstance.getProjectId();
                Long parentId = stageInstance.getParentId();
                Long stageId = stageInstance.getStageId();
                Integer stayDays = Optional.ofNullable(stageInstance.getStayDays()).orElse(0);
                String stages = parentId.equals(-1L) ? stageId + "" : StringUtils.join(parentId, "-", stageId);
                if (stageProInstanceMap.containsKey(projectId)) {
                    Map<String, Integer> stageInstanceMap = stageProInstanceMap.get(projectId);
                    stageInstanceMap.put(stages, stayDays);
                } else {
                    Map<String, Integer> stageInstanceMap = new HashMap<>();
                    stageInstanceMap.put(stages, stayDays);
                    stageProInstanceMap.put(projectId, stageInstanceMap);
                }
            });
        }
        return stageProInstanceMap;
    }

    private void sendSprintDataMail(Map<Long, List<Issue>> sprintMapData, Map<Long, Map<Long, MailSwitch>> mailSwitchMap,
                                    Byte mailType, Map<Long, Map<String, Integer>> stageProInstanceMap) {
        //Map<Long,List<Issue>> sprintMapata --><projectId,List<Issue>>
        for (Map.Entry<Long, List<Issue>> entry : sprintMapData.entrySet()) {
            Long projectId = entry.getKey();
            Map<String, Integer> stageMap = stageProInstanceMap.get(projectId);
            List<Issue> issueList = entry.getValue();
            Map<Long, MailSwitch> userMailSwitchMap = mailSwitchMap.get(projectId);
            //邮件接收人邮箱 key表示人员ID，value表示人员邮箱
            Map<Long, String> mailReceiversMap = new HashMap<>();
            //封装邮件内容

            String oneConstant = NumberConstant.ONE.toString();
            Map<Long, String> userNameMap = new HashMap<>();

            //1、如果迭代不为空，则将迭代团队人员添加发送邮件集合中
            Map<Long, String> sendContentMap = new HashMap<>();
            for (Map.Entry<Long, MailSwitch> switchEntry : userMailSwitchMap.entrySet()) {
                Long userId = switchEntry.getKey();
                MailSwitch mailSwitch = switchEntry.getValue();
                Byte subSwitch = mailSwitch.getSubSwitch();
                SsoUser ssoUserDTO = iFacadeUserApi.queryUserById(userId);
                if (Optional.ofNullable(ssoUserDTO).isPresent()) {
                    mailReceiversMap.put(userId, ssoUserDTO.getUserMail());
                }

                issueList.stream().forEach(issue -> {
                    Long handler = Optional.ofNullable(issue.getHandler()).orElse(0L);
                    Long createUid = issue.getCreateUid();
                    Long stageId = issue.getStageId();
                    Long laneId = issue.getLaneId();
                    Byte issueType = issue.getIssueType();
                    Long issueId = issue.getIssueId();
                    String title = issue.getTitle();

                    //【编码：1】-【标题：需求导入】-【要求上线日期:2020-03-07，超期/停留：103天】-【处理人：】
                    Integer staysDays = 0;
                    Date endDate = issue.getEndDate();
                    String endDateStr = "未设置日期";
                    if (Optional.ofNullable(endDate).isPresent()) {
                        endDateStr = DateUtil.formatDateToStr(endDate);
                    }
                    String type = "超期";
                    if (MailSwitchEnum.OVERDUE.getMailType().equals(mailType)) {
                        staysDays = DateUtil.differenceBetweenNow(endDate);
                    } else {
                        type = "停留";
                        IssueDTO issueDTO = ReflectUtil.copyProperties(issue, IssueDTO.class);
                        staysDays = calueCardStayDays(issueDTO);
                    }


                    /** 判断邮件类型是超时的，且阶段状态下的停留天数大于卡片的停留天数，则立刻跳出循环 */
                    if (MailSwitchEnum.OVERTIME.getMailType().equals(mailType)
                            && (!IssueTypeEnum.TYPE_TASK.CODE.equals(issueType) && !IssueTypeEnum.TYPE_FAULT.CODE.equals(issueType))) {
                        String stages = Optional.ofNullable(laneId).isPresent() ? StringUtils.join(stageId, "-", laneId) : stageId + "";
                        Integer stopMaxDays = stageMap.get(stages);
                        if (stopMaxDays > staysDays && staysDays == 0) {
                            log.info("工作项ID：{},工作项名称：{},当前停留天数：{},设置停留天数：{}, 跳出循环，终止发送该条工作项", issueId, title, staysDays, stopMaxDays);
                            return;
                        }
                    }

                    if (!userNameMap.containsKey(handler)) {
                        SsoUser ssoUserDTOS = iFacadeUserApi.queryUserById(handler);
                        if (Optional.ofNullable(ssoUserDTOS).isPresent()) {
                            userNameMap.put(handler, ssoUserDTOS.getUserName());
                        }
                    }

                    //[编码-标题-结束日期-处理人]
                    String handlerName = Optional.ofNullable(userNameMap.get(handler)).orElse("未领取");
                    String issueTypeName = IssueTypeEnum.getDesc(issue.getIssueType());
                    StringBuilder content = new StringBuilder();
                    content.append("<p>").append("【业务类型：" + issueTypeName + "】").append("【编码：" + issue.getIssueId() + "】").append("-")
                            .append("【标题：" + issue.getTitle() + "】").append("-")
                            .append("【结束日期：" + endDateStr + "," + type + ":" + staysDays + "天】").append("-")
                            .append("【处理人：" + handlerName + "】").append("</p>");

                    /**
                     * 1、判断人员是否接收邮件， 以及仅接收与自己相关的邮件
                     * 判断业务类型邮件开发是否开启，未开启将人员从邮件发送集合中进行移除
                     * 反之，则判断是否开启仅接收与自己相关的邮件。 及mailUserId 等于处理人handler 或 创建人 createUid
                     * 如果不相等，则将人员从邮件发送集合中进行移除
                     */
                    if (oneConstant.equals(subSwitch.toString())) {
                        if (handler.equals(createUid) && handler.equals(userId)
                                && createUid.equals(userId)) {
                            commonSendContentMap(sendContentMap, userId, content);
                        } else {
                            if (handler.equals(userId)) {
                                commonSendContentMap(sendContentMap, userId, content);
                            }
                            if (createUid.equals(userId)) {
                                commonSendContentMap(sendContentMap, userId, content);
                            }
                        }

                    } else {
                        commonSendContentMap(sendContentMap, userId, content);
                    }
                });
            }

            SsoProject ssoProject = iFacadeProjectApi.getProjectInfoById(projectId);
            if (!Optional.ofNullable(ssoProject).isPresent()) {
                log.info("项目ID：{},不存在，终止发送", projectId);
                continue;
            }
            String projectName = ssoProject.getProjectName();

            send(mailReceiversMap, projectName, sendContentMap, mailType);
        }
    }

    /**
     * 封装邮件发件内容
     *
     * @param sendContentMap<Long,String> -->sendContentMap<UserId,Content> ：key表示人员ID，Content表示邮件内容
     * @param userId
     * @param content
     */
    private void commonSendContentMap(Map<Long, String> sendContentMap, Long userId, StringBuilder content) {
        if (sendContentMap.containsKey(userId)) {
            content.append(sendContentMap.get(userId));
        }
        sendContentMap.put(userId, content.toString());
    }

    /**
     * 一个项目下超期的工作项发送给相关团队成员
     *
     * @param mailReceiversMap
     * @param projectName
     * @param sendContentMaps
     */
    private void send(Map<Long, String> mailReceiversMap, String projectName, Map<Long, String> sendContentMaps, Byte mailType) {
        String type = "超时";
        if (MailSwitchEnum.OVERDUE.getMailType().equals(mailType)) {
            type = "超期";
        }
        // 邮件标题格式:【超期】【项目名称】中有超期工作项
        String mailSubject = StringUtils.join("【", type, "】", "【", projectName, "】", "中有", type, "工作项");

        //6、封装邮件发送内容模版
        for (Map.Entry<Long, String> entry : sendContentMaps.entrySet()) {
            String contents = entry.getValue();
            Long userId = entry.getKey();
            MailSendDTO mailSendDTO = new MailSendDTO();
            mailSendDTO.setMailSubject(mailSubject);
            Map<String, String> map = new HashedMap();
            map.put("projectName", projectName);
            map.put("content", contents);
            map.put("YuDOWebIp", YuDOWebIp);
            map.put("projectLocation", getProjectName(projectLocation));
            mailSendDTO.setTemplateData(map);
            mailSendDTO.setMailType(MailTypeEnum.MAIL_TYPE_2.getType());
            mailSendDTO.setMailTemplateType(MailTemplateTypeEnum.ISSUE_TIMEOUT.getValue());
            mailSendDTO.setMailReceivers(mailReceiversMap.get(userId));
            log.info("定时任务:{}邮件发送模版：{}", type, mailSendDTO.getTemplateData());
            //rabbitTemplate.convertAndSend(FlowConstant.Queue.MAIL_SEND_QUEUE, mailSendDTO);
            log.info("定时任务:{}邮件发送成功", type);
        }
    }

    /**
     * 计算工作项停留天数
     *
     * @param issueDTO
     * @return
     */
    private Integer calueCardStayDays(IssueDTO issueDTO) {
        int stayDays = 0;
        Long stageId = issueDTO.getStageId();
        Long laneId = issueDTO.getLaneId();
        Long issueId = issueDTO.getIssueId();
        String stages = Optional.ofNullable(laneId).isPresent() ? StringUtils.join(stageId, "-", laneId) : stageId + "";

        IssueHistoryRecordExample example = new IssueHistoryRecordExample();
        IssueHistoryRecordExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdEqualTo(issueId).andNewValueEqualTo(stages);
        example.setOrderByClause(CREATE_TIME_DESC);
        List<IssueHistoryRecord> issueHistoryRecords = issueHistoryRecordMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(issueHistoryRecords)) {
            for (IssueHistoryRecord historyRecord : issueHistoryRecords) {
                Integer stayDaysNew = historyRecord.getStayDays();
                Date createTime = historyRecord.getCreateTime();
                if (Optional.ofNullable(stayDaysNew).isPresent()) {
                    stayDays = stayDays + stayDaysNew;
                } else {
                    Long days = (System.currentTimeMillis() - createTime.getTime()) / (24 * 60 * 60 * 1000);
                    stayDays = stayDays + days.intValue();
                }
            }
        }

        return stayDays;
    }

    /**
     * 事务卡片超期提醒
     */
    public void sendBusinessOverDueMail(Byte mailType) {
        //1、区分是超期邮件还是超时邮件
        String type = "超期";

        if (MailSwitchEnum.OVERTIME.getMailType().equals(mailType)) {
            type = "超时";
        }
        //2、区分是超期/超时的事务卡片
        BusinessExample businessExample = new BusinessExample();
        businessExample.createCriteria().andStateEqualTo(StateEnum.U.toString())
                .andIsVisibleEqualTo(NumberConstant.ZERO.byteValue())
                .andBusinessStateNotEqualTo(BusinessState.COMPLETE.getNodeCode().byteValue())
                .andPlanEndTimeLessThanOrEqualTo(new Date());
        businessExample.setOrderByClause(CREATE_TIME_DESC);
        List<Business> businesses = businessMapper.selectByExample(businessExample);

        //2.1 封装事务看板数据，格式Map<Long,Map<Long,List<Business>>> -->Map<ProjectId,Map<kanbanId,List<Business>>>
        Map<Long, Map<Long, List<Business>>> businessMapData = new HashMap<>();
        if (CollectionUtils.isNotEmpty(businesses)) {
            businesses.forEach(business -> {
                Long projectId = business.getProjectId();
                Long kanbanId = business.getKanbanId();
                if (businessMapData.containsKey(projectId)) {
                    Map<Long, List<Business>> kanbanMap = businessMapData.get(projectId);
                    if (kanbanMap.containsKey(kanbanId)) {
                        List<Business> businessList = kanbanMap.get(kanbanId);
                        businessList.add(business);
                    } else {
                        List<Business> businessList = new ArrayList<>();
                        businessList.add(business);
                        kanbanMap.put(kanbanId, businessList);
                    }
                } else {
                    Map<Long, List<Business>> kanbanMap = new HashMap<>();
                    List<Business> businessList = new ArrayList<>();
                    businessList.add(business);
                    kanbanMap.put(kanbanId, businessList);
                    businessMapData.put(projectId, kanbanMap);
                }
            });
        }
        //3、判断项目下事务卡片不为空
        if (!businessMapData.isEmpty()) {
            //3.1、判断项目下事务看板事务卡片数据
            for (Map.Entry<Long, Map<Long, List<Business>>> entry : businessMapData.entrySet()) {
                Long projectId = entry.getKey();
                //3.4、查询项目信息,如果为空直接continue
                SsoProject ssoProject = iFacadeProjectApi.getProjectInfoById(projectId);
                if (!Optional.ofNullable(ssoProject).isPresent()) {
                    continue;
                }
                String projectName = ssoProject.getProjectName();
                Map<Long, List<Business>> kanbanMap = entry.getValue();
                Long kanbanId = null;
                /** 去重邮件接收人 */
                Set<Long> userIdSet = new HashSet<>();

                //3.2、判断事务看板事务卡片数据.封装邮件接受人和邮件内容
                for (Map.Entry<Long, List<Business>> kanbanEntry : kanbanMap.entrySet()) {
                    kanbanId = kanbanEntry.getKey();
                    List<Business> businessList = kanbanEntry.getValue();

                    String finalType = type;
                    /** 邮件发送内容 */
                    StringBuilder content = new StringBuilder();
                    businessList.forEach(b -> {
                        Long businessOwner = b.getBusinessOwner();
                        String businessTypeName = BusinessType.getNodeMsg(b.getBusinessType());
                        String businessStateName = BusinessState.getNodeMsg(b.getBusinessState());
                        String businessOwnerName = Optional.ofNullable(b.getBusinessOwnerName()).orElse("");
                        Date endDate = b.getPlanEndTime();
                        String endDateStr = "未设置日期";
                        if (Optional.ofNullable(endDate).isPresent()) {
                            endDateStr = DateUtil.formatDateToStr(endDate);
                        }
                        int costDays = DateUtil.getCostDays(endDate, new Date());
                        userIdSet.add(b.getCreateUid());
                        if (Optional.ofNullable(businessOwner).isPresent()) {
                            userIdSet.add(businessOwner);
                        }
                        content.append("<p>").append("【事务类型：" + businessTypeName + "】")
                                .append("【事务编码：" + b.getBusinessId() + "】").append("-")
                                .append("【事务名称：" + b.getBusinessName() + "】").append("-")
                                .append("【事务状态：" + businessStateName + "】").append("-")
                                .append("【结束日期：" + endDateStr + "," + finalType + "天数:" + costDays + "天】").append("-")
                                .append("【处理人：" + businessOwnerName + "】").append("</p>");
                    });

                    List<Long> userIds = new ArrayList<>(userIdSet);
                    /** 封装邮件接收人邮箱  */
                    List<String> receivers = new ArrayList<>();
                    //3.3、查询邮件接收人员信息
                    List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIds);
                    if (CollectionUtils.isNotEmpty(ssoUsers)) {
                        ssoUsers.forEach(ssoUser -> receivers.add(ssoUser.getUserMail()));
                    }

                    //3.4、查询事务看板信息
                    BusinessKanban businessKanban = businessKanbanMapper.selectByPrimaryKey(kanbanId);
                    String kanbanName = businessKanban.getKanbanName();

                    //3.5、封装邮件模版内容
                    String mailSubject = StringUtils.join("【", type, "】", "【", projectName, "】", "【", kanbanName, "】", "中有", type, "的事务卡片");
                    MailSendDTO mailSendDTO = new MailSendDTO();
                    mailSendDTO.setMailSubject(mailSubject);
                    Map<String, String> map = new HashedMap();
                    map.put("projectName", projectName);
                    map.put("kanbanName", kanbanName);
                    map.put("overTitle", type + "内容");
                    map.put("content", content.toString());
                    map.put("YuDOWebIp", YuDOWebIp);
                    map.put("projectLocation", getProjectName(projectLocation));
                    mailSendDTO.setTemplateData(map);
                    mailSendDTO.setMailType(MailTypeEnum.MAIL_TYPE_2.getType());
                    mailSendDTO.setMailTemplateType(MailTemplateTypeEnum.BUSSINESS_TIMEOUT.getValue());
                    receivers.forEach(receiver -> {
                        mailSendDTO.setMailReceivers(receiver);
                        log.info("事务卡片超期提醒定时任务{}邮件接受人：{}", finalType, receiver);
                        //rabbitTemplate.convertAndSend(FlowConstant.Queue.MAIL_SEND_QUEUE, mailSendDTO);
                        log.info("事务卡片超期提醒定时任务{}邮件发送完成", finalType);
                    });
                }
            }
        }
    }

    private String getProjectName(String projectLocation) {
        String projectName = "研发运维一体化平台";
        switch (projectLocation) {
            case "yuxin":
                projectName = "研发运维一体化平台";
                break;
            default:
                break;
        }
        return projectName;
    }

}
