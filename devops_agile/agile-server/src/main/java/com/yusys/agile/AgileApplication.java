package com.yusys.agile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.yusys","com.yusys.portal.facade"})
@ComponentScan({"com.yusys.agile", "com.yusys.portal"})
@MapperScan(basePackages = {"com.yusys.agile.**.dao","com.yusys.portal.**.dao"})
public class AgileApplication {
	public static void main(String[] args) {
		// 发Email时，禁止附件文件名过长被截断
		System.setProperty("mail.mime.splitlongparameters", "false");

		SpringApplication.run(AgileApplication.class, args);
	}
	@Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(
            @Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
}
