<!DOCTYPE html>
<html lang="zh-CN" layout:decorator="core/layout/bms.default" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head layout:fragment="head">
<title>${tableName}：列表</title>
<meta name="description" content="" />
<meta name="author" content="" />
<meta charset="utf-8" th:substituteby="core/layout/include/bms.head" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/semantic/1.10.4/semantic.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/jquery-ui/1.11.2/css/jquery-ui.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/jquery-ui/1.11.2/css/jquery-ui.structure.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/jquery-ui/1.11.2/css/jquery-ui.theme.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/jquery-ui-timepicker/1.5.0/css/jquery-ui-timepicker-addon.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/jquery-ztree/3.5.17/css/zTreeStyle.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/jquery-qtip/2.2.1/css/jquery.qtip.min.css" />
<link th:if="false" rel="stylesheet" href="../../../../../../isy-core/src/main/resources/static/core/common/css/common.css" />
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery/1.11.2/js/jquery.min.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/semantic/1.10.4/semantic.min.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-ui/1.11.2/js/jquery-ui.min.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-ui/1.11.2/js/datepicker-zh-CN.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-ui-timepicker/1.5.0/js/jquery-ui-timepicker-addon.min.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-ui-timepicker/1.5.0/js/jquery-ui-timepicker-zh-CN.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-ztree/3.5.17/js/jquery.ztree.all.min.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-validation/1.13.1/jquery.validate.min.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-validation/1.13.1/jquery.validate.defaults.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-validation/1.13.1/jquery.validate.methods.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-validation/1.13.1/messages_zh.min.js"></script>
<script th:if="false" src="../../../../../../isy-core/src/main/resources/static/core/jquery-qtip/2.2.1/js/jquery.qtip.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function action(type, id) {
		$("#searchForm").attr("action", ctl.path + "/${tableUrl}/" + type);
		if (type == "list") {
			$("#pageNo").val(1); // 点击名称查询时：pageNo=1
			$("#search_EQ_parentId").val(id);
		} else {
			$("#id").val(id);
		}
		$("#searchForm").submit();
		return false;
	}
	function deletion(id) {
		$('#deletionOkBtn').click(function() {
			action("deletion", id);
		});
		$('#deletionDialog').modal('show');
	}
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
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
			<a class="active item">${tableName}列表</a>
			<div th:if="false" class="dn"></div>
			<a class="item" sec:authorize="hasAuthority('${tablePermit}:add')" href="bms.${viewName}.form.html" th:href="@{|javascript:action('add')|}">${tableName}创建</a>
		</div>
		<form id="searchForm" class="ui form small message" th:action="@{${'$'}{#ctl.path}+'/${tableUrl}/list'}" action="#" method="post">
			<div>
				<input id="id" name="id" type="hidden" />
				<div th:if="false" class="dn"></div>
				<input id="pageNo" name="pageNo" type="hidden" th:value="${'$'}{page.number + 1}" />
				<div th:if="false" class="dn"></div>
				<input id="pageSize" name="pageSize" type="hidden" th:value="${'$'}{page.size}" />
				<div th:if="false" class="dn"></div>
				<input id="search_EQ_parentId" name="search_EQ_parentId" type="hidden" th:value="${'$'}{search_EQ_parentId == null ? 'root' : search_EQ_parentId}" />
			</div>
			<div class="fields">
				<div class="inline field">
					<label>名称</label>
					<div th:if="false" class="dn"></div>
					<input type="text" id="search_LK_name" name="search_LK_name" th:value="${'$'}{search_LK_name}" />
				</div>
				<div class="field">
					<input class="ui small positive button" type="submit" value="查询" />
				</div>
			</div>
		</form>
		<div th:include="core/modal/bms.parents">
			<div class="ui small message">
				<div class="ui breadcrumb"></div>
			</div>
		</div>
		<div th:include="core/modal/bms.message">
			<div class="ui positive message">
				<i class="close icon"></i>
				<div class="header">提示信息.</div>
			</div>
		</div>
		<table class="ui celled striped table">
			<thead>
				<tr>
					<th class="w300">标识</th>
					<th>名称</th>
					<th>编号</th>
					<th>排序</th>
					<#list columns as column>
					<#if column.getIgnore('${tableExtend}') == 'false'>
					<th>${column.comment}</th>
					</#if>
					</#list>
					<th>备注</th>
					<th>创建时间</th>
					<th>更新时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr th:each="domain : ${'$'}{page.content}">
					<td><a href="bms.${viewName}.form.html" th:text="${'$'}{domain.id}" th:href="@{|javascript:action('view','${'$'}{domain.id}')|}">1</a></td>
					<td><a href="bms.${viewName}.list.html" th:text="${'$'}{domain.name}" th:href="@{|javascript:action('list','${'$'}{domain.id}')|}">1</a></td>
					<td th:text="${'$'}{domain.code}">1010</td>
					<td th:text="${'$'}{domain.ordinal}">10</td>
					<#list columns as column>
					<#if column.getIgnore('${tableExtend}') == 'false'>
					<#if column.javaType == 'Date'>
					<td th:text="${'$'}{domain.${column.beanName}==null?'':#dates.format(domain.${column.beanName},'yyyy-MM-dd HH:mm:ss')}">2014-07-16 17:56:12</td>
					<#else>
					<td th:text="${'$'}{domain.${column.beanName}}">1</td>
					</#if>
					</#if>
					</#list>
					<td th:text="${'$'}{domain.memo}">1</td>
					<td th:text="${'$'}{domain.created==null?'':#dates.format(domain.created,'yyyy-MM-dd HH:mm:ss')}">2014-07-16 17:56:12</td>
					<td th:text="${'$'}{domain.modified==null?'':#dates.format(domain.modified,'yyyy-MM-dd HH:mm:ss')}">2014-07-18 17:58:16</td>
					<td>
						<div th:if="false" class="dn"></div> <a sec:authorize="hasAuthority('${tablePermit}:edit')" href="#" th:href="@{|javascript:action('edit','${'$'}{domain.id}')|}">修改</a>
						<div th:if="false" class="dn"></div> <a sec:authorize="hasAuthority('${tablePermit}:disable')" href="#" th:href="@{|javascript:deletion('${'$'}{domain.id}')|}">删除</a>
					</td>
				</tr>
				<tr th:if="${'$'}{page.content.empty}">
					<td colspan="99">没有记录</td>
				</tr>
			</tbody>
		</table>
		<div class="ui pagination menu" th:utext="${'$'}{pagination}">
			<a class="icon item"> <i class="left arrow icon"></i>
			</a> <a class="active item"> 1 </a>
			<div class="disabled item">...</div>
			<a class="item"> 10 </a> <a class="item"> 11 </a> <a class="item"> 12 </a> <a class="icon item"> <i class="right arrow icon"></i>
			</a>
			<div class="disabled item">
				<div class="mt-5 mb-5">
					当前 <input class="w40" type="text" value="1" /> / <input class="w40" type="text" value="10" /> 条，共 2 条
				</div>
			</div>
		</div>
		<div th:include="core/modal/bms.deletion" th:with="id='deletionBtn'">
			<div class="ui deletion small modal">
				<i class="close icon"></i>
				<div class="header">系统提示</div>
				<div class="content">
					<p>确认要删除该记录吗？</p>
				</div>
				<div class="actions">
					<div id="deletionBtn" class="ui positive button">确定</div>
					<div class="ui button">取消</div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="ui divider"></div>
		<div class="item">
			Copyright &copy; 2009-2015 <a href="http://www.creditease.com" target="_blank">CreditEase</a>
			<div th:if="false" class="dn"></div>
			- Powered By <a href="http://www.isheyuan.com" target="_blank">ISY V2.0</a>
		</div>
	</div>
</body>
</html>