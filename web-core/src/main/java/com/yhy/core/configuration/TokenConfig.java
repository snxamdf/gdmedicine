/*
 * 
 *
 * 
 */
package com.yhy.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.yhy.core.interceptor.TokenInterceptor;

/**
 * 重复提交配置.
 * 
 * @author YHY
 * @version 2015-04-15
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-04-15
 */
@Configuration
class TokenConfig extends WebMvcConfigurerAdapter {

	@Autowired
	TokenInterceptor tokenInterceptor;

	@Bean
	public TokenInterceptor tokenInterceptor() {
		return new TokenInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor).excludePathPatterns(
				"/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.gif");
	}

}
