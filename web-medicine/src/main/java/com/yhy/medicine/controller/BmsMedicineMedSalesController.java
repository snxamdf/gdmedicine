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
import com.yhy.medicine.constants.MEDICINE;
import com.yhy.medicine.domain.MedicineMed;
import com.yhy.medicine.domain.MedicineMedSales;
import com.yhy.medicine.repository.MedicineMedRepository;
import com.yhy.medicine.service.MedicineMedSalesService;

/**
 * 药品销售记录表Controller.
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
@RequestMapping(value = CTL.BMS_PATH + "/medicine/med/sales")
public class BmsMedicineMedSalesController extends BaseController<MedicineMedSales, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsMedicineMedSalesController.class);

	@Autowired
	private MedicineMedSalesService medicineMedSalesService;

	@Override
	public BaseService<MedicineMedSales, String> getService() {
		return medicineMedSalesService;
	}

	@Autowired
	private MedicineMedRepository medicineMedRepository;

	@Override
	public Module<MedicineMedSales> getModule() {
		return new Module<MedicineMedSales>(MEDICINE.PROJECT, "medicine.med.sales", CTL.BMS, MedicineMedSales.class);
	}

	@Override
	@RequestMapping("/add")
	public String add(MedicineMedSales domain, HttpServletRequest request, Model model) {
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_ADD);
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam(required = true) String state, @Valid @ModelAttribute(CTL.DOMAIN) MedicineMedSales domain, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		MedicineMed medicineMed = medicineMedRepository.findByBarcode(domain.getBarcode());
		if (medicineMed == null) {
			result.rejectValue("barcode", "", "该条码/编号(" + domain.getBarcode() + ")不存在.");
		}
		if (medicineMed != null) {
			int amount1 = medicineMed.getAmount();
			int amount2 = domain.getAmount();
			if (amount2 > amount1) {
				result.rejectValue("barcode", "", "药品：(" + medicineMed.getName() + ")销售数量大于库存数量，库存剩余:" + medicineMed.getAmount());
			} else {
				int amount3 = amount1 - amount2;
				if (amount3 == 0) {
					medicineMed.setMstate("售完");
				}
				medicineMed.setAmount(amount3);
				medicineMedRepository.save(medicineMed);
			}
		}
		if (result.hasErrors()) {
			model.addAttribute(CTL.STATE, state);
			model.addAttribute(CTL.FORM_ERRORS, result.getAllErrors());
			return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
		}
		domain.setMedId(medicineMed.getId());
		this.getService().save(domain);
		Map<String, Object> params = Servlets.getParametersStartsWith(request, CTL.SEARCH_PREFIX);
		redirect.addAllAttributes(params);
		redirect.addFlashAttribute(CTL.GLOBAL_MESSAGE, "销售记录添加成功!");
		return CTL.REDIRECT_PREFIX + this.getModule().getRequestMapping() + SYMBOL.SLANT + CTL.LIST;
	}
}
