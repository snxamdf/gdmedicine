/*
 * 
 *
 * 
 */
package com.gdm.core.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * core配置.
 * 
 * @author YHY
 * @version 2015-01-16
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-16
 */
@Configuration
@PropertySource(value = { "classpath:application.core.properties", "classpath:application.ver.properties" }, ignoreResourceNotFound = false)
class CoreConfig {

	@Value("${core.upload.max.size}")
	private int uploadMaxSize;

	// 这种方式进行配置与spring boot不兼容
	//@Bean
	//CommonsMultipartResolver commonsMultipartResolver() {
	//	CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
	//	commonsMultipartResolver.setMaxUploadSize(uploadMaxSize);
	//	commonsMultipartResolver.setResolveLazily(true);
	//	return commonsMultipartResolver;
	//}

	/**
	 * 文件上传配置.
	 * 
	 * @return
	 * @author YHY
	 * @version 2015-05-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-05-13
	 */
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(uploadMaxSize);
		factory.setMaxRequestSize(uploadMaxSize);
		return factory.createMultipartConfig();
	}

}
