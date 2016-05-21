<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${moduleId}.mapper.${className}Mapper">
	<resultMap id="baseResultMap" type="${packageName}.${moduleId}.domain.${className}" extends="com.gdm.core.mapper.BaseMapper.<#if '${tableExtend}' == 'tree'>tree<#else>sys</#if>ResultMap">
		<#list columns as column>
		<#if column.getIsSys('${tableExtend}') == 'false'>
		<result column="${column.name}" property="${column.beanName}" jdbcType="${column.jdbcType}" />
		</#if>
		</#list>
	</resultMap>

	<select id="findById" parameterType="string" resultMap="baseResultMap">
		select * from ${tableId} where id = ${r"#{id}"}
	</select>
</mapper>
