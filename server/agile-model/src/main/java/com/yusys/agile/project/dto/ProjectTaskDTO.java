package com.yusys.agile.project.dto;
/**
 *    maxp
 * @Date 2020/5/7
 * @Description 迭代中所有任务数以及完成数
 */
public class ProjectTaskDTO {
    private Integer all;
    private Integer done;

    public ProjectTaskDTO() {
    }

    public ProjectTaskDTO(Integer all, Integer done) {
        this.all = all;
        this.done = done;
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }
}
