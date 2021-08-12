package com.yusys.agile.issue.rest;

import com.yusys.agile.issue.dto.EpicReviewDto;
import com.yusys.agile.issue.dto.EpicReviewRecordDto;
import com.yusys.agile.issue.service.EpicReviewRecordService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: EpicReviewRecordController
 * @Description:业务需求评审
 * @Author: fupp1
 * @CreateDate: 2021/08/12 14:31
 */
@RestController
@RequestMapping("/epicReview")
@Slf4j
public class EpicReviewRecordController {

    @Autowired
    private EpicReviewRecordService epicReviewRecordService;
    /**
     * @Author fupp1
     * @Description 业务需求评审评论
     * @Date 14:41 2021/8/12
     * @Param [epicReviewRDto, securityDTO]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @PostMapping("/saveReview")
    public ControllerResponse saveReview(@RequestBody @Valid EpicReviewDto epicReviewRDto, SecurityDTO securityDTO){
        try {
            epicReviewRecordService.saveReview(epicReviewRDto,securityDTO);
        } catch (Exception e) {
            log.error("业务需求评审评论失败：{}", e);
            return ControllerResponse.fail("业务需求评审评论失败：" + e.getMessage());
        }
        return ControllerResponse.success("业务需求评审评论成功！");
    }

    /**
     * @Author fupp1
     * @Description 获取评论列表
     * @Date 14:51 2021/8/12
     * @Param [issueId]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @GetMapping("/listEpicReview/{issueId}")
    public ControllerResponse listEpicReview(@PathVariable Long issueId){
        List<EpicReviewRecordDto> result = null;
        try {
            result= epicReviewRecordService.listEpicReview(issueId);
        } catch (Exception e) {
            log.error("获取评论列表失败：{}", e);
            return ControllerResponse.fail("获取评论列表失败：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * @Author fupp1
     * @Description 删除业务需求评审
     * @Date 15:15 2021/8/12
     * @Param [recordId, securityDTO]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @DeleteMapping("removeEpicReviewRecord/{recordId}")
    public ControllerResponse removeEpicReviewRecord(@PathVariable Long recordId,SecurityDTO securityDTO){
        try {
            epicReviewRecordService.removeEpicReviewRecord(recordId,securityDTO);
        } catch (Exception e) {
            log.error("删除业务需求评审失败：{}", e);
            return ControllerResponse.fail("删除业务需求评审失败：" + e.getMessage());
        }
        return ControllerResponse.success("删除业务需求评审成功！");
    }

}
