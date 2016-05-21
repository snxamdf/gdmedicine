/* 
 * 
 *
 * 
 */
package com.gdm.gen.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdm.core.utils.Dates;
import com.gdm.core.utils.FreeMarkers;
import com.gdm.core.utils.Paths;
import com.gdm.core.utils.Strings;
import com.gdm.gen.domain.Columns;
import com.gdm.gen.domain.Modules;
import com.gdm.gen.domain.Projects;
import com.gdm.gen.domain.Tables;
import com.gdm.gen.dto.Project;
import com.gdm.gen.dto.Type;
import com.gdm.gen.repository.ColumnsRepository;
import com.gdm.gen.repository.ModulesRepository;
import com.gdm.gen.repository.ProjectsRepository;
import com.gdm.gen.repository.TablesRepository;
import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成代码服务类
 * 
 * @author YHY
 * @version 2015-01-11
 * @----------------------------------------------------------------------------------------
 * @updated 创建.
 * @updated by YHY
 * @updated at 2015-01-11
 */
@Service
@Transactional
public class GenerateService {

	private static Logger logger = LoggerFactory.getLogger(GenerateService.class);

	private List<Projects> projects = new ArrayList<Projects>();
	private List<Modules> modules = new ArrayList<Modules>();
	private List<Tables> tables = new ArrayList<Tables>();
	private List<Columns> columns = new ArrayList<Columns>();

	@Autowired
	private ProjectsRepository projectsRepository;
	@Autowired
	private ModulesRepository modulesRepository;
	@Autowired
	private TablesRepository tablesRepository;
	@Autowired
	private ColumnsRepository columnsRepository;
	//@Autowired
	//private MenuRepository menuRepository;
	//@Autowired
	//private RoleMenuRepository roleMenuRepository;

	// 代码模板配置
	Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

	/**
	 * 生成domain代码
	 */
	public void domain() {
		generate(Type.domain);
	}

	/**
	 * 生成metamodel代码
	 */
	public void metamodel() {
		generate(Type.metamodel);
	}

	/**
	 * 生成mapper代码
	 */
	public void mapper() {
		generate(Type.mapper);
	}

	/**
	 * 生成mapperTest代码
	 */
	public void mapperTest() {
		generate(Type.mapperTest);
	}

	/**
	 * 生成repository代码
	 */
	public void repository() {
		generate(Type.repository);
	}

	/**
	 * 生成repository测试代码
	 */
	public void repositoryTest() {
		generate(Type.repositoryTest);
	}

	/**
	 * 生成service代码
	 */
	public void service() {
		generate(Type.service);
	}

	/**
	 * 生成service测试代码
	 */
	public void serviceTest() {
		generate(Type.serviceTest);
	}

	/**
	 * 生成controller代码
	 */
	public void controller() {
		generate(Type.bmsController);
		generate(Type.webController);
		//generate(Type.webappController);
		//generate(Type.apisController);
	}

	/**
	 * 生成controller测试代码
	 */
	public void controllerTest() {
		generate(Type.bmsControllerTest);
		generate(Type.webControllerTest);
		//generate(Type.webappControllerTest);
		//generate(Type.apisControllerTest);
	}

	/**
	 * 生成xmlMapper代码
	 */
	public void xmlMapper() {
		generate(Type.xmlMapper);
	}

	/**
	 * 生成viewForm代码
	 */
	public void viewForm() {
		generate(Type.sysViewForm);
		generate(Type.treeViewForm);
	}

	/**
	 * 生成viewList代码
	 */
	public void viewList() {
		generate(Type.sysViewList);
		generate(Type.treeViewList);
	}

	/**
	 * 生成menu代码
	 */
	public void menu() {
		generate(Type.menu);
	}

	/**
	 * 生成代码
	 * 
	 * @param type
	 */
	private void generate(String type) {
		projects = projectsRepository.findAll();
		for (Projects project : projects) {
			if (project.hasGenerate()) {
				modules = modulesRepository.findByProjectId("%" + project.getId() + ",%");
				for (Modules module : modules) {
					if (module.hasGenerate()) {
						module.setProjects(project);
						tables = tablesRepository.findByModuleId("%" + module.getId() + ",%");
						for (Tables table : tables) {
							table.setModules(module);
							if (Type.menu.equals(type)) {
								if (table.hasMenu() && Project.bms.equals(project.getCode())) {
									//menu(type, project, module, table);
								}
								continue;
							} else {
								if (table.hasGenerate()) {
									if (Type.sysViewForm.equals(type) || Type.sysViewList.equals(type)) {
										if ("tree".equals(table.getExtend())) {
											continue;
										}
									}
									if (Type.treeViewForm.equals(type) || Type.treeViewList.equals(type)) {
										if ("sys".equals(table.getExtend())) {
											continue;
										}
									}
									if (Project.web.equals(project.getCode())) {
										if (Type.sysViewForm.equals(type) || Type.sysViewList.equals(type) || Type.treeViewForm.equals(type) || Type.treeViewList.equals(type)) {
											continue;
										}
									}
									columns = columnsRepository.findByTableName(table.getCode());
									render(type, project, module, table, columns);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 使用FreeMarkers模板方式生成代码
	 * 
	 * @param type
	 * @param projects
	 * @param modules
	 * @param tables
	 * @param columns
	 */
	private void render(String type, Projects project, Modules module, Tables table, List<Columns> columns) {
		try {
			cfg.setDirectoryForTemplateLoading(new File(Paths.getProjectPath() + "/" + project.getViews()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Long count = columnsRepository.countDatetime(table.getCode());
		Long sum = columnsRepository.sumDatetime(table.getCode());

		// 定义模板变量
		Map<String, Object> model = Maps.newHashMap();
		model.put("applicationName", Strings.camel(project.getCode(), true) + "Application");
		model.put("packageName", project.getPkg());
		model.put("projectId", project.getCode());
		model.put("projectName", project.getName());
		model.put("moduleId", module.getCode());
		model.put("moduleName", module.getName());
		model.put("tableId", table.getCode());
		model.put("tableName", table.getName());
		model.put("tableUrl", table.getUrl());
		model.put("tablePermit", table.getPermitName());
		model.put("tableExtend", table.getExtend());
		model.put("className", table.getClassName());
		model.put("beanName", table.getBeanName());
		model.put("viewName", table.getViewName());
		model.put("author", module.getAuthor());
		model.put("version", Dates.getDate2());
		model.put("countDatetime", count);
		model.put("sumDatetime", sum);
		model.put("columns", columns);

		try {
			String file = module.getPath(type) + "/" + table.getFile(type);
			Template template = cfg.getTemplate(type + ".ftl");
			String content = FreeMarkers.renderTemplate(template, model);
			writeFile(file, content);
			logger.info(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 将内容写入文件
	 * 
	 * @param content
	 * @param filePath
	 */
	private void writeFile(String filePath, String content) {
		try {
			FileUtils.writeStringToFile(new File(filePath), content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
