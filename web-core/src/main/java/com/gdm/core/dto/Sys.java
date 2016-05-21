/*
 * 
 *
 * 
 */
package com.gdm.core.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统字段父实体类.
 * 
 * @author YHY
 * @version 2015-01-15
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Sys extends Pk {

	private static final long serialVersionUID = 7365426708367040889L;

	private String memo;
	private String creater;
	private Date created = new Date();
	private String modifier;
	private Date modified = new Date();
	private Long version = 0L;
	private Short deletion = 0;
	private Short history = 0;

}
