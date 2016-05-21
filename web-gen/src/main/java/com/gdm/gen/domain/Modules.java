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
import com.gdm.core.utils.Paths;
import com.gdm.gen.dto.Type;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_gen_modules")
public class Modules extends Pk<String> {

	private static final long serialVersionUID = 1053442803280876201L;
	@Transient
	private Projects projects = null;
	private String projectId = "";
	private String code = "";
	private String name = "";
	private String author = "";
	private String generate = "false";

	public boolean hasGenerate() {
		if ("true".equals(generate)) {
			return true;
		}
		return false;
	}

	public String getPath(String type) {
		String end = type;
		if (Type.metamodel.equals(type)) {
			end = Type.domain;
		}
		if (Type.mapperTest.equals(type) || Type.xmlMapper.equals(type)) {
			end = Type.mapper;
		}
		if (Type.repositoryTest.equals(type)) {
			end = Type.repository;
		}
		if (Type.serviceTest.equals(type)) {
			end = Type.service;
		}
		if (Type.bmsController.equals(type) || Type.bmsControllerTest.equals(type) || Type.webController.equals(type) || Type.webControllerTest.equals(type) || Type.webappController.equals(type) || Type.webappControllerTest.equals(type) || Type.apisController.equals(type) || Type.apisControllerTest.equals(type)) {
			end = Type.controller;
		}
		if (Type.sysViewForm.equals(type) || Type.sysViewList.equals(type) || Type.treeViewForm.equals(type) || Type.treeViewList.equals(type)) {
			end = "";
		}
		String path = projects.getMainSrc();
		if (Type.repositoryTest.equals(type) || Type.mapperTest.equals(type) || Type.serviceTest.equals(type) || Type.bmsControllerTest.equals(type) || Type.webControllerTest.equals(type) || Type.webappControllerTest.equals(type) || Type.apisControllerTest.equals(type)) {
			path = projects.getTestSrc();
		}
		if (Type.xmlMapper.equals(type)) {
			path = projects.getMainRes();
		}
		if (Type.sysViewForm.equals(type) || Type.sysViewList.equals(type) || Type.treeViewForm.equals(type) || Type.treeViewList.equals(type)) {
			path = projects.getViews();
		}
		String pkg = projects.getPkg().replace(SYMBOL.DOT, SYMBOL.SLANT) + SYMBOL.SLANT;
		if (Type.sysViewForm.equals(type) || Type.sysViewList.equals(type) || Type.treeViewForm.equals(type) || Type.treeViewList.equals(type)) {
			pkg = "";
		}
		return Paths.getProjectPathByRelative(SYMBOL.DOT + SYMBOL.DOT + SYMBOL.SLANT + projects.getId() + SYMBOL.SLANT + path + SYMBOL.SLANT + pkg + code.replace(SYMBOL.DOT, SYMBOL.SLANT) + SYMBOL.SLANT + end);
	}

}
