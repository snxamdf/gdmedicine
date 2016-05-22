/*
 * 
 *
 * 
 */
package com.yhy.medicine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yhy.core.annotation.BmsEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.constants.SYMBOL;
import com.yhy.core.controller.BaseController;
import com.yhy.core.dto.Module;
import com.yhy.core.service.BaseService;
import com.yhy.core.utils.Servlets;
import com.yhy.core.utils.Strings;
import com.yhy.medicine.constants.MEDICINE;
import com.yhy.medicine.domain.MedicineMed;
import com.yhy.medicine.service.MedicineMedService;
import com.yhy.medicine.service.MedicineMedTypeService;

/**
 * 药品管理表Controller.
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@BmsEnv
@Controller
@RequestMapping(value = CTL.BMS_PATH + "/medicine/med")
public class BmsMedicineMedController extends BaseController<MedicineMed, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsMedicineMedController.class);

	@Autowired
	private MedicineMedService medicineMedService;

	@Autowired
	private MedicineMedTypeService medicineMedTypeService;

	public BaseService<MedicineMed, String> getService() {
		return medicineMedService;
	}

	@Override
	public Module<MedicineMed> getModule() {
		return new Module<MedicineMed>(MEDICINE.PROJECT, "medicine.med", CTL.BMS, MedicineMed.class);
	}

	@Override
	@RequestMapping("/add")
	public String add(MedicineMed domain, HttpServletRequest request, Model model) {
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_ADD);
		model.addAttribute("medTypes", medicineMedTypeService.findAll());
		int s = (int) (Math.random() * 1000000);
		domain.setBarcode(String.valueOf(s));
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@Override
	@RequestMapping("/view")
	public String view(@RequestParam(required = true) String id, Model model) {
		MedicineMed domain = this.getService().findOne(id);
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_VIEW);
		model.addAttribute("medTypes", medicineMedTypeService.findAll());
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@Override
	@RequestMapping("/edit")
	public String edit(@RequestParam(required = true) String id, Model model) {
		MedicineMed domain = this.getService().findOne(id);
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_EDIT);
		model.addAttribute("medTypes", medicineMedTypeService.findAll());
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam(required = true) String state, @Valid @ModelAttribute(CTL.DOMAIN) MedicineMed domain, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			model.addAttribute(CTL.STATE, state);
			model.addAttribute(CTL.FORM_ERRORS, result.getAllErrors());
			return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
		}
		if (Strings.isNotBlank(domain.getId())) {
			if (!Strings.isNotBlank(domain.getMstate())) {
				domain.setMstate("入库");
			}
		} else {
			domain.setMstate("入库");
		}
		this.getService().save(domain);
		Map<String, Object> params = Servlets.getParametersStartsWith(request, CTL.SEARCH_PREFIX);
		redirect.addAllAttributes(params);
		redirect.addFlashAttribute(CTL.GLOBAL_MESSAGE, "信息保存成功！");
		return CTL.REDIRECT_PREFIX + this.getModule().getRequestMapping() + SYMBOL.SLANT + CTL.LIST;
	}

}
