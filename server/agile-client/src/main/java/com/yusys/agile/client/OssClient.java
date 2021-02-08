package com.yusys.agile.client;


import com.yusys.portal.util.oss.domain.FileResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "req-oss")
public interface OssClient {
	@PostMapping("/img/cut")
	FileResult imgCut(@RequestParam("fileName") String fileName, @RequestParam("x") double x,
					  @RequestParam("y") double y, @RequestParam("w") double w, @RequestParam("h") double h);
}