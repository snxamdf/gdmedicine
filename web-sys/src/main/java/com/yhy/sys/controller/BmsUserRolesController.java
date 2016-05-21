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
import com.yhy.sys.domain.UserRoles;
import com.yhy.sys.service.UserRolesService;

/**
 * 用户角色关系表Controller.
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
@RequestMapping(value = CTL.BMS_PATH + "/sys/user/roles")
public class BmsUserRolesController extends BaseController<UserRoles, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsUserRolesController.class);

	@Autowired
	private UserRolesService userRolesService;

	@Override
	public BaseService<UserRoles, String> getService() {
		return userRolesService;
	}

	@Override
	public Module<UserRoles> getModule() {
		return new Module<UserRoles>(SYS.PROJECT, "user.roles", CTL.BMS, UserRoles.class);
	}

}
