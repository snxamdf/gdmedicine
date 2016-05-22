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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhy.core.annotation.BmsEnv;
import com.yhy.core.constants.CTL;
import com.yhy.core.controller.BaseController;
import com.yhy.core.dto.Module;
import com.yhy.core.dto.Result;
import com.yhy.core.service.BaseService;
import com.yhy.medicine.constants.MEDICINE;
import com.yhy.medicine.domain.MedicineMedType;
import com.yhy.medicine.service.MedicineMedTypeService;

/**
 * 药品类型表Controller.
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
@RequestMapping(value = CTL.BMS_PATH + "/medicine/med/type")
public class BmsMedicineMedTypeController extends BaseController<MedicineMedType, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BmsMedicineMedTypeController.class);

	@Autowired
	private MedicineMedTypeService medicineMedTypeService;

	@Override
	public BaseService<MedicineMedType, String> getService() {
		return medicineMedTypeService;
	}

	@Override
	public Module<MedicineMedType> getModule() {
		return new Module<MedicineMedType>(MEDICINE.PROJECT, "medicine.med.type", CTL.BMS, MedicineMedType.class);
	}

	@ResponseBody
	@RequestMapping("/types")
	public Result<Iterable<MedicineMedType>> getTypes() {
		Result<Iterable<MedicineMedType>> result = new Result<Iterable<MedicineMedType>>();
		Iterable<MedicineMedType> lists = medicineMedTypeService.findAll();
		result.setData(lists);
		return result;
	}
}
