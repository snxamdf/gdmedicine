/*
 * 
 *
 * 
 */
package com.gdm.core.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.gdm.core.domain.Sys;

/**
 * 增删改查服务父类.
 * 
 * @author YHY
 * @version 2014-08-08
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-08
 */
public abstract class CrudService<T extends Sys<ID>, ID extends Serializable> extends Service<T, ID> {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	@Transactional
	public <S extends T> S save(S entity) {
		return this.getRepository().save(entity);
	}

	/**
	 * Saves all given entities.
	 * 
	 * @param entities
	 * @return the saved entities
	 * @throws IllegalArgumentException in case the given entity is (@literal null}.
	 */
	@Transactional
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		return this.getRepository().save(entities);
	}

	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	@Transactional(readOnly = true)
	public T findOne(ID id) {
		return this.getRepository().findOne(id);
	}

	/**
	 * Returns whether an entity with the given id exists.
	 * 
	 * @param id must not be {@literal null}.
	 * @return true if an entity with the given id exists, {@literal false} otherwise
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	@Transactional(readOnly = true)
	public boolean exists(ID id) {
		return this.getRepository().exists(id);
	}

	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	@Transactional(readOnly = true)
	public Iterable<T> findAll() {
		return this.getRepository().findAll();
	}

	/**
	 * Returns all instances of the type with the given IDs.
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = true)
	public Iterable<T> findAll(Iterable<ID> ids) {
		return this.getRepository().findAll(ids);
	}

	/**
	 * Returns the number of entities available.
	 * 
	 * @return the number of entities
	 */
	@Transactional(readOnly = true)
	public long count() {
		return this.getRepository().count();
	}

	/**
	 * Deletes the entity with the given id.
	 * 
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
	 */
	@Transactional
	public void delete(ID id) {
		this.getRepository().delete(id);
	}

	/**
	 * Deletes a given entity.
	 * 
	 * @param entity
	 * @throws IllegalArgumentException in case the given entity is (@literal null}.
	 */
	@Transactional
	public void delete(T entity) {
		this.getRepository().delete(entity);
	}

	/**
	 * Deletes the given entities.
	 * 
	 * @param entities
	 * @throws IllegalArgumentException in case the given {@link Iterable} is (@literal null}.
	 */
	@Transactional
	public void delete(Iterable<? extends T> entities) {
		this.getRepository().delete(entities);
	}

	/**
	 * Deletes all entities managed by the repository.
	 */
	@Transactional
	public void deleteAll() {
		this.getRepository().deleteAll();
	}

}
