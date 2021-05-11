package com.yusys.agile.sprintv3.service;

import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:48
 */
public interface Sprintv3Service {
    /**
     * 条件分页-查询列表
     * @author zhaofeng
     * @date 2021/5/11 16:33
     * @param dto
     * @param security
     */
    List<SprintListDTO> listSprint(SprintQueryDTO dto, SecurityDTO security);
}
