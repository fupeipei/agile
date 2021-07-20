package com.yusys.agile.scheduleplan.service;

import com.yusys.agile.scheduleplan.dto.ScheduleplanDTO;
import com.yusys.agile.scheduleplan.dto.SystemInfoDTO;
import com.yusys.agile.scheduleplan.dto.ToDoListDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SchedulePlanService {

    void saveSchedulePlan(ScheduleplanDTO scheduleplanDTO);

    ScheduleplanDTO getSchedulePlan(Long epicId);

    List<ToDoListDTO> queryToDoList(String target ,Integer pageNum, Integer pageSize);

    void dealToDoList(Long relateId);
}
