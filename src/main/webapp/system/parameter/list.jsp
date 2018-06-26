<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>系统参数管理</title>
</head>
<body>
<%@ include file="../../frame/head.jsp"%>
<section>
<config:config key="parameter.delete" info="delete"/>
<config:config key="synchronization.type" info="type"/>
<config:config key="synchronization.enable.department" info="enable"/>
<div class="DivGlobal clearfix">
	<%@ include file="../../frame/left.jsp"%>
	<div class="DivMainRight">
	<!--main area-->
	<form id="list" action="list.jspx" method="post">
	<input type="hidden" id="ids" name="ids" value="" />
	<input type="hidden" id="flag" name="flag" value="" />
	<div class="Content">
		<div class="item-title">
			<span style="display:block"><a href="javascript:void(0);" onclick="javascript: create('list', 'detail.jspx');" class="itembtn6">新建</a></span>
			<span style="display:block"><a href="javascript:void(0);" onclick="javascript: edit('list', 'detail.jspx');" class="itembtn4">修改</a></span>
			<c:if test="${delete == 'logical'}">
				<span style="display:block"><a href="javascript:void(0);" onclick="javascript: abandon('list', 'abandon.jspx', 0);" class="itembtn3">启用</a></span>
				<span style="display:block"><a href="javascript:void(0);" onclick="javascript: abandon('list', 'abandon.jspx', 1);" class="itembtn29">弃用</a></span>
			</c:if>
			<c:if test="${delete == 'physical'}">
				<span style="display:block"><a href="javascript:void(0);" onclick="javascript: trash('list', 'remove.jspx');" class="itembtn7">删除</a></span>
			</c:if>
			<c:if test="${delete == 'all' || delete == null}">
				<span style="display:block"><a href="javascript:void(0);" onclick="javascript: abandon('list', 'abandon.jspx', 0);" class="itembtn3">启用</a></span>
				<span style="display:block"><a href="javascript:void(0);" onclick="javascript: abandon('list', 'abandon.jspx', 1);" class="itembtn29">弃用</a></span>
				<span style="display:block"><a href="javascript:void(0);" onclick="javascript: trash('list', 'remove.jspx');" class="itembtn7">删除</a></span>
			</c:if>
			<c:if test="${fn:contains(type, 'client') && enable == 'true'}">
				<span style="display:block"><a href="javascript:void(0);" onclick="javascript: synchronization('list', 'synchronization.jspx');" class="itembtn34">同步</a></span>
			</c:if>
			<span style="display:block"><a href="javascript:void(0);" onclick="javascript: refresh('list', 'list.jspx');" class="itembtn8">刷新</a></span>
		</div>
		<div class="item-title-sub">
			<span>参数名称：<input type="text" id="query_title" name="query_title" value="<c:out value='${query_title}'/>"/></span>
			<span>使用状态：<c:choose><c:when test="${flag == null || fn:length(flag) < 1}"><select id="query_flag" name="query_flag"><option value="-1" <c:if test="${query_flag == -1}">selected</c:if>>--请选择--</option><option value="0" <c:if test="${query_flag == 0}">selected</c:if>>启用</option><option value="1" <c:if test="${query_flag == 1}">selected</c:if>>弃用</option></select></c:when><c:otherwise><select:select id="query_flag" name="query_flag" data="flag" code="code" value="meaning" selected="${query_flag}" top="--请选择--"/></c:otherwise></c:choose></span>
			<span><input type="button" class="is-btn1" value="检索" onclick="javascript: query();"/></span>
		</div>
		<h3 class="mode-tit">系统参数列表</h3>
		<div class="tab-area">
			<table cellpadding="0" cellspacing="0" class="tab-style1">
				<tr>
					<th class="center"><input type="checkbox" onclick="javascript: all_checked(this.checked, 'id')"/></th>
					<th>序号</th>
					<th>参数名称</th>
					<th>参数数值</th>
					<th>使用状态</th>
				</tr>
				<c:set var="i" value="${0}"/>
				<c:forEach items="${page.data}" var="data">
				<c:choose>
					<c:when test="${i % 2 == 0}">
						<tr>
							<td class="center"><input type="checkbox" id="<c:out value='${data.id}'/>" name="id"/></td>
							<td><c:out value="${i + 1}"/></td>
							<td><span class="tab-w1"><c:out value="${data.title}"/></span></td>
							<td><span class="tab-w2"><c:out value="${data.code}"/></span></td>
							<td><c:choose><c:when test="${data.flagmeaning != null}"><c:out value="${data.flagmeaning}"/></c:when><c:otherwise><c:choose><c:when test="${data.flag == 0}"><c:out value="启用"/></c:when><c:otherwise><c:choose><c:when test="${data.flag == 1}"><c:out value="弃用"/></c:when><c:otherwise><c:out value="${data.flag}"/></c:otherwise></c:choose></c:otherwise></c:choose></c:otherwise></c:choose></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="center tds01"><input type="checkbox" id="<c:out value='${data.id}'/>" name="id"/></td>
							<td class="tds01"><c:out value="${i + 1}"/></td>
							<td class="tds01"><span class="tab-w1"><c:out value="${data.title}"/></span></td>
							<td class="tds01"><span class="tab-w2"><c:out value="${data.code}"/></span></td>
							<td class="tds01"><c:choose><c:when test="${data.flagmeaning != null}"><c:out value="${data.flagmeaning}"/></c:when><c:otherwise><c:choose><c:when test="${data.flag == 0}"><c:out value="启用"/></c:when><c:otherwise><c:choose><c:when test="${data.flag == 1}"><c:out value="弃用"/></c:when><c:otherwise><c:out value="${data.flag}"/></c:otherwise></c:choose></c:otherwise></c:choose></c:otherwise></c:choose></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:set var="i" value="${i + 1}"/>
				</c:forEach>
			</table>
			<!--page-->
			<page:pagination totalCountRequired="false" pageInfoRequired="true" dispersed="true" dispreadLevel="5" pageChooseRequired="false" pageStyle="pagination" currPageStyle="number  current" nonCurrPageStyle="number" emptyInfo="true"/>
			<!--//page-->
		</div>
	</div>
	</form>
	<!--//main area-->
	</div>
</div>
</section>
<%@ include file="../../frame/foot.jsp"%>
</body>
</html>