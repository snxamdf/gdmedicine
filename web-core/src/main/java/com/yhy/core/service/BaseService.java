/*
 * 
 *
 * 
 */
package com.yhy.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.core.domain.Sys;
import com.yhy.core.search.Filter;
import com.yhy.core.utils.Sorts;
import com.yhy.core.utils.Specifications;

/**
 * 提供基本操作服务父类.
 * 
 * @author YHY
 * @version 2014-08-08
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-08
 */
public abstract class BaseService<T extends Sys<ID>, ID extends Serializable> extends PagingAndSortingService<T, ID> {

	/**
	 * Returns a single domain matching the given {@link Specification}.
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public T findOne(Specification<T> spec) {
		return this.getRepository().findOne(spec);
	}

	/**
	 * Returns a single domain matching the given {@link Filter}.
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public T findOne(Filter... filters) {
		Specification<T> spec = Specifications.byFilter(filters);
		return this.getRepository().findOne(spec);
	}

	/**
	 * Returns all entities matching the given {@link Specification}.
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll(Specification<T> spec) {
		return this.getRepository().findAll(spec);
	}

	/**
	 * Returns all entities matching the given {@link Filter}.
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll(Filter... filters) {
		Specification<T> spec = Specifications.byFilter(filters);
		Sort sort = Sorts.byFilter(filters);
		return this.findAll(spec, sort);
	}

	/**
	 * Returns a {@link Page} of entities matching the given {@link Specification}.
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return this.getRepository().findAll(spec, pageable);
	}

	/**
	 * Returns all entities matching the given {@link Specification} and {@link Sort}.
	 * 
	 * @param spec
	 * @param sort
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll(Specification<T> spec, Sort sort) {
		return this.getRepository().findAll(spec, sort);
	}

	/**
	 * Returns the number of instances that the given {@link Specification} will return.
	 * 
	 * @param spec the {@link Specification} to count instances for
	 * @return the number of instances
	 */
	@Transactional(readOnly = true)
	public long count(Specification<T> spec) {
		return this.getRepository().count(spec);
	}

	/**
	 * Returns the number of instances that the given {@link Filter} will return.
	 * 
	 * @param filters the {@link Filter} to count instances for
	 * @return the number of instances
	 */
	@Transactional(readOnly = true)
	public long count(Filter... filters) {
		Specification<T> spec = Specifications.byFilter(filters);
		return this.getRepository().count(spec);
	}

	/**
	 * Logical deletes the domain with the given id.
	 * 
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
	 */
	@Transactional
	public void deletion(ID id) {
		this.deletion(this.getRepository().findOne(id));
	}

	/**
	 * Logical deletes a given domain.
	 * 
	 * @param domain
	 * @throws IllegalArgumentException in case the given domain is (@literal null}.
	 */
	@Transactional
	public void deletion(T domain) {
		domain.setDeletion(Sys.DELETION_YES);
		this.getRepository().save(domain);
	}

	/**
	 * Logical deletes the given entities.
	 * 
	 * @param entities
	 * @throws IllegalArgumentException in case the given {@link Iterable} is (@literal null}.
	 */
	@Transactional
	public void deletion(Iterable<? extends T> entities) {
		for (T domain : entities) {
			this.delete(domain);
		}
	}

}
