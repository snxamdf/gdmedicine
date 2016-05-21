/*
 * 
 *
 * 
 */
package com.gdm.core.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * 登录成功后执行.
 * 
 * @author YHY
 * @version 2015-02-03
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-02-03
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
