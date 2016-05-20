/* 
 * 
 *
 * 
 */
package com.yhy.core.exception;

/**
 * 系统类公用的Exception.
 * 
 * @author YHY
 * @version 2013-5-25
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -8878901621245468981L;

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

}
