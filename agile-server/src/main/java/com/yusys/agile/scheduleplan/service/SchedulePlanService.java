package com.yusys.agile.scheduleplan.service;

import com.yusys.agile.scheduleplan.dto.ScheduleplanDTO;
import com.yusys.agile.scheduleplan.dto.ToDoListDTO;

import java.util.List;

public interface SchedulePlanService {

    /**
     * 保存排期计划
     *
     * @param scheduleplanDTO
     */
    void saveSchedulePlan(ScheduleplanDTO scheduleplanDTO);


    /**
     * 根据EpicId 获取排期信息
     *
     * @param epicId
     * @return
     */
    ScheduleplanDTO getSchedulePlan(Long epicId);

    /**
     * 分页查询待办事项
     *
     * @param target
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ToDoListDTO> queryToDoList(String target ,Integer pageNum, Integer pageSize);

    /**
     * 处理待办事项
     *
     * @param relateId
     */
    void dealToDoList(Long relateId);

    /**
     * 发起排期计划
     *
     * @param epicId
     * @param state
     */
    void startSchedulePlan(Long epicId,Byte state);
}
