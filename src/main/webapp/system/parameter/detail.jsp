<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>系统参数管理</title>
</head>
<body>
<%@ include file="../../frame/head.jsp"%>
<script type="text/javascript" src="../../js/system/parameter/detail.js" ></script>
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
		<h3 class="mode-tit">系统参数详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${parameter.id}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${parameter.flag}'/>"/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">参数名称：</td>
					<td><input type="text" id="title" name="title" value="<c:out value='${parameter.title}'/>" style="width:99%" required/></td>
					<td class="item-name">参数内容：</td>
					<td><input type="text" id="code" name="code" value="<c:out value='${parameter.code}'/>" style="width:99%" required/></td>
				</tr>
				<tr>
					<td class="item-name">备注：</td>
					<td colspan="3"><textarea id="remark" name="remark" style="width:99%" rows="3"><c:out value='${parameter.remark}'/></textarea></td>
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