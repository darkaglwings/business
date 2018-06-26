<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>登录密码管理</title>
<script type="text/javascript" src="../../js/system/access/password.js"></script>
</head>
<body>
<%@ include file="../../frame/head.jsp"%>
<config:config key="login.password.allownull" info="allowNull"/>
<section>
<div class="DivGlobal clearfix">
	<%@ include file="../../frame/left.jsp"%>
	<div class="DivMainRight">
	<!--main area-->
	<form id="detail" action="modify.jspx" method="post">
	<div class="Content nontit">
		<div class="item-title">
			<span><a href="javascript:void(0);" onclick="javascript: chgpwd();" class="itembtn10">保存</a></span>
			<span><a href="javascript:void(0);" onclick="javascript: cancel('detail');" class="itembtn11">重填</a></span>
		</div>	
		<h3 class="mode-tit">登录密码修改</h3>
		<div class="tab-area">
			<input type ="hidden" id="id" name="id" value="<c:out value='${user.id}'/>"/>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name" style="width:25%">用户名称：</td>
					<td style="width:75%"><c:out value='${user.title}'/></td>
				</tr>
				<tr>
					<td class="item-name" style="width:25%">用户名：</td>
					<td style="width:75%"><c:out value='${user.username}'/></td>
				</tr>
				<tr>
					<td class="item-name" style="width:25%">输入原密码：</td>
					<td style="width:75%"><input type="password" id="password" name="password" value="" style="width:99%" onkeyup="javascript: ajax('POST', 'validate.jspx', 'id=${user.id}&password=' + this.value, true, 'validate(msg);');" <c:if test="${allowNull != 'true'}">required</c:if>/><span id="validate"/></td>
				</tr>
				<tr>
					<td class="item-name" style="width:25%">输入新密码：</td>
					<td style="width:75%"><input type="password" id="newpassword" name="newpassword" value="" style="width:99%" onkeyup="javascript: revalidate();" <c:if test="${allowNull != 'true'}">required</c:if>/></td>
				</tr>
				<tr>
					<td class="item-name" style="width:25%">再次输入新密码：</td>
					<td style="width:75%"><input type="password" id="repassword" name="repassword" value="" style="width:99%" onkeyup="javascript: revalidate();" <c:if test="${allowNull != 'true'}">required</c:if>/><span id="revalidate"/></td>
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