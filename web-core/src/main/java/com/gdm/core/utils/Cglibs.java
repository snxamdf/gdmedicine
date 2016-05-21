/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

/**
 * CGLIB工具类.
 * 
 * @author YHY
 * @version 2013-8-19
 * @----------------------------------------------------------------------------------------
 * @updated 创建.
 * @updated by YHY
 * @updated at 2013-8-19
 */
public abstract class Cglibs {

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	/**
	 * 获取CGLIB代理类对应的原始类.
	 * 
	 * @return
	 * @author YHY
	 * @version 2013-8-19
	 * @----------------------------------------------------------------------------------------
	 * @updated 创建.
	 * @updated by YHY
	 * @updated at 2013-8-19
	 */
	public static Class<?> getTarget(Class<?> clazz) {
		String clazzName = clazz.getSimpleName();
		// 判断是否是cglib代理类
		if (Strings.contains(clazzName, CGLIB_CLASS_SEPARATOR)) {
			return clazz.getSuperclass();
		}
		return clazz;
	}

}
