/*
 * 
 *
 * 
 */
package com.gdm.core.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 树结构字段父实体类.
 * 
 * @author YHY
 * @version 2014-07-17
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-07-17
 */
@Data
@Audited
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class Tree<ID extends Serializable> extends Sys<ID> {

	private static final long serialVersionUID = 44003797685417623L;

	/** 根节点标识 */
	public static final String ROOT = "root";
	/** ztree用到的parentId名称 */
	public static final String P_ID = "pId";

	/** 是叶子节点 */
	public static final Integer LEAF_YES = 1;
	/** 不是叶子节点 */
	public static final Integer LEAF_NO = 0;

	protected String code;
	@NotEmpty(message = "名称不能为空.")
	protected String name;
	protected String fullname;
	protected String genre;
	protected Integer leaf;
	protected Integer grade;
	@NotEmpty(message = "排序不能为空.")
	protected String ordinal;
	protected ID parentId;
	protected String parentIds;
	protected String parentCodes;
	protected String parentNames;

}
