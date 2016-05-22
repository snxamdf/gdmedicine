/*
 * 
 *
 * 
 */
package com.yhy.medicine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.core.repository.BaseRepository;
import com.yhy.core.service.BaseService;
import com.yhy.medicine.domain.MedicineMedType;
import com.yhy.medicine.repository.MedicineMedTypeRepository;

/**
 * 药品类型表Service.
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@Service
@Transactional
public class MedicineMedTypeService extends BaseService<MedicineMedType, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MedicineMedTypeService.class);

	@Autowired
	private MedicineMedTypeRepository medicineMedTypeRepository;

	@Override
	public BaseRepository<MedicineMedType, String> getRepository() {
		return medicineMedTypeRepository;
	}

}
