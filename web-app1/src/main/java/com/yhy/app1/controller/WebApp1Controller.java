package com.yhy.app1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.app1.constants.WEB;
import com.yhy.app1.domain.CmsAffiche;
import com.yhy.core.annotation.WebEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.controller.BaseController;
import com.yhy.core.dto.Module;
import com.yhy.core.service.BaseService;

@WebEnv
@Controller
@RequestMapping(value = CTL.WEB_PATH + "/app1")
public class WebApp1Controller extends BaseController<CmsAffiche, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(WebApp1Controller.class);

	@Override
	public Module<CmsAffiche> getModule() {
		return new Module<CmsAffiche>(WEB.PROJECT, "cms.affiche", CTL.WEB,
				CmsAffiche.class);
	}

	/**
	 * 
	 */
	@RequestMapping("/toView")
	public String toView() {
		System.out.println("toView");
		return "test/web.index";
	}

	@Override
	public BaseService<CmsAffiche, String> getService() {
		System.out.println("BaseService");
		return null;
	}
}
