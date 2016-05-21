/*
 * 
 *
 * 
 */
package com.yhy.sys.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 医药管理元模型类
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
@StaticMetamodel(Role.class)
public class Role_ {

	public static volatile SingularAttribute<Role, String> id;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SingularAttribute<Role, String> genre;
	public static volatile SingularAttribute<Role, String> creater;
	public static volatile SingularAttribute<Role, Date> created;
	public static volatile SingularAttribute<Role, String> modifier;
	public static volatile SingularAttribute<Role, Date> modified;
	public static volatile SingularAttribute<Role, Integer> version;
	public static volatile SingularAttribute<Role, Integer> deletion;
	public static volatile SingularAttribute<Role, Integer> history;
	public static volatile SingularAttribute<Role, String> memo;
	public static volatile SingularAttribute<Role, String> code;

}
