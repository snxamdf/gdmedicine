/*
 * 
 *
 * 
 */
package com.yhy.sys.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.yhy.core.domain.Sys;

/**
 * 医药管理Domain
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_sys_role")
public class Role extends Sys<String> {

	private static final long serialVersionUID = 1L;
	private String name;
	private String genre;
	private String code;

}
