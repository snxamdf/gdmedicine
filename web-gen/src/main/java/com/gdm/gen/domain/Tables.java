/* 
 * 
 *
 * 
 */
package com.gdm.gen.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.gdm.core.constants.SYMBOL;
import com.gdm.core.domain.Pk;
import com.gdm.core.utils.Strings;
import com.gdm.gen.dto.Type;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_gen_tables")
public class Tables extends Pk<String> {

	private static final long serialVersionUID = 4227741354826687691L;
	@Transient
	private Modules modules = null;
	private String code = "";
	private String name = "";
	private String comments = "";
	private String extend = "sys";
	private String moduleId = "";
	private String ignoreCol = "";
	private String generate = "false";
	private String menu = "false";

	public boolean hasGenerate() {
		if ("true".equals(generate)) {
			return true;
		}
		return false;
	}

	public boolean hasMenu() {
		if ("true".equals(menu)) {
			return true;
		}
		return false;
	}

	public String getClassName(String type) {
		if (Type.domain.equals(type)) {
			return getClassName();
		} else if (Type.metamodel.equals(type)) {
			return getClassName() + SYMBOL.UNDERLINE;
		} else if (Type.mapper.equals(type)) {
			return getClassName() + "Mapper";
		} else if (Type.mapperTest.equals(type)) {
			return getClassName() + "MapperTests";
		} else if (Type.repository.equals(type)) {
			return getClassName() + "Repository";
		} else if (Type.repositoryTest.equals(type)) {
			return getClassName() + "RepositoryTests";
		} else if (Type.service.equals(type)) {
			return getClassName() + "Service";
		} else if (Type.serviceTest.equals(type)) {
			return getClassName() + "ServiceTests";
		} else if (Type.bmsController.equals(type)) {
			return "Bms" + getClassName() + "Controller";
		} else if (Type.bmsControllerTest.equals(type)) {
			return "Bms" + getClassName() + "ControllerTests";
		} else if (Type.webController.equals(type)) {
			return "Web" + getClassName() + "Controller";
		} else if (Type.webControllerTest.equals(type)) {
			return "Web" + getClassName() + "ControllerTests";
		} else if (Type.webappController.equals(type)) {
			return "Webapp" + getClassName() + "Controller";
		} else if (Type.webappControllerTest.equals(type)) {
			return "Webapp" + getClassName() + "ControllerTests";
		} else if (Type.apisController.equals(type)) {
			return "Apis" + getClassName() + "Controller";
		} else if (Type.apisControllerTest.equals(type)) {
			return "Apis" + getClassName() + "ControllerTests";
		} else if (Type.xmlMapper.equals(type)) {
			return getClassName() + "Mapper";
		} else if (Type.sysViewForm.equals(type) || Type.treeViewForm.equals(type)) {
			return "bms." + getViewName() + ".form";
		} else if (Type.sysViewList.equals(type) || Type.treeViewList.equals(type)) {
			return "bms." + getViewName() + ".list";
		}
		return getClassName();
	}

	public String getFile(String type) {
		if (Type.sysViewForm.equals(type) || Type.treeViewForm.equals(type)) {
			return getClassName(type) + ".html";
		} else if (Type.sysViewList.equals(type) || Type.treeViewList.equals(type)) {
			return getClassName(type) + ".html";
		} else if (Type.xmlMapper.equals(type)) {
			return getClassName(type) + ".xml";
		} else {
			return getClassName(type) + ".java";
		}
	}

	public String getUrl() {
		String tname = Strings.replace(getTableName(), SYMBOL.DOT, SYMBOL.SLANT);
		if (Strings.startsWith(code, "t_sys_") || Strings.startsWith(code, "v_sys_")) {
			return "sys/" + Strings.replace(tname, SYMBOL.UNDERLINE, SYMBOL.SLANT);
		} else {
			return Strings.replace(tname, SYMBOL.UNDERLINE, SYMBOL.SLANT);
		}
	}

	public String getClassName() {
		return Strings.camel(getTableName(), true);
	}

	public String getBeanName() {
		return Strings.camel(getTableName());
	}

	public String getViewName() {
		return Strings.replace(getTableName(), SYMBOL.UNDERLINE, SYMBOL.DOT);
	}

	public String getPermitName() {
		String tname = Strings.replace(getTableName(), SYMBOL.DOT, SYMBOL.COLON);
		if (Strings.startsWith(code, "t_sys_") || Strings.startsWith(code, "v_sys_")) {
			return "sys:" + Strings.replace(tname, SYMBOL.UNDERLINE, SYMBOL.COLON);
		} else {
			return Strings.replace(tname, SYMBOL.UNDERLINE, SYMBOL.COLON);
		}
	}

	private String getTableName() {
		String tmp = code;
		if (Strings.startsWith(tmp, "t_")) {
			tmp = Strings.replaceOnce(tmp, "t_", "");
		}
		if (Strings.startsWith(tmp, "v_")) {
			tmp = Strings.replaceOnce(tmp, "v_", "");
		}
		if (Strings.startsWith(tmp, "sys_")) {
			tmp = Strings.replaceOnce(tmp, "sys_", "");
		}
		return tmp;
	}

}
