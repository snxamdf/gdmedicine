/*
 * 
 *
 * 
 */
package com.yhy.core.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yhy.core.constants.CTL;
import com.yhy.core.constants.SYMBOL;
import com.yhy.core.domain.Sys;
import com.yhy.core.search.Filter;
import com.yhy.core.utils.Pageables;
import com.yhy.core.utils.Pages;
import com.yhy.core.utils.Servlets;
import com.yhy.core.utils.Sorts;
import com.yhy.core.utils.Specifications;

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
public abstract class BaseController<T extends Sys<ID>, ID extends Serializable> extends Controller<T, ID> {

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = Servlets.getParametersStartingWith(request, CTL.SEARCH_PREFIX);
		if (!params.containsKey(Filter.DELETION)) { // 如果没有指定查询逻辑删除条件：默认查询未逻辑删除的数据
			params.put(Filter.DELETION, String.valueOf(Sys.DELETION_NO));
		}
		Sort sort = Sorts.byMap(params);
		Pageable pageable = Pageables.Build(request, response, sort);
		Page<T> page = this.getService().findAll(Specifications.byMap(this.getModule().getEntityClass(), params), pageable);
		model.addAttribute(CTL.PAGE, page);
		model.addAttribute(CTL.PAGINATION, Pages.toHtml(page));
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.LIST;
	}

	@RequestMapping("/add")
	public String add(T domain, HttpServletRequest request, Model model) {
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_ADD);
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@RequestMapping("/view")
	public String view(@RequestParam(required = true) ID id, Model model) {
		T domain = this.getService().findOne(id);
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_VIEW);
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@RequestMapping("/edit")
	public String edit(@RequestParam(required = true) ID id, Model model) {
		T domain = this.getService().findOne(id);
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_EDIT);
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public <S extends T> String save(@RequestParam(required = true) String state, @Valid @ModelAttribute(CTL.DOMAIN) S domain, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			model.addAttribute(CTL.STATE, state);
			model.addAttribute(CTL.FORM_ERRORS, result.getAllErrors());
			return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
		}
		this.getService().save(domain);
		Map<String, Object> params = Servlets.getParametersStartsWith(request, CTL.SEARCH_PREFIX);
		redirect.addAllAttributes(params);
		redirect.addFlashAttribute(CTL.GLOBAL_MESSAGE, "信息保存成功！");
		return CTL.REDIRECT_PREFIX + this.getModule().getRequestMapping() + SYMBOL.SLANT + CTL.LIST;
	}

	@RequestMapping("/deletion")
	public String deletion(@RequestParam(required = true) ID id, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		this.getService().deletion(id);
		Map<String, Object> params = Servlets.getParametersStartsWith(request, CTL.SEARCH_PREFIX);
		redirect.addAllAttributes(params);
		redirect.addFlashAttribute(CTL.GLOBAL_MESSAGE, "信息删除成功！");
		return CTL.REDIRECT_PREFIX + this.getModule().getRequestMapping() + SYMBOL.SLANT + CTL.LIST;
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
