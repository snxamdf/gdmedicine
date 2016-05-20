/*
 * 
 *
 * 
 */
package com.yhy.core.service;

import java.io.Serializable;

import com.yhy.core.domain.Sys;
import com.yhy.core.repository.BaseRepository;

/**
 * 祖先服务类.
 * 
 * @author YHY
 * @version 2014-08-08
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-08
 */
public abstract class Service<T extends Sys<ID>, ID extends Serializable> {

	/**
	 * 在子类实现的回调函数，为Service提供基本操作所需的Repository.
	 * 
	 * @return
	 * @author YHY
	 * @version 2014-08-08
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-08
	 */
	public abstract BaseRepository<T, ID> getRepository();

}
