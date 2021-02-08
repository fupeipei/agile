package com.yusys.agile.project.dto;

/**
 *    maxp
 * @Date 2020/5/7
 * @Description 迭代中所有故事数以及完成数
 */
public class ProjectFaultDTO {
    private Integer all;
    private Integer done;

    public ProjectFaultDTO() {
    }

    public ProjectFaultDTO(Integer all, Integer done) {
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
