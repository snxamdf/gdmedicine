/* 
 * 
 *
 * 
 */
package com.yhy.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yhy.core.dto.Result;
import com.yhy.core.secure.UserDetail;

/**
 * 安全工具类.
 * 
 * @author YHY
 * @version 2015-01-13
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-13
 */
public abstract class Auths {

	/**
	 * 获取当前登录用户信息. 没有登录返回null.
	 * 
	 * @return 当前登录用户信息
	 * @author YHY
	 * @version 2015-01-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-13
	 */
	public static UserDetail getUserDetail() {
		if (isLogin()) {
			return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return null;
	}

	/**
	 * 判断是否登录.
	 * 
	 * @return 登录返回true，否则返回false
	 * @author YHY
	 * @version 2015-01-12
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-12
	 */
	public static boolean isLogin() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return false;
		}
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
			return false;
		}
		Object userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetail instanceof UserDetail) {
			return true;
		}
		return false;
	}

	/**
	 * 登录操作.
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 * @author YHY
	 * @version 2015-02-05
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-02-05
	 */
	public static Result<String> login(String username, String password, HttpServletRequest request) {
		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		try {
			ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			AuthenticationManager authenticationManager = ac.getBean(AuthenticationManager.class);
			Authentication authentication = authenticationManager.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = request.getSession();
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
			return Results.success("登录成功");
		} catch (AuthenticationException ex) {
			return Results.fault(ex.getMessage());
		}
	}

	/**
	 * 判断当前登录人员是否有某个操作权限. boolean isPermit = Auths.hasRole("sys:area:view");
	 * 
	 * @param authority
	 *            操作权限标识
	 * @return
	 * @author YHY
	 * @version 2015-03-05
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-03-05
	 */
	public static boolean hasRole(String authority) {
		if (!isLogin()) {
			return false;
		}
		if (Strings.isBlank(authority)) {
			return false;
		}
		UserDetail userDetail = getUserDetail();
		for (GrantedAuthority ga : userDetail.getAuthorities()) {
			if (authority.equals(ga.getAuthority())) {
				return true;
			}
		}
		return false;
	}

}
