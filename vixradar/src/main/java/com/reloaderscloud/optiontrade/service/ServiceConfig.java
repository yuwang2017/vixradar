package com.reloaderscloud.optiontrade.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class ServiceConfig {

	@Bean
	public OptionService optionService() {
		return new OptionServiceImpl();
	}
	
	@Bean
	public CloudService cloudService() {
		return new CloudServiceImpl();
	}
}
