/*
 * 
 *
 * 
 */
package com.gdm.gen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gdm.gen.dto.Project;
import com.gdm.gen.service.GenerateService;
import com.gdm.gen.service.InitService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.gdm.gen" })
@EntityScan(basePackages = "com.gdm.gen.domain")
@EnableJpaRepositories(basePackages = "com.gdm.gen.repository")
public class GenApplication implements CommandLineRunner {

	@Autowired
	private InitService initService;
	@Autowired
	private GenerateService generateService;

	@Override
	public void run(String... args) {
		try {
			initService.initData(Project.medicine);
			generateService.domain();
			generateService.metamodel();
			generateService.mapper();
			generateService.mapperTest();
			generateService.repository();
			generateService.repositoryTest();
			generateService.service();
			generateService.serviceTest();
			generateService.controller();
			generateService.controllerTest();
			generateService.xmlMapper();
			generateService.viewList();
			generateService.viewForm();
			System.out.println("=======================gen:end=======================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GenApplication.class, args);
	}

}
