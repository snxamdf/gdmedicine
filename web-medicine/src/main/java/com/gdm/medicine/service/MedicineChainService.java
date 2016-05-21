/*
 * 
 *
 * 
 */
package com.gdm.medicine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdm.core.repository.BaseRepository;
import com.gdm.core.service.BaseService;
import com.gdm.medicine.domain.MedicineChain;
import com.gdm.medicine.repository.MedicineChainRepository;

/**
 * 医药管理表Service.
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
@Service
@Transactional
public class MedicineChainService extends BaseService<MedicineChain, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MedicineChainService.class);

	@Autowired
	private MedicineChainRepository medicineChainRepository;

	@Override
	public BaseRepository<MedicineChain, String> getRepository() {
		return medicineChainRepository;
	}

}
