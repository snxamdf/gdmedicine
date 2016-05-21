/*
 * 
 *
 * 
 */
package com.gdm.core.dto;

import lombok.Data;

import com.gdm.core.mapper.JsonMapper;

/**
 * 接口统一返回值.
 * 
 * @author YHY
 * @version 2013-6-25
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2013-6-25
 */
@Data
public class Result<T> {

	private boolean success;
	private String token;
	private int status;
	private String message;
	private T data;

	public String toJson() {
		JsonMapper jsonMapper = JsonMapper.alWaysMapper();
		return jsonMapper.toJson(this);
	}

}
