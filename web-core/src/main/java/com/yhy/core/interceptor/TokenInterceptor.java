/*
 * 
 *
 * 
 */
package com.yhy.core.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yhy.core.annotation.Token;
import com.yhy.core.utils.Results;
import com.yhy.core.utils.Tokens;

/**
 * 重复提交拦截器.
 * 
 * @author YHY
 * @version 2015-04-15
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-04-15
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

	/**
	 * 在业务处理器处理请求之前被调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean isGenerateSession = annotation.generate();
				if (isGenerateSession) {
					Tokens.generate(request);
				}
				boolean isRemoveSession = annotation.remove();
				if (isRemoveSession) {
					if (!Tokens.valid(request)) {
						String token = request.getParameter(Tokens.TOKEN);
						String url = request.getRequestURI();
						String sessionId = request.getRequestedSessionId();
						logger.error("{_token:'" + token + "',error:'重复提交错误',url:'" + url + "',sessionId:'" + sessionId + "'}");
						String requestType = request.getHeader("X-Requested-With");
						if (requestType != null && "XMLHttpRequest".equals(requestType)) { // ajax请求
							PrintWriter out = response.getWriter();
							out.print(Results.fault(403, "token error", "").toJson());
							response.setStatus(403);
						} else {
							// 重复提交拒绝处理，跳转到403页面
							response.sendRedirect(request.getContextPath() + "/403");
						}
						return false;
					}
					Tokens.remove(request);
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
	}

	/**
	 * 完全处理完请求后被调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception mav) throws Exception {
	}

}
