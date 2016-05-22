/*
 * 
 *
 * 
 */
package com.yhy.medicine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.core.annotation.WebEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.controller.BaseController;
import com.yhy.core.dto.Module;
import com.yhy.core.service.BaseService;
import com.yhy.medicine.constants.MEDICINE;
import com.yhy.medicine.domain.MedicineMed;
import com.yhy.medicine.service.MedicineMedService;

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
@WebEnv
@Controller
@RequestMapping(value = CTL.WEB_PATH + "/medicine/med")
public class WebMedicineMedController extends BaseController<MedicineMed, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(WebMedicineMedController.class);

	@Autowired
	private MedicineMedService medicineMedService;

	@Override
	public BaseService<MedicineMed, String> getService() {
		return medicineMedService;
	}

	@Override
	public Module<MedicineMed> getModule() {
		return new Module<MedicineMed>(MEDICINE.PROJECT, "medicine.med", CTL.WEB, MedicineMed.class);
	}

}
