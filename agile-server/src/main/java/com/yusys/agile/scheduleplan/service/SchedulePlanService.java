package com.yusys.agile.scheduleplan.service;

import com.yusys.agile.scheduleplan.dto.ScheduleplanDTO;
import com.yusys.agile.scheduleplan.dto.SystemInfoDTO;
import com.yusys.agile.scheduleplan.dto.ToDoListDTO;

import java.util.List;

public interface SchedulePlanService {

    void saveSchedulePlan(ScheduleplanDTO scheduleplanDTO);

    ScheduleplanDTO getSchedulePlan(Long epicId);

    List<ToDoListDTO> queryToDoList(Long epicId,String title);

    void dealToDoList(Long relateId);
}
