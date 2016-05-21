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
import com.gdm.gen.domain.Modules;
import com.gdm.core.constants.PROFILES;

/**
 * 模块Repository测试类.
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
public class ModulesRepositoryTests {

	@Autowired
	private ModulesRepository modulesRepository;

	@Test
	public void testFindOne() throws Exception {
		Modules modules = modulesRepository.findOne("sys01");
		System.out.println(modules.getCode());
	}

	@Test
	public void testFindByProjectId() throws Exception {
		List<Modules> modules = modulesRepository.findByProjectId("isy-sys,");
		System.out.println(modules.size());
	}

}
