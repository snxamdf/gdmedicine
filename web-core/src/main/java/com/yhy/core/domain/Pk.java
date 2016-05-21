/*
 * 
 *
 * 
 */
package com.yhy.core.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

/**
 * 主键父实体类.
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
@MappedSuperclass
public class Pk<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 2488866834724059151L;

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid2")
	protected ID id;

}
