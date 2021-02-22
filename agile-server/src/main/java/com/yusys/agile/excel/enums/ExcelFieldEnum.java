package com.yusys.agile.excel.enums;

public class ExcelFieldEnum {

    /**
     * @description 需求主体
     * @date 2021/2/1
     */
    public enum ReqSubject {
        KEHUZHONGXIN("客户中心", "377006"), GERENDINGDAN("个人订单", "377014"), WULIANWANG("物联网", "377011"), PEIZHI("配置", "377005"),
        JIAKUANG("家宽", "377004"), NGCRM("NGCRM", "377001"), QUANWANGYEWU("全网业务", "377013"), NENGLIKAIFANGPINGTAI("能力开放平台", "377010"),
        DINGDANZHONGXIN("订单中心", "377002"), CHANSHANGPINZHONGXIN("产商品中心", "377012"), JITUAN("集团", "377003"), XIEZHUANZHONGXIN("携转中心", "377015"),
        ZHONGTAI("中台", "377016"), XIANGMU("项目", "377017"), ZHANGGUAN("账管", "377018"), YIDIANPEIZHI("一点配置", "377019");

        private String fieldName;
        private String fieldValue;

        ReqSubject(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public static String getFieldName(String fieldValue) {
            String fieldName = "";
            for (ReqSubject reqSubject : ReqSubject.values()) {
                if (reqSubject.fieldValue.equals(fieldValue)) {
                    fieldName = reqSubject.fieldName;
                }
            }
            return fieldName;
        }
    }

    /**
     * @description 需求计划状态
     * @date 2021/2/1
     */
    public enum ReqPlanStates {
        XUQIUQUXIAO("需求取消", "8880"), XUQIUGUAQI("需求挂起", "8881"), XUQIUQUXIAOGUAQI("需求取消挂起", "8882"),
        YANQI("延期", "8883"), WUXUBUSHU("无需部署", "8884"), ANQI("按期", "8888"), FENQI("分期", "8889");

        private String fieldName;
        private String fieldValue;

        ReqPlanStates(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public static String getFieldName(String fieldValue) {
            String fieldName = "";
            for (ReqPlanStates reqPlanStates : ReqPlanStates.values()) {
                if (reqPlanStates.fieldValue.equals(fieldValue)) {
                    fieldName = reqPlanStates.fieldName;
                }
            }
            return fieldName;
        }
    }

    /**
     * @description 需求组
     * @date 2021/2/1
     */
    public enum ReqGroup {
        GERENYEWUZU("个人业务组", "001"), JITUANYEWUZU("集团业务组", "002"), JICHUFUWUZU("基础服务组", "003"),
        DIANZIJIHEZU("电子稽核组", "004"), GUIHUAJIANSHEZU("规划建设组", "005"), ITZHICHENGSHIXUQIUZUYIZU("IT支撑室需求组1组", "012"),
        XITONGYUNWEIZU("系统运维组", "013"), ITZHICHENGSHIXUQIUZUERZU("IT支撑室需求组2组", "023"), YEWUYUNWEIZU("业务运维组", "024"),
        FUWUZHICHENGZU("服务支撑组", "025"), HUAGUANXUQIUZU("话管需求组", "050");

        private String fieldName;
        private String fieldValue;

        ReqGroup(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public static String getFieldName(String fieldValue) {
            String fieldName = "";
            for (ReqGroup reqGroup : ReqGroup.values()) {
                if (reqGroup.fieldValue.equals(fieldValue)) {
                    fieldName = reqGroup.fieldName;
                }
            }
            return fieldName;
        }
    }

    /**
     * @description 交维子类型
     * @date 2021/2/1
     */
    public enum SubInterType {
        XINZENGYEWULIUCHENG("业务影响判断-新增业务流程", "1001"), YUANYOULIUCHENGBIANGENG("业务影响判断-原有流程变更", "1002"), XINZENGHUOBIANGENG("系统影响-新增或变更接口、服务、功能", "1003"),
        KEFUTANPING("系统影响-客服弹屏", "1004"), YINGXIANGJITUANKAOHE("系统影响-影响集团考核", "1005"), YUANYOUKUBIAOJIEGOUBIANGENG("系统影响-新增库表、运维影响-原有库表结构变更", "1006"),
        XINZENGMRT("运维影响-新增MRT", "1007"), MRTBIANGENG("运维影响-MRT变更", "1008"), YUJIYINGXIANGXINGNENGCUXIAO("应用性能影响-预计影响性能的促销", "1009");

        private String fieldName;
        private String fieldValue;

        SubInterType(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public static String getFieldName(String fieldValue) {
            String fieldName = "";
            for (SubInterType subInterType : SubInterType.values()) {
                if (subInterType.fieldValue.equals(fieldValue)) {
                    fieldName = subInterType.fieldName;
                }
            }
            return fieldName;
        }
    }

    /**
     * @description 部署类型
     * @date 2021/2/1
     */
    public enum DeployType {
        REBUSHU("热部署", "0"), LINSHIBUSHU("临时部署", "1"), CHANGGUIBUSHU("常规部署", "3");

        private String fieldName;
        private String fieldValue;

        DeployType(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public static String getFieldName(String fieldValue) {
            String fieldName = "";
            for (DeployType deployType : DeployType.values()) {
                if (deployType.fieldValue.equals(fieldValue)) {
                    fieldName = deployType.fieldName;
                }
            }
            return fieldName;
        }
    }

    /**
     * @description 部署说明
     * @date 2021/2/1
     */
    public enum DeployIllustration {
        WUBUSHUDONGZUO("无部署动作", "791113"), XINGUANKAIFA("信管开发", "791115"), XINGUANPEIZHI("信管配置", "791116"),
        JIFEICHENGXU("计费程序", "791100"), JIFEIPEIZHI("计费配置", "791101"), ZHANGCHUGUANLI("帐处程序", "791102");

        private String fieldName;
        private String fieldValue;

        DeployIllustration(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public static String getFieldName(String fieldValue) {
            String fieldName = "";
            for (DeployIllustration deployIllustration : DeployIllustration.values()) {
                if (deployIllustration.fieldValue.equals(fieldValue)) {
                    fieldName = deployIllustration.fieldName;
                }
            }
            return fieldName;
        }
    }
}
