/*
 * 
 *
 * 
 */
package ${packageName}.${moduleId}.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.yhy.core.constants.CTL;
import com.yhy.core.constants.PROFILES;
import ${packageName}.${moduleId}.${applicationName};

/**
 * ${tableName}Controller测试.
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
@WebAppConfiguration
@IntegrationTest("${'$' + '{server.port:8182}'}")
@DirtiesContext
@ActiveProfiles({ PROFILES.WEB, PROFILES.JUNIT, PROFILES.DEV })
public class Web${className}ControllerTests {

	@Value("${'$' + '{server.port}'}")
	private int port;

	@Test
	public void list() throws Exception {
		String url = "http://localhost:" + this.port + CTL.WEB_PATH + "/${tableUrl}/list";
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(url, String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertTrue("Wrong body (title doesn't match):\n" + entity.getBody(), entity.getBody().contains("<title>${tableName}"));
		assertFalse("Wrong body (found layout:fragment):\n" + entity.getBody(), entity.getBody().contains("layout:fragment"));
	}

	@Test
	public void save() throws Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.set("id", "root");
		String url = "http://localhost:" + this.port + CTL.WEB_PATH + "/${tableUrl}/save";
		URI location = new TestRestTemplate().postForLocation(url, map);
		assertTrue("Wrong location:\n" + location, location.toString().contains("localhost:" + this.port));
	}

}
