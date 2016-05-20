/*
 * 
 *
 * 
 */
package com.yhy.core.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhy.core.constants.DOMAIN;
import com.yhy.core.constants.SYMBOL;
import com.yhy.core.constants.CTL;
import com.yhy.core.domain.Sys;
import com.yhy.core.domain.Tree;
import com.yhy.core.dto.Checked;
import com.yhy.core.search.Filter;
import com.yhy.core.service.TreeService;
import com.yhy.core.utils.Pageables;
import com.yhy.core.utils.Pages;
import com.yhy.core.utils.Servlets;
import com.yhy.core.utils.Specifications;
import com.yhy.core.utils.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 树结构控制器父类.
 * 
 * @author YHY
 * @version 2014-08-11
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-11
 */
public abstract class TreeController<T extends Tree<ID>, ID extends Serializable> extends BaseController<T, ID> {

	@Override
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = Servlets.getParametersStartingWith(request, CTL.SEARCH_PREFIX);
		if (!params.containsKey(Filter.DELETION)) { // 如果没有指定查询逻辑删除条件：默认查询未逻辑删除的数据
			params.put(Filter.DELETION, String.valueOf(Sys.DELETION_NO));
		}
		String searchParentId = request.getParameter(CTL.SEARCH_PREFIX + Filter.PARENT_ID);
		if (StringUtils.isBlank(searchParentId)) {
			searchParentId = Tree.ROOT;
			params.put(Filter.PARENT_ID, searchParentId);
		}
		Pageable pageable = Pageables.Build(request, response, Sort.Direction.ASC, DOMAIN.ORDINAL);
		Page<T> page = this.getService().findAll(Specifications.byMap(this.getModule().getEntityClass(), params), pageable);
		TreeService<T, ID> treeService = (TreeService<T, ID>) this.getService();
		@SuppressWarnings("unchecked")
		List<Checked> parents = treeService.getParents((ID) searchParentId);
		model.addAttribute(CTL.PARENTS, parents);
		model.addAttribute(CTL.PAGE, page);
		model.addAttribute(CTL.PAGINATION, Pages.toHtml(page));
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.LIST;
	}

	@Override
	@RequestMapping("/add")
	public String add(T entity, HttpServletRequest request, Model model) {
		@SuppressWarnings("unchecked")
		ID searchParentId = (ID) request.getParameter(CTL.SEARCH_PREFIX + Filter.PARENT_ID);
		entity.setParentId(searchParentId);
		return super.add(entity, request, model);
	}

	/**
	 * 获取树json数据.
	 * 
	 * @param exclusionId 排除掉的ID
	 * @param request
	 * @param response
	 * @return
	 * @author YHY
	 * @version 2014-08-26
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-26
	 */
	@ResponseBody
	@RequestMapping(value = "tree")
	public List<Map<String, String>> tree(ID exclusionId, ID rootId, boolean isShowRoot, HttpServletRequest request) {
		Map<String, Object> params = Servlets.getParametersStartingWith(request, CTL.SEARCH_PREFIX);
		if (!params.containsKey(Filter.DELETION)) { // 如果没有指定查询逻辑删除条件：默认查询未逻辑删除的数据
			params.put(Filter.DELETION, String.valueOf(Sys.DELETION_NO));
		}
		List<Map<String, String>> mapList = Lists.newArrayList();
		Iterable<T> trees = this.getService().findAll(Specifications.byMap(this.getModule().getEntityClass(), params));
		for (T tree : trees) {
			if (!isExclusionId(tree, exclusionId) && hasRootId(tree, rootId, isShowRoot)) {
				Map<String, String> map = Maps.newHashMap();
				map.put(DOMAIN.ID, tree.getId().toString());
				map.put(Tree.P_ID, (tree.getParentId() == null || Strings.isBlank(tree.getParentId().toString())) ? "" : tree.getParentId().toString());
				map.put(DOMAIN.NAME, tree.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 判断是否是在某根节点之下的子节点.
	 * 
	 * @param tree
	 * @param rootId
	 * @return
	 * @author YHY
	 * @version 2014-08-27
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-27
	 */
	private boolean hasRootId(T tree, ID rootId, boolean isShowRoot) {
		if (rootId == null) {
			return true;
		} else {
			if (isShowRoot && rootId.equals(tree.getId())) {
				return true;
			}
			if (tree.getParentIds() != null && tree.getParentIds().indexOf(rootId + SYMBOL.COMMA) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是需要排除掉的记录.
	 * 
	 * @param tree
	 * @param exclusionId 排除掉的ID
	 * @return
	 * @author YHY
	 * @version 2014-08-27
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-27
	 */
	private boolean isExclusionId(T tree, ID exclusionId) {
		if (exclusionId == null) {
			return false;
		} else {
			if (exclusionId.equals(tree.getId())) {
				return true;
			}
			if (tree.getParentIds() != null && tree.getParentIds().indexOf(SYMBOL.COMMA + exclusionId) != -1) {
				return true;
			}
		}
		return false;
	}

}
