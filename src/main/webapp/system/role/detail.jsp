<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>系统角色管理</title>
<script type="text/javascript" src="../../js/system/role/detail.js" ></script>
</head>
<body>
<%@ include file="../../frame/head.jsp"%>
<section>
<div class="DivGlobal clearfix">
	<%@ include file="../../frame/left.jsp"%>
	<div class="DivMainRight">
	<!--main area-->
	<form id="detail" action="modify.jspx" method="post">
	<div class="Content nontit">
		<div class="item-title">
			<span><a href="javascript:void(0);" onclick="javascript: save('detail', true);" class="itembtn10">保存</a></span>
			<span><a href="javascript:void(0);" onclick="javascript: cancel('detail');" class="itembtn11">重填</a></span>
			<span style="background:none;"><a href="javascript:void(0);" onclick="javascript: back('list.jspx');" class="itembtn12">返回</a></span>
		</div>	
		<h3 class="mode-tit">系统角色详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${role.id}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${role.flag}'/>"/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">角色名称：</td>
					<td><input type="text" id="title" name="title" value="<c:out value='${role.title}'/>" style="width:99%" required/></td>
					<td class="item-name">角色等级：</td>
					<td><input type="number" id="rank" name="rank" min="0" value="<c:out value='${role.rank}'/>" class="number" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">备注：</td>
					<td colspan="3"><textarea id="remark" name="remark" style="width:99%" rows="3"><c:out value='${role.remark}'/></textarea></td>
				</tr>
			</table>
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