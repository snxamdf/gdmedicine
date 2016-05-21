/*
 * 
 *
 * 
 */
package com.gdm.medicine.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdm.core.annotation.BmsEnv;
import com.gdm.core.constants.CTL;
import com.gdm.core.dto.Result;
import com.gdm.core.utils.Auths;

/**
 * Controller.
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
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = { "/login", "/", "index" })
	public String login() {
		if (Auths.isLogin()) {
			return CTL.REDIRECT_PREFIX + "/medicine/chain/list";
		}
		return "medicine/login";
	}

	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String login(@RequestParam(required = true) String username, @RequestParam(required = true) String password, HttpServletRequest request, Model model) {
		// if (!ValidcodeServlet.validate(request, validcode)) {
		// model.addAttribute("username",
		// Encodes.urlEncode(Encodes.urlEncode(username)));
		// model.addAttribute("validcodeErr",
		// Encodes.urlEncode(Encodes.urlEncode("验证码错误")));
		// return CTL.REDIRECT_PREFIX + "/login";
		// }
		Result<String> result = Auths.login(username, password, request);
		if (!result.isSuccess()) {
			model.addAttribute("usernameErr", "无效的用户名或密码");
			logger.info(result.getMessage());
			return "medicine/login";
		} else {
			return CTL.REDIRECT_PREFIX + "/";
		}
		// Passwords.encrypt(newPasswd)
	}

}
