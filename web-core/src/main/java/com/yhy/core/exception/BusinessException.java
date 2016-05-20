/* 
 * 
 *
 * 
 */
package com.yhy.core.exception;

/**
 * 业务类公用的Exception.
 * 业务异常事务默认不会回滚，需要事务回滚使用：@Transactional(rollbackFor = { Exception.class })
 * 
 * @author YHY
 * @version 2013-5-25
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = -4034364884822187179L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}
