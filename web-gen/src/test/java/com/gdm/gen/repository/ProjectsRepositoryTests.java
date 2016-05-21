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
import com.gdm.gen.domain.Projects;
import com.gdm.core.constants.PROFILES;

/**
 * 项目Repository测试类.
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
public class ProjectsRepositoryTests {

	@Autowired
	private ProjectsRepository projectsRepository;

	@Test
	public void testFindOne() throws Exception {
		Projects projects = projectsRepository.findOne("isy-sys");
		System.out.println(projects.getCode());
	}

	@Test
	public void testFindAll() throws Exception {
		List<Projects> projects = projectsRepository.findAll();
		System.out.println(projects.size());
	}

}
