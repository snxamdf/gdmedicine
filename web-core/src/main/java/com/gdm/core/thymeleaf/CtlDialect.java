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

import com.gdm.core.constants.CTL;
import com.gdm.core.constants.PROFILES;
import com.gdm.core.dto.Ctl;
import com.google.common.collect.Maps;

/**
 * thymeleaf对应CTL的Dialect类.
 * 
 * @author YHY
 * @version 2015-01-19
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-19
 */
public class CtlDialect extends AbstractDialect implements IExpressionEnhancingDialect {

	public static final String DEFAULT_PREFIX = "ctl";
	public static final String CTL_EXPRESSION_OBJECT_NAME = "ctl";

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
			boolean isBmsProfile = ctx.getEnvironment().acceptsProfiles(PROFILES.BMS);
			boolean isWebProfile = ctx.getEnvironment().acceptsProfiles(PROFILES.WEB);
			String apis = ctx.getEnvironment().getProperty("core.apis.path");
			String assets = ctx.getEnvironment().getProperty("core.assets.path");
			String bmsStatics = ctx.getEnvironment().getProperty("core.assets.statics.bms.path");
			String webStatics = ctx.getEnvironment().getProperty("core.assets.statics.web.path");
			String wapStatics = ctx.getEnvironment().getProperty("core.assets.statics.wap.path");
			String developers = ctx.getEnvironment().getProperty("core.assets.developers.path");
			String operations = ctx.getEnvironment().getProperty("core.assets.operations.path");
			String uploads = ctx.getEnvironment().getProperty("core.assets.uploads.path");
			Ctl ctl = new Ctl();
			ctl.setProfile(PROFILES.DEV); // 默认为开发环境
			ctl.setApis(apis);
			ctl.setAssets(assets);
			if (isBmsProfile) {
				ctl.setStatics(bmsStatics);
				ctl.setPath(CTL.BMS_PATH);
				ctl.setEnv(CTL.BMS);
			} else if (isWebProfile) {
				ctl.setStatics(webStatics);
				ctl.setPath(CTL.WEB_PATH);
				ctl.setEnv(CTL.WEB);
			} else {
				ctl.setStatics(wapStatics);
				ctl.setPath(CTL.WEBAPP_PATH);
				ctl.setEnv(CTL.WEBAPP);
			}
			boolean isProdProfile = ctx.getEnvironment().acceptsProfiles(PROFILES.PROD);
			if (isProdProfile) { // 生产环境
				ctl.setProfile(PROFILES.PROD);
			}
			boolean isTestProfile = ctx.getEnvironment().acceptsProfiles(PROFILES.TEST);
			if (isTestProfile) { // 测试环境
				ctl.setProfile(PROFILES.TEST);
			}
			ctl.setDevelopers(developers);
			ctl.setOperations(operations);
			ctl.setUploads(uploads);
			objects.put(CTL_EXPRESSION_OBJECT_NAME, ctl);
		}
		return objects;
	}

}
