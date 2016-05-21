/*
 * 
 *
 * 
 */
package com.gdm.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import com.gdm.core.annotation.IgnoreAudited;
import com.gdm.core.secure.UserDetail;
import com.gdm.core.utils.Auths;
import com.gdm.core.utils.Cglibs;

/**
 * 系统字段父实体类.
 * 
 * @author YHY
 * @version 2014-07-17
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-07-17
 */
@Data
@Audited
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class Sys<ID extends Serializable> extends Pk<ID> {

	private static final long serialVersionUID = 4661729833892942624L;

	/** 不是逻辑删除数据 */
	public static final short DELETION_NO = 0;
	/** 是逻辑删除数据 */
	public static final short DELETION_YES = 1;
	/** 不是人造数据 */
	public static final short ARTIFICAL_NO = 0;
	/** 是人造数据 */
	public static final short ARTIFICAL_YES = 1;
	/** 不是历史数据 */
	public static final short HISTORY_NO = 0;
	/** 是历史数据 */
	public static final short HISTORY_YES = 1;
	/** 启用 */
	public static final short USEABLE_YES = 1;
	/** 禁用 */
	public static final short USEABLE_NO = 0;

	protected String memo;// 备注
	protected ID creater;// 创建人员
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date created = new Date();// 创建日期
	protected ID modifier;// 修改人员
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date modified = new Date();// 修改日期
	protected Long version = 0L;// //乐观锁
	protected Short deletion = 0;// 删除标志
	protected Short history = 0;// 历史数据

	@Transient
	protected Date filterStartDate; // 开始日期：用于查询
	@Transient
	protected Date filterStopDate; // 截止日期：用于查询
	@Transient
	protected Date filterBeginDate; // 开始日期：用于查询
	@Transient
	protected Date filterEndDate; // 截止日期：用于查询
	@Transient
	protected Double filterMinAmount; // 最小金额：用于查询
	@Transient
	protected Double filterMaxAmount; // 最大金额：用于查询

	@PrePersist
	@SuppressWarnings("unchecked")
	public void prePersist() {
		Class<?> targetClazz = Cglibs.getTarget(this.getClass());
		boolean ignoreAudited = targetClazz.isAnnotationPresent(IgnoreAudited.class);
		if (!ignoreAudited) { // 如果是历史备份表，不修改操作人信息
			if (Auths.isLogin()) {
				UserDetail userDetail = Auths.getUserDetail();

				creater = (ID) userDetail.getId();
				modifier = (ID) userDetail.getId();
			} else {

				creater = (ID) "anonymousUser";
				modifier = (ID) "anonymousUser";
			}
		}
		if (created == null) {
			created = new Date();
		}
		if (modified == null) {
			modified = new Date();
		}
	}

	@PreUpdate
	@SuppressWarnings("unchecked")
	public void preUpdate() {
		Class<?> targetClazz = Cglibs.getTarget(this.getClass());
		boolean ignoreAudited = targetClazz.isAnnotationPresent(IgnoreAudited.class);
		if (!ignoreAudited) { // 如果是历史备份表，不修改操作人信息
			if (Auths.isLogin()) {
				UserDetail userDetail = Auths.getUserDetail();
				modifier = (ID) userDetail.getId();
			} else {
				modifier = (ID) "anonymousUser";
			}
			modified = new Date();
		}
	}

}
