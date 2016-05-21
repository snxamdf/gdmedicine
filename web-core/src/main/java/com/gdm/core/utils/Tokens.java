/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重复提交工具类.
 * 
 * @author YHY
 * @version 2015-04-15
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-04-15
 */
public class Tokens {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Tokens.class);

	private static final String TOKEN_KEY = "session_token_key";
	public static final String TOKEN = "_token";

	/**
	 * 生成token新值并保持到session中.
	 * 
	 * @param request
	 * @return
	 * @author YHY
	 * @version 2015-04-15
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-04-15
	 */
	public static String generate(HttpServletRequest request) {
		String token = Identities.uuid();
		HttpSession session = request.getSession();
		synchronized (session) {
			@SuppressWarnings("unchecked")
			Set<String> tokens = (Set<String>) session.getAttribute(TOKEN_KEY);
			if (tokens == null) {
				tokens = new HashSet<String>();
			}
			tokens.add(token);
			session.setAttribute(TOKEN_KEY, tokens);
		}
		request.setAttribute(TOKEN, token);
		return token;
	}

	/**
	 * 清除session中的token值.
	 * 
	 * @param request
	 * @author YHY
	 * @version 2015-04-15
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-04-15
	 */
	public static boolean remove(HttpServletRequest request) {
		String token = request.getParameter(TOKEN);
		if (Strings.isBlank(token)) {
			return false;
		}
		HttpSession session = request.getSession(false);
		synchronized (session) {
			@SuppressWarnings("unchecked")
			Set<String> tokens = (Set<String>) session.getAttribute(TOKEN_KEY);
			if (tokens == null || tokens.size() < 1) {
				return false;
			}
			if (tokens.contains(token)) {
				tokens.remove(token);
				session.setAttribute(TOKEN_KEY, tokens);
			}
		}
		return true;
	}

	/**
	 * 判断token值是否有效.
	 * 
	 * @param request
	 * @return
	 * @author YHY
	 * @version 2015-04-15
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-04-15
	 */
	public static boolean valid(HttpServletRequest request) {
		String token = request.getParameter(TOKEN);
		if (Strings.isBlank(token)) {
			return false;
		}
		HttpSession session = request.getSession(false);
		synchronized (session) {
			@SuppressWarnings("unchecked")
			Set<String> tokens = (Set<String>) session.getAttribute(TOKEN_KEY);
			if (tokens == null || tokens.size() < 1) {
				return false;
			}
			if (!tokens.contains(token)) {
				return false;
			}
		}
		return true;
	}

}
