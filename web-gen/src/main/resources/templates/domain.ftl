/*
 * 
 *
 * 
 */
package ${packageName}.${moduleId}.domain;
<#if 0 < sumDatetime>

import java.util.Date;
</#if>

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
<#if 0 < sumDatetime>

import org.springframework.format.annotation.DateTimeFormat;
</#if>

import com.gdm.core.domain.<#if '${tableExtend}' == 'tree'>Tree<#else>Sys</#if>;

/**
 * ${tableName}Domain
 * 
 * @author ${author}
 * @version ${version}
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by ${author}
 * @updated at ${version}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "${tableId}")
public class ${className} extends <#if '${tableExtend}' == 'tree'>Tree<#else>Sys</#if><String> {

	private static final long serialVersionUID = 1L;
	<#list columns as column>
	<#if column.getIsSys('${tableExtend}') == 'false'>
	<#if column.types == 'datetime'>
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	</#if>
	private ${column.javaType} ${column.beanName};
	</#if>
	</#list>

}
