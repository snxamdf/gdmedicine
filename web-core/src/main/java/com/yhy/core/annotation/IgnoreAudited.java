/* 
 * 
 *
 * 
 */
package com.yhy.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 不执行维护创建人和修改人操作。
 * 
 * @author YHY
 * @version 2015-02-09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface IgnoreAudited {
}
