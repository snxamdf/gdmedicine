package com.gdm.core.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.envers.Audited;

/**
 * 用户实体类.
 * 
 * @author YHY
 * @version 2014-09-02
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
@Table(name = "t_users")
public class Users extends Sys<String> {

	private static final long serialVersionUID = 3788917486093777871L;

	private String loginname;
	private String passwd;
	private String uname;
	private Integer sex;
	private String chainid;

}
