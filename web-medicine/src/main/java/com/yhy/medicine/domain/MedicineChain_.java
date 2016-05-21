/*
 * 
 *
 * 
 */
package com.yhy.medicine.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 医药管理表元模型类
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@StaticMetamodel(MedicineChain.class)
public class MedicineChain_ {

	public static volatile SingularAttribute<MedicineChain, String> id;
	public static volatile SingularAttribute<MedicineChain, String> name;
	public static volatile SingularAttribute<MedicineChain, String> creater;
	public static volatile SingularAttribute<MedicineChain, Date> created;
	public static volatile SingularAttribute<MedicineChain, String> modifier;
	public static volatile SingularAttribute<MedicineChain, Date> modified;
	public static volatile SingularAttribute<MedicineChain, Integer> version;
	public static volatile SingularAttribute<MedicineChain, Integer> deletion;
	public static volatile SingularAttribute<MedicineChain, Integer> history;
	public static volatile SingularAttribute<MedicineChain, String> memo;

}
