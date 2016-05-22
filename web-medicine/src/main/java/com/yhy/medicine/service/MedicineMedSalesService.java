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
import com.yhy.medicine.domain.MedicineMedSales;
import com.yhy.medicine.repository.MedicineMedSalesRepository;

/**
 * 药品销售记录表Service.
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
public class MedicineMedSalesService extends BaseService<MedicineMedSales, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MedicineMedSalesService.class);

	@Autowired
	private MedicineMedSalesRepository medicineMedSalesRepository;

	@Override
	public BaseRepository<MedicineMedSales, String> getRepository() {
		return medicineMedSalesRepository;
	}

}
