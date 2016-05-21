/*
 * 
 *
 * 
 */
package com.gdm.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import com.gdm.core.constants.CTL;
import com.gdm.core.constants.PROFILES;

/**
 * 错误页面配置.
 * 
 * @author YHY
 * @version 2015-01-27
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-27
 */
@Configuration
public class ErrorConfig implements EmbeddedServletContainerCustomizer {

	@Autowired
	private Environment env;

	/**
	 * @see org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer#customize(org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer)
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		String path = CTL.APIS_PATH;
		boolean isBmsProfile = env.acceptsProfiles(PROFILES.BMS);
		if (isBmsProfile) {
			path = CTL.BMS_PATH;
		}
		boolean isWebProfile = env.acceptsProfiles(PROFILES.WEB);
		if (isWebProfile) {
			path = CTL.WEB_PATH;
		}
		boolean isWebappProfile = env.acceptsProfiles(PROFILES.WEBAPP);
		if (isWebappProfile) {
			path = CTL.WEBAPP_PATH;
		}
		container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, path + "/403"));
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, path + "/404"));
		container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, path + "/500"));
		container.addErrorPages(new ErrorPage(path + "/err"));
	}

}
