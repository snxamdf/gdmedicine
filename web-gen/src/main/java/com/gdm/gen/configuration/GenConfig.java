/*
 * 
 *
 * 
 */
package com.gdm.gen.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * gen配置.
 * 
 * @author YHY
 * @version 2015-03-10
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-03-10
 */
@Configuration
class GenConfig {

	@Value("${spring.datasource.driverClassName:com.mysql.jdbc.Driver}")
	private String driverClassName;

	@Value("${spring.datasource.url:jdbc:mysql://10.100.30.152:3306/ISY_DEV_DB?useUnicode=true&characterEncoding=utf-8}")
	private String url;

	@Value("${spring.datasource.username:isy_dev}")
	private String username;

	@Value("${spring.datasource.password:29kjY89x}")
	private String password;

	@Bean
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDefaultAutoCommit(true);
		dataSource.setValidationQuery("SELECT 1");

		dataSource.setMaxActive(20);
		dataSource.setMaxIdle(8);
		dataSource.setMinIdle(8);
		dataSource.setTestOnBorrow(false);

		return dataSource;
	}

}
