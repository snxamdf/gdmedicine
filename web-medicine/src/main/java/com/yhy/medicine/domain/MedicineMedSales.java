/*
 * 
 *
 * 
 */
package com.yhy.medicine.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.yhy.core.domain.Sys;

/**
 * 药品销售记录表Domain
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_medicine_med_sales")
public class MedicineMedSales extends Sys<String> {

	private static final long serialVersionUID = 1L;
	private String medId;
	private Integer amount;

	@OneToOne(targetEntity = MedicineMed.class, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "medId", referencedColumnName = "id", insertable = false, updatable = false)
	private MedicineMed med;

	private String barcode;

}
