/*
 * 
 *
 * 
 */
package com.gdm.core.controller;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdm.core.constants.CTL;
import com.gdm.core.domain.Sys;
import com.gdm.core.search.Filter;
import com.gdm.core.utils.Pageables;
import com.gdm.core.utils.Results;
import com.gdm.core.utils.Servlets;
import com.gdm.core.utils.Sorts;
import com.gdm.core.utils.Specifications;

/**
 * 提供基本操作的控制器父类.
 * 
 * @author YHY
 * @version 2014-08-11
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-11
 */
public abstract class ApisController<T extends Sys<ID>, ID extends Serializable> extends Controller<T, ID> {

	@ResponseBody
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = Servlets.getParametersStartingWith(request, CTL.SEARCH_PREFIX);
		if (!params.containsKey(Filter.DELETION)) { // 如果没有指定查询逻辑删除条件：默认查询未逻辑删除的数据
			params.put(Filter.DELETION, String.valueOf(Sys.DELETION_NO));
		}
		Sort sort = Sorts.byMap(params);
		Pageable pageable = Pageables.Build(request, response, sort);
		Page<T> page = this.getService().findAll(Specifications.byMap(this.getModule().getEntityClass(), params), pageable);
		return Results.success(page).toJson();
	}

	@ResponseBody
	@RequestMapping("/add")
	public String add(T domain, HttpServletRequest request, Model model) {
		return Results.success(domain).toJson();
	}

	@ResponseBody
	@RequestMapping("/view")
	public String view(@RequestParam(required = true) ID id, Model model) {
		T domain = this.getService().findOne(id);
		return Results.success(domain).toJson();
	}

	@ResponseBody
	@RequestMapping("/edit")
	public String edit(@RequestParam(required = true) ID id, Model model) {
		T domain = this.getService().findOne(id);
		return Results.success(domain).toJson();
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public <S extends T> String save(@RequestParam(required = true) String state, @Valid @ModelAttribute(CTL.DOMAIN) S domain, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return Results.fault("信息保存失败！", result.getAllErrors()).toJson();
		}
		this.getService().save(domain);
		return Results.success("信息保存成功！", domain).toJson();
	}

	@ResponseBody
	@RequestMapping("/deletion")
	public String deletion(@RequestParam(required = true) ID id, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		this.getService().deletion(id);
		return Results.success("信息删除成功！", id).toJson();
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法,先根据form的id从数据库查出Area对象,再把Form提交的内容绑定到该对象上
	 * 。
	 */
	@ModelAttribute
	public void get(ID id, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		Map<String, Object> params = Servlets.getParametersStartsWith(request, CTL.SEARCH_PREFIX);
		model.addAllAttributes(params);
		if (id != null && StringUtils.isNotBlank((String) id)) {
			T domain = this.getService().findOne(id);
			model.addAttribute(CTL.DOMAIN, domain);
		}
	}

}
