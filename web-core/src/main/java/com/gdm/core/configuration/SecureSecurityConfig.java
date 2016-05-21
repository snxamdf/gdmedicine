/*
 * 
 *
 * 
 */
package com.gdm.core.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.gdm.core.constants.CTL;
import com.gdm.core.constants.PROFILES;
import com.gdm.core.handler.LoginSuccessHandler;
import com.gdm.core.handler.LogoutSuccessHandler;
import com.gdm.core.utils.Passwords;

/**
 * WebSecurity配置.
 * 
 * @author YHY
 * @version 2014-12-11
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-12-11
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecureSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	private CsrfMatcher csrfRequestMatcher = new CsrfMatcher();

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(Passwords.getPasswordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// URL是否启用CSRF配置
		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher);
		// 关闭X-Frame-Options：DENY
		http.headers().frameOptions().disable();
		// 静态文件不需要权限控制
		http.authorizeRequests().antMatchers("/dologin", "/relogin", "/servlet/**", "/error", "/500", "/404").permitAll();
		http.authorizeRequests().antMatchers("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.gif").permitAll();
		http.authorizeRequests().antMatchers("/**/*.svg", "/**/*.eot", "/**/*.otf", "/**/*.ttf", "/**/*.woff").permitAll();
		http.authorizeRequests().antMatchers("/", "/login", "/index").permitAll();
		boolean isWebProfile = env.acceptsProfiles(PROFILES.WEB);

		if (isWebProfile) {
			http.authorizeRequests().antMatchers("/", "/login", "/index").permitAll();
			// http.authorizeRequests().antMatchers("/*", "/",
			// "/**").permitAll();
		}

		// 单元测试环境：所有url都有权限可以访问
		boolean isJunitProfile = env.acceptsProfiles(PROFILES.JUNIT);
		if (!isJunitProfile) {
			http.authorizeRequests().anyRequest().fullyAuthenticated();
		}

		boolean isBmsProfile = env.acceptsProfiles(PROFILES.BMS);
		String path = CTL.WEB_PATH;
		if (isBmsProfile) {
			path = CTL.BMS_PATH;
		}
		http.formLogin().successHandler(new LoginSuccessHandler()).loginPage(path + "/login").failureUrl(path + "/login?error").permitAll();
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher(path + "/logout")).logoutSuccessHandler(new LogoutSuccessHandler());
		http.exceptionHandling().accessDeniedPage(path + "/access?error");
	}

	/**
	 * URL是否启用CSRF配置类.
	 * 
	 * @author YHY
	 * @version 2015-06-19
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-06-19
	 */
	class CsrfMatcher implements RequestMatcher {
		public boolean matches(HttpServletRequest request) {
			// 单元测试环境：所有url都停用csrf
			boolean isJunitProfile = env.acceptsProfiles(PROFILES.JUNIT);
			if (isJunitProfile) {
				return false;
			}
			if ("GET".equals(request.getMethod())) {
				return false;
			}
			if (request.getRequestURL().indexOf("/apis/") != -1) {
				return false;
			}
			return true;
		}
	}

}
