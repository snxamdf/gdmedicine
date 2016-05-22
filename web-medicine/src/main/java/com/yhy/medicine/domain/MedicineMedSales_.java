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
 * 药品销售记录表元模型类
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@StaticMetamodel(MedicineMedSales.class)
public class MedicineMedSales_ {

	public static volatile SingularAttribute<MedicineMedSales, String> id;
	public static volatile SingularAttribute<MedicineMedSales, String> creater;
	public static volatile SingularAttribute<MedicineMedSales, Date> created;
	public static volatile SingularAttribute<MedicineMedSales, String> modifier;
	public static volatile SingularAttribute<MedicineMedSales, Date> modified;
	public static volatile SingularAttribute<MedicineMedSales, Integer> version;
	public static volatile SingularAttribute<MedicineMedSales, Integer> deletion;
	public static volatile SingularAttribute<MedicineMedSales, Integer> history;
	public static volatile SingularAttribute<MedicineMedSales, String> memo;
	public static volatile SingularAttribute<MedicineMedSales, String> medId;
	public static volatile SingularAttribute<MedicineMedSales, String> barcode;
	public static volatile SingularAttribute<MedicineMedSales, Integer> amount;
	public static volatile SingularAttribute<MedicineMedSales, MedicineMed> med;

}
