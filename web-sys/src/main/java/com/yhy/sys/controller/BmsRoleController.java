/*
 * 
 *
 * 
 */
package com.yhy.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhy.core.annotation.BmsEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.controller.BaseController;
import com.yhy.core.dto.Module;
import com.yhy.core.dto.Result;
import com.yhy.core.search.Filter;
import com.yhy.core.search.Filter.Operator;
import com.yhy.core.service.BaseService;
import com.yhy.core.utils.Results;
import com.yhy.sys.constants.SYS;
import com.yhy.sys.domain.Role;
import com.yhy.sys.domain.UserRoles;
import com.yhy.sys.domain.Users;
import com.yhy.sys.repository.UserRolesRepository;
import com.yhy.sys.service.RoleService;
import com.yhy.sys.service.UsersService;

/**
 * 医药管理Controller.
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
@RequestMapping(value = CTL.BMS_PATH + "/sys/role")
public class BmsRoleController extends BaseController<Role, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsRoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private UserRolesRepository userRolesRepository;

	@Override
	public BaseService<Role, String> getService() {
		return roleService;
	}

	@Override
	public Module<Role> getModule() {
		return new Module<Role>(SYS.PROJECT, "role", CTL.BMS, Role.class);
	}

	@RequestMapping("/user/distr")
	public String userRoleDist(HttpServletRequest request, Model model) {
		Iterable<Users> users = usersService.findAll(new Filter("deletion", Operator.EQ, "0"));
		Iterable<Role> roles = roleService.findAll(new Filter("deletion", Operator.EQ, "0"));
		model.addAttribute("users", users);
		model.addAttribute("roles", roles);
		return "sys/bms.role.user.distr.form";
	}

	@ResponseBody
	@RequestMapping("/user/get")
	public Result<List<UserRoles>> getRolesByUserId(String uid) {
		return Results.success(userRolesRepository.findByUserId(uid));
	}

	@ResponseBody
	@RequestMapping("/user/distr/save")
	public Result<String> userRoleDistSave(HttpServletRequest request, String user, Model model) {
		if (user == null || "-1".equals(user)) {
			return Results.fault(-1, "请选择用户");
		}
		String[] roles = request.getParameterValues("roles");
		if (roles == null) {
			return Results.fault(-1, "请选择权限");
		}
		userRolesRepository.deleteByUserId(user);
		for (String role : roles) {
			UserRoles ur = new UserRoles();
			ur.setUserId(user);
			ur.setRoleId(role);
			userRolesRepository.save(ur);
		}
		return Results.success("ok");
	}
}
