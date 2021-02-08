package com.yusys.agile.feign;

import com.yusys.agile.set.stage.dto.KanbanTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "req-agile-impl")//url = "http://localhost:8060"
public interface IKanbanTemplateApi {

    @GetMapping("/req/kanbanTemplate/getDefaultKanbanTemplate")
    public KanbanTemplateDTO getDefaultKanbanTemplateByTenantCode(@RequestParam("tenantCode") String tenantCode);

    @PostMapping("/req/kanbanTemplate/createDefaultKanbanTemplate")
    public Long createDefaultKanbanTemplate(@RequestBody KanbanTemplateDTO kanbanTemplateDTO);
}
