/* 
 * 
 *
 * 
 */
package com.yhy.gen.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.yhy.core.domain.Pk;
import com.yhy.core.utils.Strings;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_gen_projects")
public class Projects extends Pk<String> {

	private static final long serialVersionUID = -7746037671571630073L;
	private String code = "";
	private String name = "";
	private String path = "";
	private String pkg = "com.yhy";
	private String mainSrc = "src/main/java";
	private String mainRes = "src/main/resources";
	private String mainApp = "src/main/webapp";
	private String testSrc = "src/test/java";
	private String testRes = "src/test/resources";
	private String views = "src/main/resources/templates";
	private String generate = "false";

	public boolean hasGenerate() {
		if ("true".equals(generate)) {
			return true;
		}
		return false;
	}

	public String getPkg() {
		if (Strings.isBlank(pkg)) {
			return "com.yhy";
		}
		return this.pkg;
	}

	public String getMainSrc() {
		if (Strings.isBlank(mainSrc)) {
			return "src/main/java";
		}
		return this.mainSrc;
	}

	public String getMainRes() {
		if (Strings.isBlank(mainRes)) {
			return "src/main/resources";
		}
		return this.mainRes;
	}

	public String getMainApp() {
		if (Strings.isBlank(mainApp)) {
			return "src/main/webapp";
		}
		return this.mainApp;
	}

	public String getTestSrc() {
		if (Strings.isBlank(testSrc)) {
			return "src/test/java";
		}
		return this.testSrc;
	}

	public String getTestRes() {
		if (Strings.isBlank(testRes)) {
			return "src/test/resources";
		}
		return this.testRes;
	}

	public String getViews() {
		if (Strings.isBlank(views)) {
			return "src/main/resources/templates";
		}
		return this.views;
	}

}
