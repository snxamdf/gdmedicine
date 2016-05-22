/*
 * 
 *
 * 
 */
package com.yhy.medicine.mapper;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yhy.core.constants.PROFILES;
import com.yhy.medicine.MedicineApplication;
import com.yhy.medicine.domain.MedicineMedSales;

/**
 * 药品销售记录表Mapper测试.
 * 禁用事务回滚使用@Rollback(false).
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MedicineApplication.class)
@ActiveProfiles({ PROFILES.COMM, PROFILES.JUNIT, PROFILES.DEV })
public class MedicineMedSalesMapperTests {

	@Autowired
	private MedicineMedSalesMapper medicineMedSalesMapper;

	@Test
	public void findById() throws Exception {
		MedicineMedSales medicineMedSales = medicineMedSalesMapper.findById("1");
		assertNull(medicineMedSales);
	}

}
