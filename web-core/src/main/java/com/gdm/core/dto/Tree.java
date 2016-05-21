/*
 * 
 *
 * 
 */
package com.gdm.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 树结构字段父实体类.
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
public class Tree extends Sys {

	private static final long serialVersionUID = -797363534429020841L;

	private String code;
	private String name;
	private String fullname;
	private String genre;
	private Integer leaf;
	private Integer grade;
	private String ordinal;
	private String parentId;
	private String parentIds;
	private String parentCodes;
	private String parentNames;

}
