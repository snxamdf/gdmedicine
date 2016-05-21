/* 
 * 
 *
 * 
 */
package com.gdm.core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.gdm.core.utils.Strings;

/**
 * 网站来路是否是本站过滤.
 * 
 * @author YHY
 * @version 2015-12-10
 */
public class RefererFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(RefererFilter.class);

	private String[] urlExcludes = {};
	private boolean isUrlExcludes = true;

	/**
	 * 此程序块主要用来解决网站来路非本站的过滤功能
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (isUrlExcludes) {
			// 排除url匹配判断
			PathMatcher matcher = new AntPathMatcher();
			String servletPath = req.getServletPath();
			for (int i = 0; i < urlExcludes.length; i++) {
				boolean result = matcher.match(urlExcludes[i], servletPath);
				if (result) {
					chain.doFilter(request, response);
					return;
				}
			}
		}

		String prefix = req.getScheme() + "://" + req.getServerName();
		String referer = req.getHeader("Referer");
		if (Strings.isNotBlank(referer) && !referer.startsWith(prefix)) {
			logger.error("Referer: " + referer + " -> " + req.getRequestURI());
			res.setContentType("text/html");
			res.setCharacterEncoding("utf-8");
			String requestType = req.getHeader("X-Requested-With");
			if (requestType != null && "XMLHttpRequest".equals(requestType)) { // ajax请求
				PrintWriter out = res.getWriter();
				out.print("对不起，您没有权限访问该页面!");
				res.setStatus(403);
			} else {
				PrintWriter out = res.getWriter();
				out.print("<script language='javascript'>alert(\"对不起，您没有权限访问该页面!\");" + "window.history.go(-1);</script>");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		if (config.getInitParameter("urlExcludes").length() < 1) {
			isUrlExcludes = false;
		} else {
			this.urlExcludes = config.getInitParameter("urlExcludes").split(",");
		}
	}

	public void destroy() {
	}

}
