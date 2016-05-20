/*
 * 
 *
 * 
 */
package com.yhy.core.utils;

import com.yhy.core.dto.Result;

/**
 * 接口返回值工具类.
 * 
 * @author YHY
 * @version 2015-01-11
 * @----------------------------------------------------------------------------------------
 * @updated 创建.
 * @updated by YHY
 * @updated at 2015-01-11
 */
public abstract class Results {

	public static <T> Result<T> success(T data) {
		return success(0, data);
	}

	public static <T> Result<T> success(int status, T data) {
		return success(status, "", data);
	}

	public static <T> Result<T> success(String token, int status, T data) {
		return success(token, status, "", data);
	}

	public static <T> Result<T> success(String message, T data) {
		return success(0, message, data);
	}

	public static <T> Result<T> success(String token, String message, T data) {
		return success(token, 0, message, data);
	}

	public static <T> Result<T> success(int status, String message, T data) {
		return success("", status, message, data);
	}

	public static <T> Result<T> success(String token, int status, String message, T data) {
		return build(true, token, status, message, data);
	}

	public static <T> Result<T> fault(T data) {
		return fault(0, data);
	}

	public static <T> Result<T> fault(int status, T data) {
		return fault(status, "", data);
	}

	public static <T> Result<T> fault(String token, int status, T data) {
		return fault(token, status, "", data);
	}

	public static <T> Result<T> fault(String message, T data) {
		return fault(0, message, data);
	}

	public static <T> Result<T> fault(String token, String message, T data) {
		return fault(token, 0, message, data);
	}

	public static <T> Result<T> fault(int status, String message, T data) {
		return fault("", status, message, data);
	}

	public static <T> Result<T> fault(String token, int status, String message, T data) {
		return build(false, token, status, message, data);
	}

	private static <T> Result<T> build(boolean success, String token, int status, String message, T data) {
		Result<T> result = new Result<T>();
		result.setSuccess(success);
		result.setToken(token);
		result.setStatus(status);
		result.setMessage(message);
		result.setData(data);
		return result;
	}

}
