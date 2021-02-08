package com.yusys.agile.feign;


import com.yusys.agile.cases.dto.CaseParamDTO;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 功能描述: 提供cicd接口
 *
 *
 * @date 2020/8/19
 * @return
 */
@FeignClient(name = "agile-server")
public interface IProjectApi {

    /**
     * 功能描述:根据项目id查询项目下所有未完成迭代
     *
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;<java.util.List < com.yusys.agile.sprint.dto.SprintDTO>>
     *
     * @date 2020/8/19
     */
    @GetMapping("/agile/sprint/listUnFinisherSprintsByProjectId")
    public ControllerResponse listSprintsByProjectId(@RequestParam("projectId") Long projectId,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "pageNum", required = false) Integer pageNum,
                                                     @RequestParam(name = "pageSize", required = false) Integer pageSize);


    /**
     * 功能描述:根据项目id查询项目下所有迭代
     *
     * @param projectId
     * @param name
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     *
     * @date 2020/11/5
     */
    @GetMapping("/agile/sprint/listAllSprintsByProjectId")
    public ControllerResponse listAllSprintsByProjectId(@RequestParam("projectId") Long projectId,
                                                        @RequestParam(name = "name", required = false) String name);

    /**
     * 功能描述:根据项目id查询项目下所有故事
     *
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;<java.util.List < com.yusys.agile.sprint.dto.SprintDTO>>
     *
     * @date 2020/8/19
     */
    @GetMapping("/agile/issue/listStorysByProjectId")
    public ControllerResponse listStorysByProjectId(@RequestParam("projectId") Long projectId,
                                                    @RequestParam(name = "pageNum") Integer pageNum,
                                                    @RequestParam(name = "pageSize") Integer pageSize);


    /**
     * @param sprintId
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询迭代下未完成的用户故事
     */
    @GetMapping("/agile/issue/getUnfinishedStorysBySprintId")
    public ControllerResponse getUnfinishedStorysBySprintId(@RequestParam("sprintId") Long sprintId,
                                                            @RequestParam(name = "pageNum") Integer pageNum,
                                                            @RequestParam(name = "pageSize") Integer pageSize);


    /**
     * 功能描述:提供cicd接口：根据故事id集合查询用例信息
     *
     * @param caseParamDTO
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     *
     * @date 2020/11/10
     */
    @PostMapping("/agile/case/listCaseByStoryIds")
    public ControllerResponse listCaseByStoryIds(@RequestBody CaseParamDTO caseParamDTO);


    /**
     * 功能描述:cicd接口-根据taskId查用例信息
     *
     * @date 2020/11/17
     * @param caseParamDTO
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @PostMapping("/agile/case/listCaseByTaskIds")
    public ControllerResponse listCaseByTaskIds(@RequestBody CaseParamDTO caseParamDTO);

    /**
     * 功能描述:提供cicd接口-根据任务id查询故事id
     *
     * @date 2020/11/17
     * @param taskIds
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @PostMapping("/issue/task/listStoryIdsByTaskIds")
    public ControllerResponse listStoryIdsByTaskIds(@RequestBody List<Long> taskIds) ;
}
