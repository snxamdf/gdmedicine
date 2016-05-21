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
import com.yhy.sys.domain.Users;
import com.yhy.sys.service.UsersService;

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
@RequestMapping(value = CTL.BMS_PATH + "/sys/users")
public class BmsUsersController extends BaseController<Users, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsUsersController.class);

	@Autowired
	private UsersService usersService;

	@Override
	public BaseService<Users, String> getService() {
		return usersService;
	}

	@Override
	public Module<Users> getModule() {
		return new Module<Users>(SYS.PROJECT, "users", CTL.BMS, Users.class);
	}

}
