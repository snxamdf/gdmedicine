/*
 * 
 *
 * 
 */
package com.gdm.medicine.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.gdm.core.annotation.BmsEnv;
import com.gdm.core.constants.CTL;
import com.gdm.core.controller.BaseController;
import com.gdm.core.dto.Module;
import com.gdm.core.service.BaseService;
import com.gdm.core.utils.Passwords;
import com.gdm.medicine.constants.MEDICINE;
import com.gdm.medicine.domain.MedicineUsers;
import com.gdm.medicine.service.MedicineUsersService;

/**
 * 用户表Controller.
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
@BmsEnv
@Controller
@RequestMapping(value = CTL.WEB_PATH + "/medicine/users")
public class BmsMedicineUsersController extends BaseController<MedicineUsers, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsMedicineUsersController.class);

	@Autowired
	private MedicineUsersService medicineUsersService;

	@Override
	public BaseService<MedicineUsers, String> getService() {
		return medicineUsersService;
	}

	@Override
	public Module<MedicineUsers> getModule() {
		return new Module<MedicineUsers>(MEDICINE.PROJECT, "medicine.users", CTL.WEB, MedicineUsers.class);
	}

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam(required = true) String state, @Valid @ModelAttribute(CTL.DOMAIN) MedicineUsers domain, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		if (StringUtils.isBlank(domain.getPasswd())) {
			MedicineUsers user = medicineUsersService.findOne(domain.getId());
			domain.setPasswd(user.getPasswd());
		} else {
			domain.setPasswd(Passwords.encrypt(domain.getPasswd()));
		}
		return super.save(state, domain, result, request, model, redirect);
	}
}
