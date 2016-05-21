<!DOCTYPE html>
<html lang="zh-CN" layout:decorator="core/layout/bms.default" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head layout:fragment="head">
<title th:text="${'$'}{state}=='add'?'${tableName}：创建':(${'$'}{state}=='edit'?'${tableName}：修改':'${tableName}：查看')">${tableName}：创建</title>
<meta name="description" content="" />
<meta name="author" content="" />
<meta charset="utf-8" th:substituteby="core/layout/include/bms.head" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/semantic/1.10.4/semantic.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/jquery-ui/1.11.2/css/jquery-ui.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/jquery-ui/1.11.2/css/jquery-ui.structure.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/jquery-ui/1.11.2/css/jquery-ui.theme.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/jquery-ui-timepicker/1.5.0/css/jquery-ui-timepicker-addon.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/jquery-ztree/3.5.17/css/zTreeStyle.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/jquery-qtip/2.2.1/css/jquery.qtip.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../web-core/src/main/resources/static/core/common/css/common.css" />
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery/1.11.2/js/jquery.min.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/semantic/1.10.4/semantic.min.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-ui/1.11.2/js/jquery-ui.min.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-ui/1.11.2/js/datepicker-zh-CN.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-ui-timepicker/1.5.0/js/jquery-ui-timepicker-addon.min.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-ui-timepicker/1.5.0/js/jquery-ui-timepicker-zh-CN.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-ztree/3.5.17/js/jquery.ztree.all.min.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-validation/1.13.1/jquery.validate.min.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-validation/1.13.1/jquery.validate.defaults.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-validation/1.13.1/jquery.validate.methods.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-validation/1.13.1/messages_zh.min.js"></script>
<script th:if="false" src="../../../../../../web-core/src/main/resources/static/core/jquery-qtip/2.2.1/js/jquery.qtip.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#eForm").validate();
	});
</script>
</head>
<body id="main">
	<div class="header">
		<div class="ui fixed menu inverted navbar">
			<a class="item" href="/">
				<div th:if="false" class="dn"></div> <i class="home icon"></i> 爱社员后台
			</a>
			<div th:if="false" class="dn"></div>
			<a class="item" href="#">
				<div th:if="false" class="dn"></div> <i class="settings icon"></i> 系统管理
			</a>
			<div class="right menu">
				<div class="ui pointing dropdown item user">
					<span>您好，管理员</span> <i class="dropdown icon"></i>
					<div class="menu">
						<a class="item"><i class="edit icon"></i>个人信息</a>
						<div th:if="false" class="dn"></div>
						<a class="item"><i class="privacy icon"></i>修改密码</a>
					</div>
				</div>
				<a class="item" href="/login?logout">
					<div th:if="false" class="dn"></div> <i class="sign out icon"></i> 退出
				</a>
			</div>
		</div>
	</div>
	<div class="content" layout:fragment="content">
		<div class="ui tabular menu">
			<a class="item" th:href="@{${'$'}{#ctl.path}+'/${tableUrl}/list'}" href="bms.${viewName}.list.html">${tableName}列表</a>
			<div th:if="false" class="dn"></div>
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
			<div th:if="false" class="dn"></div>
			- Powered By <a href="http://github.com/snxamdf" target="_blank">SXM V2.0</a>
		</div>
	</div>
</body>
</html>