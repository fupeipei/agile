package com.yusys.agile.feign;

import com.yusys.agile.set.stage.dto.StageTemplateConfigDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "req-agile-impl")//url = "http://localhost:8060"
public interface IStageTemplateConfigApi {

    @GetMapping("/req/stageTemplateConfig/getStageTemplateConfigListByLevel")
    public List<StageTemplateConfigDTO> getDefaultStageTemplateConfigListByLevel(@RequestParam Byte level);
}
