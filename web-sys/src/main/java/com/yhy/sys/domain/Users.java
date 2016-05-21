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
 * 用户表Domain
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_sys_users")
public class Users extends Sys<String> {

	private static final long serialVersionUID = 1L;
	private String loginname;
	private String passwd;
	private String uname;
	private Integer sex;
	private String chainid;

}
