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
 * 用户表元模型类
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@StaticMetamodel(Users.class)
public class Users_ {

	public static volatile SingularAttribute<Users, String> id;
	public static volatile SingularAttribute<Users, String> loginname;
	public static volatile SingularAttribute<Users, String> passwd;
	public static volatile SingularAttribute<Users, String> uname;
	public static volatile SingularAttribute<Users, Integer> sex;
	public static volatile SingularAttribute<Users, String> chainid;
	public static volatile SingularAttribute<Users, String> creater;
	public static volatile SingularAttribute<Users, Date> created;
	public static volatile SingularAttribute<Users, String> modifier;
	public static volatile SingularAttribute<Users, Integer> version;
	public static volatile SingularAttribute<Users, Integer> deletion;
	public static volatile SingularAttribute<Users, Integer> history;
	public static volatile SingularAttribute<Users, String> memo;
	public static volatile SingularAttribute<Users, Date> modified;

}
