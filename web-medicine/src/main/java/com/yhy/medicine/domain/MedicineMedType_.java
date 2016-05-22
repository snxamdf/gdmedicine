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
 * 药品类型表元模型类
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@StaticMetamodel(MedicineMedType.class)
public class MedicineMedType_ {

	public static volatile SingularAttribute<MedicineMedType, String> id;
	public static volatile SingularAttribute<MedicineMedType, String> name;
	public static volatile SingularAttribute<MedicineMedType, String> creater;
	public static volatile SingularAttribute<MedicineMedType, Date> created;
	public static volatile SingularAttribute<MedicineMedType, String> modifier;
	public static volatile SingularAttribute<MedicineMedType, Date> modified;
	public static volatile SingularAttribute<MedicineMedType, Integer> version;
	public static volatile SingularAttribute<MedicineMedType, Integer> deletion;
	public static volatile SingularAttribute<MedicineMedType, Integer> history;
	public static volatile SingularAttribute<MedicineMedType, String> memo;

}
