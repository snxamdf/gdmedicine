/*
 * 
 *
 * 
 */
package com.gdm.medicine.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 用户表元模型类
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
@StaticMetamodel(MedicineUsers.class)
public class MedicineUsers_ {

	public static volatile SingularAttribute<MedicineUsers, String> id;
	public static volatile SingularAttribute<MedicineUsers, String> loginname;
	public static volatile SingularAttribute<MedicineUsers, String> passwd;
	public static volatile SingularAttribute<MedicineUsers, String> uname;
	public static volatile SingularAttribute<MedicineUsers, Integer> sex;
	public static volatile SingularAttribute<MedicineUsers, String> chainid;
	public static volatile SingularAttribute<MedicineUsers, String> creater;
	public static volatile SingularAttribute<MedicineUsers, Date> created;
	public static volatile SingularAttribute<MedicineUsers, String> modifier;
	public static volatile SingularAttribute<MedicineUsers, Integer> version;
	public static volatile SingularAttribute<MedicineUsers, Integer> deletion;
	public static volatile SingularAttribute<MedicineUsers, Integer> history;
	public static volatile SingularAttribute<MedicineUsers, String> memo;
	public static volatile SingularAttribute<MedicineUsers, Date> modified;

}
