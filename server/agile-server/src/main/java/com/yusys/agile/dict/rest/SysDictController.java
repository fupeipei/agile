package com.yusys.agile.dict.rest;

import com.yusys.agile.dict.domain.SysDictDetail;
import com.yusys.agile.dict.service.SysDictService;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @date 2021/2/1
 */
@RestController
@RequestMapping("/sysDict")
public class SysDictController {

    @Resource
    private SysDictService sysDictService;
    @RequestMapping("/getResponsiblePerson")
    public ControllerResponse getResponsiblePerson(Integer pageNum, Integer pageSize, String detailName, @RequestParam(value = "projectId", required = false) String projectId){
        List<SysDictDetail> sysDictDetailList;
        try {
            sysDictDetailList = sysDictService.getResponsiblePerson(pageNum,pageSize,detailName);
        }catch (Exception e){
            return ControllerResponse.fail("获取分页局方负责人列表数据异常");
        }
        return ControllerResponse.success(new PageInfo<>(sysDictDetailList));
    }
}
