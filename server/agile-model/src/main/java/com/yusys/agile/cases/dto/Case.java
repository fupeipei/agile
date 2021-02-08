package com.yusys.agile.cases.dto;

/**
 * 测试用例返回DTO
 *
 *
 * @create 2020-07-02 17:32
 */
public class Case {

    private Integer task_id;

    private String case_type_desc;

    private String case_name;

    private String exec_time;

    private String exec_user_name;

    private Integer exec_count;

    private String result_desc;
    /**
     * 1 多数据 0单数据
     */
    private Integer is_support_data;
    /**
     * 跳转链接
     */
    private String jumpUrl;
    /**
     * 用例id
     */
    private Integer case_id;

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getCase_type_desc() {
        return case_type_desc;
    }

    public void setCase_type_desc(String case_type_desc) {
        this.case_type_desc = case_type_desc;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public String getExec_time() {
        return exec_time;
    }

    public void setExec_time(String exec_time) {
        this.exec_time = exec_time;
    }

    public String getExec_user_name() {
        return exec_user_name;
    }

    public void setExec_user_name(String exec_user_name) {
        this.exec_user_name = exec_user_name;
    }

    public Integer getExec_count() {
        return exec_count;
    }

    public void setExec_count(Integer exec_count) {
        this.exec_count = exec_count;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public Integer getIs_support_data() {
        return is_support_data;
    }

    public void setIs_support_data(Integer is_support_data) {
        this.is_support_data = is_support_data;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public Integer getCase_id() {
        return case_id;
    }

    public void setCase_id(Integer case_id) {
        this.case_id = case_id;
    }

    @Override
    public String toString() {
        return "Case{" +
                "task_id=" + task_id +
                ", case_type_desc='" + case_type_desc + '\'' +
                ", case_name='" + case_name + '\'' +
                ", exec_time='" + exec_time + '\'' +
                ", exec_user_name='" + exec_user_name + '\'' +
                ", exec_count=" + exec_count +
                ", result_desc='" + result_desc + '\'' +
                ", is_support_data=" + is_support_data +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", case_id=" + case_id +
                '}';
    }
}