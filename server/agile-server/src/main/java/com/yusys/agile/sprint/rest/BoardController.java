package com.yusys.agile.sprint.rest;


import com.google.common.base.Preconditions;

import com.yusys.agile.sprint.dto.BoardStoryParam;
import com.yusys.agile.sprint.service.BoardService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *    maxp
 * @Date 2020/4/30
 */
@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    /**
     *    maxp
     * @Date 2020/4/30
     * @Description 看板搜索故事和任务
      * @param param
     * @Return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @PostMapping("/board/searchStorysAndTasks")
    public ControllerResponse getStorysAndTasks(@RequestBody BoardStoryParam param){
        Long sprintId = param.getSprintId();
        Preconditions.checkArgument(null != sprintId, "迭代id为空");
        return ControllerResponse.success( boardService.getStoryWithTask(param));
    }
}
