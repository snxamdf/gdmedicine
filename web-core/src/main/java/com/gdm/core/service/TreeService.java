/*
 * 
 *
 * 
 */
package com.gdm.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.gdm.core.constants.DOMAIN;
import com.gdm.core.constants.SYMBOL;
import com.gdm.core.domain.Sys;
import com.gdm.core.domain.Tree;
import com.gdm.core.dto.Checked;
import com.gdm.core.utils.Strings;
import com.google.common.collect.Lists;

/**
 * 树结构服务父类.
 * 
 * @author YHY
 * @version 2014-08-08
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-08
 */
public abstract class TreeService<T extends Tree<ID>, ID extends Serializable> extends BaseService<T, ID> {

	@Override
	@Transactional
	public void deletion(ID id) {
		T domain = this.findOne(id);
		this.deletion(domain);
	}

	@Override
	@Transactional
	public void deletion(T domain) {
		super.deletion(domain);
		setParentLeaf(domain);
	}

	@Override
	@Transactional
	public void delete(ID id) {
		T entity = this.findOne(id);
		this.delete(entity);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		super.delete(entity);
		setParentLeaf(entity);
	}

	/*
	 * 设置父节点是否是叶子节点
	 */
	private void setParentLeaf(T entity) {
		T parent = this.getRepository().findOne(entity.getParentId());
		if (parent != null) {
			Integer leaf = getLeaf(entity.getParentId());
			if (!leaf.equals(parent.getLeaf())) {
				parent.setLeaf(leaf);
				super.save(parent);
			}
		}
	}

	@Override
	@Transactional
	public <S extends T> S save(S entity) {
		// 修改当前节点的系统字段信息
		modifyParents(entity);
		if (entity.getId() != null) {
			// 判断是否修改了parentId，如果修改了，那么就修改老的父节点的系统字段信息
			modifyOldParents(entity);
			S result = super.save(entity);
			// 修改当前节点的所有子节点系统字段信息
			modifyParents4Childs(entity.getId());
			return result;
		} else {
			return super.save(entity);
		}
	}

	/*
	 * 修改系统字段信息：修改ParentIds、ParentCodes、ParentNames、fullname、leaf、grade
	 */
	private void modifyParents(T entity) {
		if (entity.getParentId() != null && StringUtils.isNotBlank(entity.getParentId().toString())) {
			T parent = this.getRepository().findOne(entity.getParentId());
			if (parent != null) {
				// 设置父节点是否是叶子节点
				if (Tree.LEAF_YES.equals(parent.getLeaf())) {
					parent.setLeaf(Tree.LEAF_NO);
					super.save(parent);
				}
				String parentIds = StringUtils.isBlank(parent.getParentIds()) ? "" : parent.getParentIds();
				String parentCodes = StringUtils.isBlank(parent.getParentCodes()) ? "" : parent.getParentCodes();
				String parentNames = StringUtils.isBlank(parent.getParentNames()) ? "" : parent.getParentNames();
				String fullname = StringUtils.isBlank(parent.getFullname()) ? "" : parent.getFullname() + SYMBOL.SLANT2;
				entity.setParentIds(parentIds + entity.getParentId() + SYMBOL.COMMA);
				entity.setParentCodes(parentCodes + parent.getCode() + SYMBOL.COMMA);
				entity.setParentNames(parentNames + parent.getName() + SYMBOL.COMMA);
				entity.setFullname(fullname + entity.getName());
				if (parent.getGrade() == null) {
					entity.setGrade(1);
				} else {
					entity.setGrade(parent.getGrade() + 1);
				}
				// 设置是否是叶子节点
				entity.setLeaf(getLeaf(entity.getId()));
			}
		}
	}

	/*
	 * 递归修改子节点的系统字段信息：ParentIds、ParentCodes、ParentNames、fullname、leaf、grade
	 */
	private void modifyParents4Childs(final ID id) {
		if (id != null && StringUtils.isNotBlank(id.toString())) {
			// 查询当前节点的子节点信息
			Specification<T> spec = new Specification<T>() {
				public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
					List<Predicate> predicates = Lists.newArrayList();
					predicates.add(builder.equal(root.get(DOMAIN.DELETION), Sys.DELETION_NO));
					predicates.add(builder.equal(root.get(DOMAIN.PARENT_ID), id));
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			List<T> childs = this.getRepository().findAll(spec);
			for (T child : childs) {
				modifyParents(child);
				modifyParents4Childs(child.getId());
			}
		}
	}

	/*
	 * 判断是否修改了parentId，如果修改了，那么就修改老的父节点的系统字段信息
	 */
	private void modifyOldParents(T entity) {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			return;
		}
		ID parentId = entity.getParentId();
		if (parentId != null && StringUtils.isNotBlank(parentId.toString())) {
			T old = this.getRepository().findOne(entity.getId());
			if (old != null) {
				if (!parentId.toString().equals(old.getParentId().toString())) { // 修改了parentId
					modifyParents(old);
					return;
				}
			}
		}
		return;
	}

	/*
	 * 获取该节点的叶子节点值
	 */
	private Integer getLeaf(final ID id) {
		if (id == null || Strings.isBlank(id.toString())) {
			return Tree.LEAF_YES;
		}
		Specification<T> spec = new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = Lists.newArrayList();
				predicates.add(builder.equal(root.get(DOMAIN.DELETION), Sys.DELETION_NO));
				predicates.add(builder.equal(root.get(DOMAIN.PARENT_ID), id));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		long count = this.getRepository().count(spec);
		if (count > 0) {
			return Tree.LEAF_NO;
		} else {
			return Tree.LEAF_YES;
		}
	}

	/**
	 * 获取当前节点的所有父节点.
	 * 
	 * @param id
	 * @return
	 * @author YHY
	 * @version 2014-08-08
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-08
	 */
	@Transactional(readOnly = true)
	public List<Checked> getParents(ID id) {
		return getParents(id, id);
	}

	/**
	 * 递归方式获取当前节点的所有父节点.
	 * 
	 * @param currentId
	 * @param rootId
	 * @return
	 * @author YHY
	 * @version 2014-08-08
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-08
	 */
	@Transactional(readOnly = true)
	private List<Checked> getParents(ID currentId, ID rootId) {
		List<Checked> parents = new ArrayList<Checked>();
		if (currentId == null || StringUtils.isBlank((String) currentId) || rootId == null || StringUtils.isBlank((String) rootId)) {
			return parents;
		}
		Tree<ID> tree = this.getRepository().findOne(currentId);
		if (tree.getParentId() != null && StringUtils.isNotBlank((String) tree.getParentId())) {
			Tree<ID> parent = this.getRepository().findOne(tree.getParentId());
			if (parent != null) {
				List<Checked> parentActiveMaps = getParents(parent.getId(), rootId);
				for (Checked parentActiveMap : parentActiveMaps) {
					parents.add(parentActiveMap);
				}
			}
		}
		Checked checked = new Checked();
		checked.setId((String) tree.getId());
		checked.setName(tree.getName());
		checked.setChecked(rootId.equals(currentId) ? true : false);
		parents.add(checked);
		return parents;
	}

}
