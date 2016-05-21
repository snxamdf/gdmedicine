/*
 * 
 *
 * 
 */
package ${packageName}.${moduleId}.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdm.core.repository.BaseRepository;
import com.gdm.core.service.<#if '${tableExtend}' == 'tree'>Tree<#else>Base</#if>Service;
import ${packageName}.${moduleId}.domain.${className};
import ${packageName}.${moduleId}.repository.${className}Repository;

/**
 * ${tableName}Service.
 * 
 * @author ${author}
 * @version ${version}
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by ${author}
 * @updated at ${version}
 */
@Service
@Transactional
public class ${className}Service extends <#if '${tableExtend}' == 'tree'>Tree<#else>Base</#if>Service<${className}, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(${className}Service.class);

	@Autowired
	private ${className}Repository ${beanName}Repository;

	@Override
	public BaseRepository<${className}, String> getRepository() {
		return ${beanName}Repository;
	}

}
