/*
 * 
 *
 * 
 */
package com.gdm.core.thymeleaf;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

import com.google.common.collect.Maps;

/**
 * thymeleaf对应静态文件服务器文件加载版本号Ver的Dialect类.
 * 
 * @author YHY
 * @version 2015-03-17
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-03-17
 */
public class VerDialect extends AbstractDialect implements IExpressionEnhancingDialect {

	public static final String DEFAULT_PREFIX = "ver";
	public static final String CTL_EXPRESSION_OBJECT_NAME = "ver";

	@Override
	public String getPrefix() {
		return DEFAULT_PREFIX;
	}

	@Override
	public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
		final Map<String, Object> objects = Maps.newHashMap();
		final IContext context = processingContext.getContext();
		final IWebContext webContext = (context instanceof IWebContext ? (IWebContext) context : null);
		if (webContext != null) {
			final ServletContext servletContext = webContext.getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			String ver = ctx.getEnvironment().getProperty("core.assets.statics.ver");
			objects.put(CTL_EXPRESSION_OBJECT_NAME, ver);
		}
		return objects;
	}

}
