/*
 * 
 *
 * 
 */
package com.gdm.gen.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdm.gen.GenApplication;
import com.gdm.gen.domain.Tables;
import com.gdm.core.constants.PROFILES;

/**
 * 表Repository测试类.
 * 
 * @author YHY
 * @version 2015-01-10
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenApplication.class)
@ActiveProfiles({ PROFILES.COMM, PROFILES.JUNIT, PROFILES.DEV })
public class TablesRepositoryTests {

	@Autowired
	private TablesRepository tablesRepository;

	@Test
	public void testFindOne() throws Exception {
		Tables tables = tablesRepository.findOne("sys01001");
		System.out.println(tables.getCode());
	}

	@Test
	public void testFindByModuleId() throws Exception {
		List<Tables> tables = tablesRepository.findByModuleId("sys01,");
		System.out.println(tables.size());
	}

}
