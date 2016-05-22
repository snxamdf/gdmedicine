/*
 * 
 *
 * 
 */
package com.yhy.sys.controller;

import java.util.List;
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
import com.yhy.core.utils.Auths;
import com.yhy.core.utils.Passwords;
import com.yhy.core.utils.Servlets;
import com.yhy.core.utils.Strings;
import com.yhy.sys.constants.SYS;
import com.yhy.sys.domain.Role;
import com.yhy.sys.domain.UserRoles;
import com.yhy.sys.domain.Users;
import com.yhy.sys.repository.RoleRepository;
import com.yhy.sys.repository.UserRolesRepository;
import com.yhy.sys.service.UsersService;

/**
 * 用户表Controller.
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
@RequestMapping(value = CTL.BMS_PATH + "/sys/users")
public class BmsUsersController extends BaseController<Users, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsUsersController.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserRolesRepository userRolesRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public BaseService<Users, String> getService() {
		return usersService;
	}

	@Override
	public Module<Users> getModule() {
		return new Module<Users>(SYS.PROJECT, "users", CTL.BMS, Users.class);
	}

	@Override
	@RequestMapping("/add")
	public String add(Users domain, HttpServletRequest request, Model model) {
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_ADD);
		Iterable<Role> iterable = roleRepository.findAll();
		System.out.println(iterable);

		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@RequestMapping("/information")
	public String Information(Model model) {
		Users domain = this.getService().findOne(Auths.getUserDetail().getId());
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_VIEW);
		List<UserRoles> roles = userRolesRepository.findByUserId(Auths.getUserDetail().getId());
		System.out.println(roles);
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@Override
	@RequestMapping("/view")
	public String view(String id, Model model) {
		Users domain = this.getService().findOne(id);
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_VIEW);
		List<UserRoles> roles = userRolesRepository.findByUserId(id);
		System.out.println(roles);
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@Override
	@RequestMapping("/edit")
	public String edit(String id, Model model) {
		if (Strings.isBlank(id)) {
			id = Auths.getUserDetail().getId();
		}
		Users domain = this.getService().findOne(id);
		model.addAttribute(CTL.DOMAIN, domain);
		model.addAttribute(CTL.STATE, CTL.STATE_EDIT);
		List<UserRoles> roles = userRolesRepository.findByUserId(id);
		System.out.println(roles);
		Iterable<Role> iterable = roleRepository.findAll();
		System.out.println(iterable);
		return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
	}

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam(required = true) String state, @Valid @ModelAttribute(CTL.DOMAIN) Users domain, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			model.addAttribute(CTL.STATE, state);
			model.addAttribute(CTL.FORM_ERRORS, result.getAllErrors());
			return this.getModule().getTmplName() + SYMBOL.DOT + CTL.FORM;
		}
		domain.setPasswd(Passwords.encrypt(domain.getPasswd()));
		this.getService().save(domain);
		Map<String, Object> params = Servlets.getParametersStartsWith(request, CTL.SEARCH_PREFIX);
		redirect.addAllAttributes(params);
		redirect.addFlashAttribute(CTL.GLOBAL_MESSAGE, "信息保存成功！");
		return CTL.REDIRECT_PREFIX + this.getModule().getRequestMapping() + SYMBOL.SLANT + CTL.LIST;
	}
}
