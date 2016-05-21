/*
 * 
 *
 * 
 */
package ${packageName}.${moduleId}.domain;
<#if 0 < countDatetime>

import java.util.Date;
</#if>

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * ${tableName}元模型类
 * 
 * @author ${author}
 * @version ${version}
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by ${author}
 * @updated at ${version}
 */
@StaticMetamodel(${className}.class)
public class ${className}_ {

	<#list columns as column>
	public static volatile SingularAttribute<${className}, ${column.javaType}> ${column.beanName};
	</#list>

}
