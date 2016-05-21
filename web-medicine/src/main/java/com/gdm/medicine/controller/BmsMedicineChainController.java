/*
 * 
 *
 * 
 */
package com.gdm.medicine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdm.core.annotation.BmsEnv;
import com.gdm.core.constants.CTL;
import com.gdm.core.controller.BaseController;
import com.gdm.core.dto.Module;
import com.gdm.core.service.BaseService;
import com.gdm.medicine.constants.MEDICINE;
import com.gdm.medicine.domain.MedicineChain;
import com.gdm.medicine.service.MedicineChainService;

/**
 * 医药管理表Controller.
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
@RequestMapping(value = CTL.WEB_PATH + "/medicine/chain")
public class BmsMedicineChainController extends BaseController<MedicineChain, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsMedicineChainController.class);

	@Autowired
	private MedicineChainService medicineChainService;

	@Override
	public BaseService<MedicineChain, String> getService() {
		return medicineChainService;
	}

	@Override
	public Module<MedicineChain> getModule() {
		return new Module<MedicineChain>(MEDICINE.PROJECT, "medicine.chain", CTL.WEB, MedicineChain.class);
	}

}
