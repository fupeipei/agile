package com.yusys.agile.sprintv3.service;

import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

import com.yusys.agile.sprint.dto.SprintDTO;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:48
 */
public interface Sprintv3Service {



    /**
     * @param sprintId
     * @Date 2021/5/11
     * @Description 查看迭代编辑页面
     * @Return com.yusys.agile.sprint.dto.SprintDTO
     */
    SprintDTO viewEdit(Long sprintId);

    /**
     * 条件分页-查询列表
     * @author zhaofeng
     * @date 2021/5/11 16:33
     * @param dto
     * @param security
     */
    List<SprintListDTO> listSprint(SprintQueryDTO dto, SecurityDTO security);

    /**
     * 新建迭代
     *
     * @param sprintV3DTO 迭代v3dto
     * @return {@link Long}
     */
    Long createSprint(SprintV3DTO sprintV3DTO);
}
