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
import com.yhy.medicine.domain.MedicineMedSales;
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
@WebEnv
@Controller
@RequestMapping(value = CTL.WEB_PATH + "/medicine/med/sales")
public class WebMedicineMedSalesController extends BaseController<MedicineMedSales, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(WebMedicineMedSalesController.class);

	@Autowired
	private MedicineMedSalesService medicineMedSalesService;

	@Override
	public BaseService<MedicineMedSales, String> getService() {
		return medicineMedSalesService;
	}

	@Override
	public Module<MedicineMedSales> getModule() {
		return new Module<MedicineMedSales>(MEDICINE.PROJECT, "medicine.med.sales", CTL.WEB, MedicineMedSales.class);
	}

}
