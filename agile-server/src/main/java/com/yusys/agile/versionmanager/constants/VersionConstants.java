package com.yusys.agile.versionmanager.constants;

public class VersionConstants {

    //每页记录条数
    public static final int PAGE_SIZE = 100;

    public static class VersionManagerConstant {
        public static final String DATE_FORMAT = "yyyy-MM-dd";
        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

        // 版本计划查询类型
        public static final int VERSION_PLAN_BASIC_QUERY = 1;
        public static final int VERSION_PLAN_ADVANCE_QUERY = 2;

        // 基线版本标记
        public static final Byte BASELINE_FLAG = 1;

        // 版本计划同步结果
        public static final Integer SYNC_SUCCESSFULLY = Integer.valueOf(1);
        public static final Integer SYNC_FAILED = Integer.valueOf(2);

        // 版本计划审批结果字段
        public static final String APPROVED = "0"; // 通过
        public static final String REJECTED = "1"; // 不通过
        public static final String APPROVE_RESULT_ID = "id"; // 版本计划主键id
        public static final String APPROVE_RESULT_VERSION_STATUS = "versionStatus"; // 版本的审批状态：0-通过，1-不通过
        public static final String APPROVE_RESULT_RELATED_REQUIREMENT = "relatedRequirementList"; // 需求审批结果列表
        public static final String APPROVE_RESULT_CODE = "code"; // 局方需求编号
        public static final String APPROVE_RESULT_BIZNUM = "bizNum"; // 客户需求编号
        public static final String APPROVE_RESULT_STATUS = "status"; // 需求的审批状态：0-通过，1-不通过
        public static final Byte APPROVE_FAILED_STATUS = 3; // 审批失败(已到最大审批次数)
        public static final String APPROVE_RESULT_MSG = "msg"; // 审批意见
        public static final String APPROVE_RESULT_PLAN_DEPLOY_DATE = "planDeployDate"; // 计划部署时间
        public static final String APPROVE_RESULT_INTERSECTION_TYPE = "intersectionType"; // 交维类型
        public static final String EXCEED_MAX_APPROVAL_COUNT_KEYWORD = "无法再次审批"; // 达到最大审批次数时返回的关键字

        // 响应结果字段值
        public static final String RESPONSE_SUCCESS_CODE = "200";
        public static final String RESPONSE_FAILURE_CODE = "400";
        public static final String RESPONSE_SUCCESS_VALUE = "successful";
        public static final String RESPONSE_FAILURE_VALUE = "failed";


        // 审批结果
        public static final String APPROVE_PASS = "通过";
        public static final String APPROVE_FAILED = "不通过";
        public static final String EMPTY_STRING = "";

        // 最大审批次数
        public static final int REVIEW_MAX_COUNT = 10;

        // 版本已同步到第三方平台的状态
        public static final int SEND_TO_SUCCESS = 1;

        // 默认的空的bizType的值为
        public static final Byte DEFAULT_BIZ_TYPE = -1;

        // 需求的审批状态
        public static final Byte UN_APPROVAL = 0; // 未审批
        public static final Byte REVIEWING = 1; // 审批中
        public static final Byte APPROVAL = 2; // 已审批
        public static final Byte EXCEED_MAX_APPROVAL = 3; // 审批失败(已到最大审批次数)

        // 版本的部署类型
        public static final Byte ROUTINE_DEPLOY_TYPE = 1; // 常规部署
        public static final Byte HOT_DEPLOY_TYPE = 2; // 热部署
    }

    public static class SysExtendFiledConstant {
        public static final String RESPONSIBLEPERSON = "responsiblePerson";
        public static final String BIZNUM = "bizNum";
        public static final String PLANSTATES = "planStates";
        public static final String PLANSTATESNAME = "需求计划状态";
        public static final String REQSCHEDULING = "reqScheduling";
        public static final String REQSCHEDULINGNAME = "需求排期";
        public static final String SOURCE = "source";
        public static final String BJ_SOURCE = "bjSource";
        public static final String REQGROUP = "reqGroup";
        public static final String FORMALREQCODE = "formalReqCode";
        public static final String MAKESECONDARYDEP = "makeSecondaryDep";
        public static final String MAKEMAN = "makeMan";
        public static final String ACTUAL_ASK_LINE_TIME = "actualAskLineTime";
        public static final String ACTUAL_ASK_LINE_TIME_NAME = "实际要求上线时间";
        public static final String APPROVAL_RESULT = "approvalResult";
        public static final String APPROVAL_RESULT_NAME = "局方审批结果";
        public static final String APPROVAL_END_TIME = "approvalEndTime";
        public static final String APPROVAL_END_TIME_NAME = "审批结束时间";
        public static final String APPROVAL_START_TIME = "approvalStartTime";
        public static final String APPROVAL_START_TIME_NAME = "审批开始时间";
        public static final String APPROVAL_STATUS = "approvalStatus";
        public static final String APPROVAL_STATUS_NAME = "局方审批状态";
        public static final String ASK_LINE_TIME = "askLineTime";
        public static final String ASK_LINE_TIME_NAME = "要求上线时间";
        public static final String DEVLOPMANAGER = "devlopManager";
        public static final String EXTERNALHANDLERID = "externalHandlerId";
        public static final String SERVERANALYSTMANAGER = "serverAnalystManager";
        public static final String BIZTYPE = "bizType";
        public static final String BIZTYPENAME = "代码配置比";
        public static final String PLANDEPLOYDATE = "planDeployDate";
        public static final String ONLINEBATCHTIME = "onlineBatchTime";
        public static final String IF_KEY = "ifKey";
        public static final String LABEL = "label";
        public static final String ACTUAL_ONLINE_TIME = "actualOnlineTime";
        public static final String RELATEDSYSTEM = "relatedSystem";
        public static final String ACTUAL_ONLINE_TIME_NAME = "需求实际上线时间";


    }

    public static class VersionHistoryConstant {
        public static final int BINDING_TYPE = 1; // 版本绑定需求操作
        public static final int UNBINDING_TYPE = 2; // 版本解绑需求操作
        public static final int REQ_CHANGE_RECORD_TYPE = 1; // 需求变更记录类型
        public static final String REQ_ADD_OP_FIELD = "增加需求";
        public static final String REQ_DEL_OP_FIELD = "删除需求";
        public static final String EMPTY_STRING = "";
    }

    public static class VersionChangeSyncConstant {
        /**
         * 同步失败
         */
        public static final Byte SYNC_FAIL = 0;

        /**
         * 同步成功
         */
        public static final Byte SYNC_SUCCESS = 1;
    }

    public static class RequirememtSendConstants {
        public static final String PLANSTATES_NO_DEPLOY = "8884";
    }
}
