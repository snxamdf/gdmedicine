package ${packageName}.${moduleId}.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdm.core.annotation.WebappEnv;
import com.gdm.core.constants.CTL;
import com.gdm.core.controller.<#if '${tableExtend}' == 'tree'>Tree<#else>Base</#if>Controller;
import com.gdm.core.dto.Module;
import com.gdm.core.service.BaseService;
import com.gdm.${moduleId}.constants.${projectId?upper_case};
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
@WebappEnv
@Controller
@RequestMapping(value = CTL.WEBAPP_PATH + "/${tableUrl}")
public class Webapp${className}Controller extends <#if '${tableExtend}' == 'tree'>Tree<#else>Base</#if>Controller<${className}, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Webapp${className}Controller.class);

	@Autowired
	private ${className}Service ${beanName}Service;

	@Override
	public BaseService<${className}, String> getService() {
		return ${beanName}Service;
	}

	@Override
	public Module<${className}> getModule() {
		return new Module<${className}>(${projectId?upper_case}.PROJECT, "${viewName}", CTL.WEBAPP, ${className}.class);
	}

}