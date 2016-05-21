/* 
 * 
 *
 * 
 */
package com.gdm.core.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.gdm.core.utils.Encodes;
import com.gdm.core.utils.Strings;

/**
 * 非法字符过滤.
 * 
 * @author YHY
 * @version 2015-01-24
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-24
 */
public class IllegalCharacterFilter implements Filter {

	private static Logger logger = Logger.getLogger(IllegalCharacterFilter.class);

	private String[] characterParams = {};
	private String[] urlExcludes = {};
	private boolean isCharacterParams = true;
	private boolean isUrlExcludes = true;

	public void setCharacterParams(String[] characterParams) {
		this.characterParams = characterParams;
	}

	public void setUrlExcludes(String[] urlExcludes) {
		this.urlExcludes = urlExcludes;
	}

	/**
	 * 此程序块主要用来解决参数带非法字符等过滤功能
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

		boolean status = false;
		if (isCharacterParams) { // 过滤字符串为0个时 不对字符过滤
			Enumeration<?> params = request.getParameterNames();
			String param = "";
			while (params.hasMoreElements()) { // 获取请求参数名列表
				param = (String) params.nextElement();
				String[] values = request.getParameterValues(param);
				String paramValue = Strings.join(values);
				if (Strings.isBlank(paramValue)) { // 当参数值为空时忽略
					continue;
				}
				String decodeValue = Encodes.urlDecode(paramValue);
				decodeValue = Encodes.urlDecode(decodeValue); // 进行两次解码
				for (int i = 0; i < characterParams.length; i++) {
					String cp = characterParams[i]; // 获取过滤字符列表，逗号分隔
					if (Strings.isBlank(cp)) {
						continue;
					}
					String[] cps = Strings.split(cp, ","); // 把逗号分隔转换为数组，然后获取数组中的元素与参数值比较
					for (int y = 0; y < cps.length; y++) {
						if (Strings.isBlank(cps[y])) {
							continue;
						}
						if (Strings.containsIgnoreCase(paramValue, cps[y])) {
							logger.warn("输入的内容含有非法字符：" + cps[y]);
							status = true;
							break;
						}
						// 对内容进行解密再判断
						if (Strings.containsIgnoreCase(decodeValue, cps[y])) {
							logger.warn("输入的内容含有非法字符：" + cps[y]);
							status = true;
							break;
						}
					}
				}
				if (status) {
					break;
				}
			}
		}
		if (status) {
			res.setContentType("text/html");
			res.setCharacterEncoding("utf-8");
			String requestType = req.getHeader("X-Requested-With");
			if (requestType != null && "XMLHttpRequest".equals(requestType)) { // ajax请求
				PrintWriter out = res.getWriter();
				out.print("存在非法字符，请检查输入内容!");
				res.setStatus(441);
			} else {
				PrintWriter out = res.getWriter();
				out.print("<script language='javascript'>alert(\"存在非法字符，请检查输入内容!\");" + "window.history.go(-1);</script>");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

}
