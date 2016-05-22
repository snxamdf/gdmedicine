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
import com.yhy.medicine.domain.MedicineMed;
import com.yhy.medicine.repository.MedicineMedRepository;

/**
 * 药品管理表Service.
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
public class MedicineMedService extends BaseService<MedicineMed, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MedicineMedService.class);

	@Autowired
	private MedicineMedRepository medicineMedRepository;

	@Override
	public BaseRepository<MedicineMed, String> getRepository() {
		return medicineMedRepository;
	}

}
