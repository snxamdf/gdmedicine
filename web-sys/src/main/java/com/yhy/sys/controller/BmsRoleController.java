/*
 * 
 *
 * 
 */
package com.yhy.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.core.annotation.BmsEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.controller.BaseController;
import com.yhy.core.dto.Module;
import com.yhy.core.service.BaseService;
import com.yhy.sys.constants.SYS;
import com.yhy.sys.domain.Role;
import com.yhy.sys.service.RoleService;

/**
 * 医药管理Controller.
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
@RequestMapping(value = CTL.BMS_PATH + "/sys/role")
public class BmsRoleController extends BaseController<Role, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsRoleController.class);

	@Autowired
	private RoleService roleService;

	@Override
	public BaseService<Role, String> getService() {
		return roleService;
	}

	@Override
	public Module<Role> getModule() {
		return new Module<Role>(SYS.PROJECT, "role", CTL.BMS, Role.class);
	}

}
