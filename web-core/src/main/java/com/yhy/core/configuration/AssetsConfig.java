/*
 * 
 *
 * 
 */
package com.yhy.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.yhy.core.constants.PROFILES;

/**
 * 静态资源配置.
 * 
 * @author YHY
 * @version 2016-03-28
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2016-03-28
 */
@Configuration
class AssetsConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		boolean isDevProfile = env.acceptsProfiles(PROFILES.DEV);
		if (isDevProfile) {
			registry.addResourceHandler("/assets/dev/**").addResourceLocations("file:/upload/dev/");
			registry.addResourceHandler("/assets/opr/**").addResourceLocations("file:/upload/operate/");
			registry.addResourceHandler("/assets/upl/**").addResourceLocations("file:/upload/archive/");
		}
	}

}
