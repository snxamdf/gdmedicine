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
 * 用户角色关系表元模型类
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
@StaticMetamodel(UserRoles.class)
public class UserRoles_ {

	public static volatile SingularAttribute<UserRoles, String> id;
	public static volatile SingularAttribute<UserRoles, String> userId;
	public static volatile SingularAttribute<UserRoles, String> roleId;
	public static volatile SingularAttribute<UserRoles, String> creater;
	public static volatile SingularAttribute<UserRoles, Date> created;
	public static volatile SingularAttribute<UserRoles, String> modifier;
	public static volatile SingularAttribute<UserRoles, Date> modified;
	public static volatile SingularAttribute<UserRoles, Integer> version;
	public static volatile SingularAttribute<UserRoles, Integer> deletion;
	public static volatile SingularAttribute<UserRoles, Integer> history;
	public static volatile SingularAttribute<UserRoles, String> memo;

}
