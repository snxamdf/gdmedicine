/* 
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FreeMarkers工具类.
 * 
 * @author YHY
 * @version 2015-01-15
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-15
 */
public abstract class FreeMarkers {

	/**
	 * 渲染模板字符串.
	 * 
	 * @param templateString
	 * @param model
	 * @return
	 * @author YHY
	 * @version 2015-01-15
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-15
	 */
	public static String renderString(String templateString, Map<String, ?> model) {
		try {
			StringWriter result = new StringWriter();
			Template t = new Template("name", new StringReader(templateString), new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
			t.process(model, result);
			return result.toString();
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 渲染Template文件.
	 * 
	 * @param template
	 * @param model
	 * @return
	 * @author YHY
	 * @version 2015-01-15
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-15
	 */
	public static String renderTemplate(Template template, Object model) {
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 渲染Template文件.
	 * 
	 * @param resourceLoaderClass
	 * @param basePackagePath
	 * @param tempateFile
	 * @param model
	 * @return
	 * @author YHY
	 * @version 2016-03-21
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2016-03-21
	 */
	public static String renderFile(Class<?> resourceLoaderClass, String basePackagePath, String tempateFile, Object model) {
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		cfg.setClassicCompatible(true); // 处理空值为空字符串
		cfg.setClassForTemplateLoading(resourceLoaderClass, basePackagePath);
		try {
			Template template = cfg.getTemplate(tempateFile);
			return FreeMarkers.renderTemplate(template, model);
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 创建默认配置，设定模板目录.
	 * 
	 * @param directory
	 * @return
	 * @throws IOException
	 * @author YHY
	 * @version 2015-01-15
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-15
	 */
	public static Configuration buildConfiguration(String directory) throws IOException {
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		Resource path = new DefaultResourceLoader().getResource(directory);
		cfg.setDirectoryForTemplateLoading(path.getFile());
		return cfg;
	}

}
