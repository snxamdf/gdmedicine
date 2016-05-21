/*
 * 
 *
 * 
 */
package com.gdm.core.configuration;

import java.util.Arrays;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.gdm.core.filter.IllegalCharacterFilter;
import com.gdm.core.servlet.ValidcodeServlet;

/**
 * web.xml配置.
 * 
 * @author YHY
 * @version 2015-02-09
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-02-09
 */
@Configuration
class WebXmlConfig {

	@Value("${core.illegal.character}")
	private String illegalCharacter;

	@Autowired
	private ValidcodeServlet validcodeServlet;

	/**
	 * 字符集过滤.
	 * 
	 * @return
	 * @author YHY
	 * @version 2015-02-09
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-02-09
	 */
	@Bean
	public FilterRegistrationBean characterEncodingFilterRegistration() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("utf-8");
		characterEncodingFilter.setForceEncoding(true);
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(characterEncodingFilter);
		registration.setUrlPatterns(Arrays.asList("/*"));
		registration.setName("characterEncodingFilter");
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		return registration;
	}

	/**
	 * 非法字符过滤.
	 * 
	 * @return
	 * @author YHY
	 * @version 2015-02-09
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-02-09
	 */
	@Bean
	public FilterRegistrationBean illegalCharacterFilterRegistration() {
		IllegalCharacterFilter illegalCharacterFilter = new IllegalCharacterFilter();
		illegalCharacterFilter.setCharacterParams(new String[] { illegalCharacter });
		illegalCharacterFilter.setUrlExcludes(new String[] { "/static/**", "/**/*.css", "/**/*.js", "/sys/conf/**", "/subj/balance/uptSubjBal/**", "/subj/balance/setSaveSubjBal/**", "/cms/illegal/char/check" });
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(illegalCharacterFilter);
		registration.setUrlPatterns(Arrays.asList("/*"));
		registration.setName("illegalCharacterFilter");
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		return registration;
	}

	/**
	 * 生成随机验证码.
	 * 
	 * @return
	 * @author YHY
	 * @version 2015-02-09
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-02-09
	 */
	@Bean
	public ServletRegistrationBean validcodeServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(validcodeServlet);
		registration.addUrlMappings("/servlet/validcodeServlet");
		return registration;
	}

	@Bean
	public ValidcodeServlet validcodeServlet() {
		ValidcodeServlet validcodeServlet = new ValidcodeServlet();
		return validcodeServlet;
	}

}
