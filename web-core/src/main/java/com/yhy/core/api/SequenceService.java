/*
 * 
 *
 * 
 */
package com.yhy.core.api;

import com.yhy.core.dto.Result;

/**
 * 数据库序列服务接口.
 * 
 * @author YHY
 * @version 2015-01-17
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-17
 */
public interface SequenceService {

	/**
	 * 根据序列名称获取下一个序列值.
	 * 第一次获取序列值前需要到数据库中执行相应的sql：insert into t_sys_sequence set name='seq_sys_user';
	 * 序列名称规范：seq_没有前缀"t_"的表名
	 * 序列名称示例：seq_sys_user（对应的表名是：t_sys_user）
	 * 
	 * @param seq_name 序列名称
	 * @return 下一个序列值
	 * @author YHY
	 * @version 2015-01-17
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-17
	 */
	Result<Long> nextval(String seq_name);

}
