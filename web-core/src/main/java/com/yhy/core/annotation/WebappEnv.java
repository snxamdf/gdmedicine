/*
 * 
 *
 * 
 */
package com.yhy.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

import com.yhy.core.conditional.EnvCondition;

/**
 * 用于控制只注入Webapp子项目相关的bean.
 * 
 * @author YHY
 * @version 2015-01-16
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Conditional(EnvCondition.class)
public @interface WebappEnv {

}
