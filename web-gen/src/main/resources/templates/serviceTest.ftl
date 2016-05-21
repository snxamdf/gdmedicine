/*
 * 
 *
 * 
 */
package ${packageName}.${moduleId}.service;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yhy.core.constants.PROFILES;
import ${packageName}.${moduleId}.${applicationName};
import ${packageName}.${moduleId}.domain.${className};

/**
 * ${tableName}Service测试.
 * 禁用事务回滚使用@Rollback(false).
 * 
 * @author ${author}
 * @version ${version}
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by ${author}
 * @updated at ${version}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ${applicationName}.class)
@ActiveProfiles({ PROFILES.COMM, PROFILES.JUNIT, PROFILES.DEV })
public class ${className}ServiceTests {

	@Autowired
	private ${className}Service ${beanName}Service;

	@Test
	public void findOne() throws Exception {
		${className} ${beanName} = ${beanName}Service.findOne("1");
		assertNull(${beanName});
	}

}
