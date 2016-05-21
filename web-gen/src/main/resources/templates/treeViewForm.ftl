<!DOCTYPE html>
<html lang="zh-CN" layout:decorator="core/layout/bms.default" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head layout:fragment="head">
<title th:text="${'$'}{state}=='add'?'${tableName}：创建':(${'$'}{state}=='edit'?'${tableName}：修改':'${tableName}：查看')">${tableName}：创建</title>
<meta name="description" content="" />
<meta name="author" content="" />
<meta charset="utf-8" th:substituteby="core/layout/include/bms.head" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#eForm").validate();
	});
</script>
</head>
<body id="main">
	
	<div class="content" layout:fragment="content">
		<div class="ui tabular menu">
			<a class="item" th:href="@{${'$'}{#ctl.path}+'/${tableUrl}/list'}" href="bms.${viewName}.list.html">${tableName}列表</a>
			
			<a class="active item" th:text="${'$'}{state}=='add'?'${tableName}创建':(${'$'}{state}=='edit'?'${tableName}修改':'${tableName}查看')">${tableName}创建</a>
		</div>
		<form id="eForm" th:action="@{${'$'}{#ctl.path}+'/${tableUrl}/save'}" th:object="${'$'}{domain}" action="#" method="post" class="ui error form segment">
			<div th:if="${'$'}{#fields.hasErrors('*')}" class="ui error message">
				<p th:each="error : ${'$'}{#fields.errors('*')}" th:text="${'$'}{error}">Validation error</p>
			</div>
			<div>
				<input id="search_EQ_parentId" name="search_EQ_parentId" type="hidden" th:value="${'$'}{search_EQ_parentId}" />
				<div></div>
				<input id="search_LK_name" name="search_LK_name" type="hidden" th:value="${'$'}{search_LK_name}" />
				<div></div>
				<input type="hidden" id="state" name="state" th:value="${'$'}{state}" />
				<div></div>
				<input type="hidden" id="parentId" name="parentId" th:value="${'$'}{domain.parentId}" />
				<div></div>
				<input type="hidden" th:field="*{id}" />
			</div>
			<div class="required field" th:class="${'$'}{#fields.hasErrors('name')} ? 'required field error' : 'required field'">
				<label>名称</label>
				<div class="ui icon input">
					<input type="text" class="required" th:field="*{name}" />
				</div>
			</div>
			<div class="required field" th:class="${'$'}{#fields.hasErrors('code')} ? 'required field error' : 'required field'">
				<label>编号</label>
				<div class="ui icon input">
					<input type="text" class="required" th:field="*{code}" />
				</div>
			</div>
			<div class="required field" th:class="${'$'}{#fields.hasErrors('ordinal')} ? 'required field error' : 'required field'">
				<label>排序</label>
				<div class="ui icon input">
					<input type="text" class="required" th:field="*{ordinal}" />
				</div>
			</div>
			<#list columns as column>
			<#if column.getIgnore('${tableExtend}') == 'false'>
			<div class="required field" th:class="${'$'}{#fields.hasErrors('${column.beanName}')} ? 'required field error' : 'required field'">
				<label>${column.comment}</label>
				<div class="ui icon input">
					<input type="text" class="required" th:field="*{${column.beanName}}" />
				</div>
			</div>
			</#if>
			</#list>
			<div class="field" th:class="${'$'}{#fields.hasErrors('memo')} ? 'field error' : 'field'">
				<label>备注</label>
				<textarea th:field="*{memo}"></textarea>
			</div>
			<div class="ui buttons">
				<input class="ui positive button" type="submit" value="保存" th:if="${'$'}{state} == 'add' or ${'$'}{state} == 'edit'" />
				<div class="or" th:if="${'$'}{state} == 'add' or ${'$'}{state} == 'edit'"></div>
				<input class="ui button" type="button" value="返回" onclick="history.go(-1)" />
			</div>
		</form>
	</div>
	<div class="footer">
		<div class="ui divider"></div>
		<div class="item">
			Copyright &copy; 2009-2015 <a href="http://github.com/snxamdf" target="_blank">hongyanyang</a>
			
			- Powered By <a href="http://github.com/snxamdf" target="_blank">SXM V2.0</a>
		</div>
	</div>
</body>
</html>