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

import com.yhy.core.annotation.BmsEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.controller.BaseController;
import com.yhy.core.dto.Module;
import com.yhy.core.service.BaseService;
import com.yhy.medicine.constants.MEDICINE;
import com.yhy.medicine.domain.MedicineChain;
import com.yhy.medicine.service.MedicineChainService;

/**
 * 医药管理表Controller.
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
@RequestMapping(value = CTL.BMS_PATH + "/medicine/chain")
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
		return new Module<MedicineChain>(MEDICINE.PROJECT, "medicine.chain", CTL.BMS, MedicineChain.class);
	}

}
