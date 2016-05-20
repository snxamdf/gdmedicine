/*
 * 
 *
 * 
 */
package com.yhy.core.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 主键父实体类.
 * 
 * @author YHY
 * @version 2015-01-15
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-15
 */
@Data
public class Pk implements Serializable {

	private static final long serialVersionUID = -4266143861287690662L;

	private String id;

}
