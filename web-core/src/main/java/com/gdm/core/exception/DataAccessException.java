/*
 * 
 *
 * 
 */
package com.gdm.core.exception;

/**
 * 数据库访问异常.
 * 
 * @author YHY
 * @version 2013-6-26
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2013-6-26
 */
public class DataAccessException extends SystemException {

	private static final long serialVersionUID = 3108425277661334216L;

	public DataAccessException() {
		super("数据库操作失败!");
	}

	public DataAccessException(Throwable cause) {
		super("数据库操作失败!", cause);
	}

}
