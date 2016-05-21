/*
 * 
 *
 * 
 */
package com.yhy.core.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA配置.
 * 
 * @author YHY
 * @version 2015-05-13
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-05-13
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EntityScan(basePackages = "com.yhy.*.domain")
@EnableJpaRepositories(basePackages = "com.yhy.*.repository")
class JpaConfig {

	@Autowired
	EntityManagerFactory emf;
	@Autowired
	private DataSource dataSource;

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(emf);
		tm.setDataSource(dataSource);
		return tm;
	}

}
