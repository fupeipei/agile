package com.yusys.agile.utils;

/**
 *
 */
public class MailContent {
    /**
     * EPIC Feature story task fault
     */
    public static final String ISSUE_CREATE_SUBJECT = "【YUDO】userName新增issueTypeName [title]";
    //林光变更任务 [前端--高级搜索按过滤条件搜索工作项接口调试] 状态
    public static final String ISSUE_CHANGE_SUBJECT = "【YUDO】userName变更issueTypeName [title]";
    public static final String ISSUE_DRAG_CHANGE_SUBJECT = "【YUDO】userName变更issueTypeName [title]状态";
    public static final String ISSUE_DELETE_SUBJECT = "【YUDO】userName删除issueTypeName [title]";

    public static final String ISSUE_CREATE_CONTENT = "【YUDO】userName新增issueTypeName [title] ";
    public static final String ISSUE_CHANGE_CONTENT = "【YUDO】issueTypeName [title]被userName修改,当前状态为[stageStatus]";
    /**
     * 研发需求[前端--高级搜索按过滤条件搜索工作项接口调试] 状态变为研发中
     */
    public static final String ISSUE_DRAG_CHANGE_CONTENT = "【YUDO】issueTypeName[title] 状态变更为 [stageStatus]";
    public static final String ISSUE_DELETE_CONTENT = "【YUDO】issueTypeName [title]被userName删除为失效";

//
//
//    public static final String CARD_STATE_SUBJECT = "【YUDO】卡片[cardName]状态变更";
//
//    //card
//    public static final String CARD_TIMEOUT_SUBJECT = "【YUDO】[项目] projectName 任务超时";
//    public static final String CARD_TIMEOUT_CONTENT = "[卡片标题]cardName 超时days天";
//
//    public static final String CARD_STATE_CONTENT = "[项目名称] projectName<br/> [卡片] cardName 状态变更为stateDesc";
//
//
//    //task
//
//    public static final String TASK_STATE_CHANGE_CONTENT = "[项目名称] projectName<br/> [故事标题] storyName<br/>[任务标题] taskName 状态变为state";
//    public static final String TASK_TIMEOUT_SUBJECT = "【YUDO】[项目] projectName 任务超时";
//    public static final String TASK_TIMEOUT_CONTENT = "[故事标题]storyName [任务标题]taskName 超时days天";
//
//
//    //story
//    public static final String CREATE_STORY_SUBJECT = "【YUDO】userName新增用户故事 [storyName] ";
//    public static final String CREATE_STORY_CONTENT = "[项目名称] projectName<br/> userName 新增故事 [故事标题] storyName（storyId）<br/>[故事描述] storyDesc";
//    public static final String UPDATE_STORY_SUBJECT = "【YUDO】userName变更用户故事 [storyName] ";
//    public static final String UPDATE_STORY_CONTENT = "[项目名称] projectName<br/> [故事标题] storyName（storyId）被userName修改";
//    public static final String STORY_STATE_SUBJECT = "【YUDO】userName变更用户故事[storyName]状态";
//    public static final String STORY_STATE_CONTENT = "[项目名称] projectName<br/>[故事标题] storyName（storyId）状态变为stateDesc";
//    public static final String DELETE_STORY_SUBJECT = "【YUDO】userName 删除用户故事[storyName]";
//    public static final String DELETE_STORY_CONTENT = "[项目名称] projectName<br/> [故事标题] storyName（storyId）被userName删除";
//
//    //backlog
//    public static final String CREATE_BACKLOG_SUBJECT = "【YUDO】新增用户需求[backlogName]";
//    public static final String CREATE_BACKLOG_CONTENT = "[项目名称] projectName<br/> userName 新增需求 [需求标题] backlogName（backlogId）<br/>[需求描述] backlogDesc";
//    public static final String UPDATE_BACKLOG_SUBJECT = "【YUDO】userName 变更用户需求[backlogName]";
//    public static final String UPDATE_BACKLOG_CONTENT = "[项目名称] projectName<br/> [需求标题] backlogName（backlogId）被userName修改";
//    public static final String DELETE_BACKLOG_SUBJECT = "【YUDO】userName删除用户需求[backlogName]";
//    public static final String DELETE_BACKLOG_CONTENT = "[项目名称] projectName<br/> [需求标题] backlogName（backlogId）被userName删除";
//    public static final String BACKLOG_STATE_SUBJECT = "【YUDO】userName变更用户需求[backlogName]状态";
//    public static final String BACKLOG_STATE_CONTENT = "[项目名称] projectName<br/> [需求标题] backlogName（backlogId）状态变为stateDesc";
//    public static final String BACKLOG_TIMEOUT_SUBJECT = "【YUDO】[项目名称] projectName 需求超时";
//    public static final String BACKLOG_TIMEOUT_CONTENT = "[需求标题] backlogName（backlogId）要求上线日期:planFinishTime，超期days天";
//
//    //sprint
//    public static final String CREATE_SPRINT_SUBJECT = "【YUDO】新增迭代计划[sprintName]";
//    public static final String CREATE_SPRINT_CONTENT = "[项目名称] projectName 新增迭代计划 [迭代名称] sprintName<br/>迭代周期startTime-endTime，共days天，每天工作时长hours。迭代成员：users，迭代中包含storyCount个故事";
//    public static final String CANCEL_SPRINT_SUBJECT = "【YUDO】userName取消迭代计划[sprintName]";
//    public static final String CANCEL_SPRINT_CONTENT = "[项目名称] projectName<br/> [迭代计划] sprintName 被userName取消";
//    public static final String DELETE_SPRINT_SUBJECT = "【YUDO】userName删除迭代计划[sprintName]";
//    public static final String DELETE_SPRINT_CONTENT = "[项目名称] projectName<br/> [迭代计划] sprintName 被userName删除";
//    public static final String EDIT_SPRINT_SUBJECT = "【YUDO】userName修改迭代计划[sprintName]";
//    public static final String EDIT_SPRINT_CONTENT = "[项目名称] projectName<br/> [迭代计划] sprintName 被userName修改";
//    public static final String SPRINT_STATE_SUBJECT = "【YUDO】迭代计划[sprintName]状态变更";
//    public static final String SPRINT_STATE_CONTENT = "[项目名称] projectName<br/> [迭代计划] sprintName 状态变为 stateDesc";
//
//
//    //fault
//    public static final String FAULT_CREATE_SUBJECT = "【YUDO】新增缺陷[faultName] 通知邮件";
//    public static final String FAULT_CREATE_CONTENT = "[项目名称] projectName<br/> [缺陷] 新增缺陷： faultName";
//    public static final String FAULT_STATE_SUBJECT = "【YUDO】缺陷[faultName]状态变更";
//    public static final String FAULT_STATE_CONTENT = "[项目名称] projectName<br/> [缺陷] faultName 状态变更为stateDesc";
//
//    //projecgt
//    public static final String PROJECT_CHANGE_MANAGER_SUBJECT_FOR_PM = "【YUDO】项目[projectName]管理员变更 ";
//    public static final String PROJECT_CHANGE_MANAGER_CONTENT_FOR_PM = "尊敬的用户PM，PMChangeDesc，请知悉";
//    public static final String PROJECT_ADD_MANAGER_CONTENT_FOR_PM = "添加 newUser 为 [项目]projectName 的管理员";
//    public static final String PROJECT_RM_MANAGER_CONTENT_FOR_PM  = "移除 oldUser 在 [项目]projectName 中的管理员职位";
//
//    public static final String PROJECT_CHANGE_USER_SUBJECT = "【YUDO】项目 [projectName] 用户变更";
//    public static final String PROJECT_CHANGE_USER_CONTENT = "尊敬的用户PM，userChangeDesc,请知悉";
//    public static final String PROJECT_RM_USER_CONTENT = "YUDO项目管理员 admin 将 oldUser 移出 [项目]projectName";
//    public static final String PROJECT_ADD_USER_CONTENT = "YUDO项目管理员 admin 将 newUser 加入 [项目]projectName";
//
//
//    public static final String ROLE_CHANGE_SUBJECT_FOR_USER = "【YUDO】项目[projectName]用户权限变更";
//    public static final String ROLE_CHANGE_CONTENT_FOR_USER = "尊敬的用户userName，YUDO项目管理员 admin 把您在 [项目]projectName 中的角色设定为 roleName";
//
//    public static final String ROLE_CHANGE_SUBJECT_FOR_PM = "【YUDO】项目[projectName]用户权限变更";
//    public static final String ROLE_CHANGE_CONTENT_FOR_PM = "尊敬的用户PM，YUDO项目管理员 admin 把 userName 在 [项目]projectName 中的角色设定为 roleName,请知悉";


}
