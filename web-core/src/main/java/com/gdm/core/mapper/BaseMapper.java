/*
 * 
 *
 * 
 */
package com.gdm.core.mapper;

import java.io.Serializable;

/**
 * BaseMapper范型接口.
 * 
 * @author YHY
 * @version 2015-01-15
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-15
 */
public interface BaseMapper<T, ID extends Serializable> {

	public T findById(ID id);

}
