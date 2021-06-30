package com.yusys.agile.issue.dto;

import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.portal.model.facade.dto.SsoSystemDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import lombok.Data;
import java.util.List;

/**
 *  @Description: 故事新建前置信息
 *  @author: zhao_yd
 *  @Date: 2021/5/26 1:58 下午
 *
 */

@Data
public class StoryCreatePrepInfoDTO {

    private List<SprintListDTO> sprintDTOS;

    private List<SsoSystemDTO> systemDTOS;

    private List<SsoUserDTO> userDTOS;


}
