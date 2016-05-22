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
 * 药品管理表元模型类
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@StaticMetamodel(MedicineMed.class)
public class MedicineMed_ {

	public static volatile SingularAttribute<MedicineMed, String> id;
	public static volatile SingularAttribute<MedicineMed, String> name;
	public static volatile SingularAttribute<MedicineMed, String> creater;
	public static volatile SingularAttribute<MedicineMed, Date> created;
	public static volatile SingularAttribute<MedicineMed, String> modifier;
	public static volatile SingularAttribute<MedicineMed, Date> modified;
	public static volatile SingularAttribute<MedicineMed, Integer> version;
	public static volatile SingularAttribute<MedicineMed, Integer> deletion;
	public static volatile SingularAttribute<MedicineMed, Integer> history;
	public static volatile SingularAttribute<MedicineMed, String> memo;
	public static volatile SingularAttribute<MedicineMed, String> typeId;
	public static volatile SingularAttribute<MedicineMed, String> spec;
	public static volatile SingularAttribute<MedicineMed, Double> price;
	public static volatile SingularAttribute<MedicineMed, String> brand;
	public static volatile SingularAttribute<MedicineMed, String> vendor;
	public static volatile SingularAttribute<MedicineMed, String> mstate;
	public static volatile SingularAttribute<MedicineMed, String> barcode;
	public static volatile SingularAttribute<MedicineMed, Integer> amount;
	public static volatile SingularAttribute<MedicineMed, MedicineMedType> type;

}
