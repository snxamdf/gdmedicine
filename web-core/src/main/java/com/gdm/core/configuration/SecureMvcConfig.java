/*
 * 
 *
 * 
 */
package com.gdm.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.gdm.core.constants.CTL;
import com.gdm.core.constants.PROFILES;
import com.gdm.core.constants.SYMBOL;

/**
 * WebMvc配置.
 * 
 * @author YHY
 * @version 2014-12-11
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-12-11
 */
@Configuration
class SecureMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String project = CTL.APIS;
		String login = "login";
		String path = CTL.APIS_PATH;
		String prefix = CTL.APIS;
		boolean isBmsProfile = env.acceptsProfiles(PROFILES.BMS);
		if (isBmsProfile) {
			project = "secure";
			login = "login";
			path = CTL.BMS_PATH;
			prefix = CTL.BMS;
		}
		boolean isWebProfile = env.acceptsProfiles(PROFILES.WEB);
		if (isWebProfile) {
			project = "sys";
			login = "login2";
			path = CTL.WEB_PATH;
			prefix = CTL.WEB;
		}
		boolean isWebappProfile = env.acceptsProfiles(PROFILES.WEBAPP);
		if (isWebappProfile) {
			project = CTL.WEBAPP;
			login = "login";
			path = CTL.WEBAPP_PATH;
			prefix = CTL.WEBAPP;
		}
		registry.addViewController(path + SYMBOL.SLANT + "login").setViewName(project + SYMBOL.SLANT + prefix + SYMBOL.DOT + login);
		registry.addViewController(path + SYMBOL.SLANT + "access").setViewName(project + SYMBOL.SLANT + prefix + SYMBOL.DOT + "access");
		registry.addViewController(path + SYMBOL.SLANT + "err").setViewName(project + SYMBOL.SLANT + prefix + SYMBOL.DOT + "error");
		registry.addViewController(path + SYMBOL.SLANT + "403").setViewName(project + SYMBOL.SLANT + prefix + SYMBOL.DOT + "403");
		registry.addViewController(path + SYMBOL.SLANT + "404").setViewName(project + SYMBOL.SLANT + prefix + SYMBOL.DOT + "404");
		registry.addViewController(path + SYMBOL.SLANT + "500").setViewName(project + SYMBOL.SLANT + prefix + SYMBOL.DOT + "500");
	}

}
