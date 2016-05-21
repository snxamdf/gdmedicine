/*
 * 
 *
 * 
 */
package com.gdm.core.configuration;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.gdm.core.thymeleaf.CtlDialect;
import com.gdm.core.thymeleaf.VerDialect;

/**
 * Thymeleaf配置.
 * 
 * @author YHY
 * @version 2014-09-19
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-09-19
 */
@Configuration
class ThymeleafConfig {

	@Autowired
	private Collection<ITemplateResolver> templateResolvers = Collections.emptySet();

	@Autowired(required = false)
	private Collection<IDialect> dialects = Collections.emptySet();

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		for (ITemplateResolver templateResolver : this.templateResolvers) {
			engine.addTemplateResolver(templateResolver);
		}
		for (IDialect dialect : this.dialects) {
			engine.addDialect(dialect);
		}
		engine.addDialect(new CtlDialect());
		engine.addDialect(new VerDialect());
		return engine;
	}

}
