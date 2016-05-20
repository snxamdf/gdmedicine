/* 
 * 
 *
 * 
 */
package com.yhy.core.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel注解定义.
 * 
 * @author YHY
 * @version 2013-10-11
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelFields {

	/**
	 * 子字段列表
	 */
	ExcelField[] fields();
}
