/*
 * 
 *
 * 
 */
package com.yhy.core.dto;

import lombok.Data;

import com.yhy.core.constants.CTL;
import com.yhy.core.constants.SYMBOL;
import com.yhy.core.utils.Strings;

/**
 * 模块DTO.
 * 
 * @author YHY
 * @version 2014-12-26
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-12-26
 */
@Data
public class Module<T> {

	private String project; // 子项目名称，比如：sys、core、cms
	private String name; // 模块名称，比如：user、role、dict
	private String genre; // 类型，比如：bms、web
	private Class<T> entityClass;
	private Boolean permit;

	public Module(String project, String name, String genre, Class<T> entityClass) {
		super();
		this.project = project;
		this.name = name;
		this.genre = genre;
		this.entityClass = entityClass;
		this.permit = false;
	}

	public Module(String project, String name, String genre, Class<T> entityClass, Boolean permit) {
		super();
		this.project = project;
		this.name = name;
		this.genre = genre;
		this.entityClass = entityClass;
		this.permit = permit;
	}

	public String getTmplName() {
		return project + SYMBOL.SLANT + genre + SYMBOL.DOT + name;
	}

	public String getRequestMapping() {
		if (CTL.BMS.equals(genre)) {
			if ("sys".equals(project)) {
				return CTL.BMS_PATH + SYMBOL.SLANT + project + SYMBOL.SLANT + Strings.replace(name, SYMBOL.DOT, SYMBOL.SLANT);
			} else {
				return CTL.BMS_PATH + SYMBOL.SLANT + Strings.replace(name, SYMBOL.DOT, SYMBOL.SLANT);
			}
		} else {
			if ("sys".equals(project)) {
				return CTL.WEB_PATH + project + SYMBOL.SLANT + Strings.replace(name, SYMBOL.DOT, SYMBOL.SLANT);
			} else {
				return CTL.WEB_PATH + SYMBOL.SLANT + Strings.replace(name, SYMBOL.DOT, SYMBOL.SLANT);
			}
		}
	}

}
