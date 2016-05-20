/*
 * 
 *
 * 
 */
package com.yhy.core.api;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.core.dto.Result;
import com.yhy.core.utils.Results;

/**
 * 数据库序列服务实现类.
 * 
 * @author YHY
 * @version 2015-01-17
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-17
 */
@Service
@Transactional
public class SequenceServiceImpl implements SequenceService {

	@PersistenceContext
	private EntityManager em;

	/**
	 * 根据序列名称获取下一个序列值.
	 * 第一次获取序列值前需要到数据库中执行相应的sql：insert into t_sys_sequence set name='seq_sys_user';
	 * 序列名称规范：seq_没有前缀"t_"的表名
	 * 序列名称示例：seq_sys_user（对应的表名是：t_sys_user）
	 * 
	 * @see com.yhy.core.api.SequenceService#nextval(java.lang.String)
	 * @param seq_name 序列名称
	 * @return 下一个序列值
	 * @author YHY
	 * @version 2015-01-17
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-17
	 */
	@Override
	public Result<Long> nextval(String seq_name) {
		Query query = em.createNativeQuery("select nextval('" + seq_name + "')");
		Object obj = query.getSingleResult();
		if (obj == null) {
			return Results.fault(-1, "", null);
		} else {
			BigInteger val = (BigInteger) obj;
			return Results.success(val.longValue());
		}
	}

}
