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
 * 药品管理表Domain
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
@Table(name = "t_medicine_med")
public class MedicineMed extends Sys<String> {

	private static final long serialVersionUID = 1L;
	private String name;
	private String typeId;
	private String spec;
	private Double price;
	private String brand;
	private String vendor;
	private String mstate;
	private String barcode;
	private Integer amount;
	private String chainId;

	@OneToOne(targetEntity = MedicineMedType.class, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "typeId", referencedColumnName = "id", insertable = false, updatable = false)
	private MedicineMedType type;
	
	@OneToOne(targetEntity = MedicineChain.class, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "chainId", referencedColumnName = "id", insertable = false, updatable = false)
	private MedicineChain chain;

}
