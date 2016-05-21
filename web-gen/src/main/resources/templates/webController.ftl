/*
 * 
 *
 * 
 */
package ${packageName}.${moduleId}.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.core.annotation.WebEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.controller.<#if '${tableExtend}' == 'tree'>Tree<#else>Base</#if>Controller;
import com.yhy.core.dto.Module;
import com.yhy.core.service.BaseService;
import com.yhy.${moduleId}.constants.${projectId?upper_case};
import ${packageName}.${moduleId}.domain.${className};
import ${packageName}.${moduleId}.service.${className}Service;

/**
 * ${tableName}Controller.
 * 
 * @author ${author}
 * @version ${version}
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by ${author}
 * @updated at ${version}
 */
@WebEnv
@Controller
@RequestMapping(value = CTL.WEB_PATH + "/${tableUrl}")
public class Web${className}Controller extends <#if '${tableExtend}' == 'tree'>Tree<#else>Base</#if>Controller<${className}, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Web${className}Controller.class);

	@Autowired
	private ${className}Service ${beanName}Service;

	@Override
	public BaseService<${className}, String> getService() {
		return ${beanName}Service;
	}

	@Override
	public Module<${className}> getModule() {
		return new Module<${className}>(${projectId?upper_case}.PROJECT, "${viewName}", CTL.WEB, ${className}.class);
	}

}
