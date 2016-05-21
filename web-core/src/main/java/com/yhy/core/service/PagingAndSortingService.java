/*
 * 
 *
 * 
 */
package com.yhy.core.service;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.core.domain.Sys;
import com.yhy.core.exception.DataAccessException;
import com.yhy.core.utils.Statements;

/**
 * 分页排序服务父类.
 * 
 * @author YHY
 * @version 2014-08-08
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-08
 */
public abstract class PagingAndSortingService<T extends Sys<ID>, ID extends Serializable> extends CrudService<T, ID> {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * 根据select id分页查询记录.
	 */
	public <X> Page<X> findBySelectId(Pageable pageable, String selectId, Object parameter) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			long count = 0;
			if (pageable.getPageSize() > 0) {
				count = Statements.getRowCount(sqlSession, selectId, parameter);
			}
			if (pageable.getPageSize() == -1) {
				List<X> datas = sqlSession.selectList(selectId, parameter);
				return new PageImpl<X>(datas, pageable, count);
			} else {
				List<X> datas = sqlSession.selectList(selectId, parameter, new RowBounds(pageable.getOffset(), pageable.getPageSize()));
				return new PageImpl<X>(datas, pageable, count);
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * Returns all entities sorted by the given options.
	 * 
	 * @param sort
	 * @return all entities sorted by the given options
	 */
	@Transactional(readOnly = true)
	public Iterable<T> findAll(Sort sort) {
		return this.getRepository().findAll(sort);
	}

	/**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
	 * 
	 * @param pageable
	 * @return a page of entities
	 */
	@Transactional(readOnly = true)
	public Page<T> findAll(Pageable pageable) {
		return this.getRepository().findAll(pageable);
	}

}
