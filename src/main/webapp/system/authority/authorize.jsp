<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>软件授权</title>
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
			<span><a href="javascript:void(0);" onclick="javascript: save('detail', false);" class="itembtn10">保存</a></span>
			<span><a href="javascript:void(0);" onclick="javascript: cancel('detail');" class="itembtn11">重填</a></span>
		</div>	
		<h3 class="mode-tit">软件授权</h3>
		<div class="tab-area">
			<input type ="hidden" id="id" name="id" value="<c:out value='${user.id}'/>"/>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name" style="width:25%">授权文件：</td>
					<td style="width:75%"><input type="text" id="certificate" name="certificate" value="<c:out value='${authorization.certificate}'/>" style="width:99%" /></td>
				</tr>
				<tr>
					<td class="item-name" style="width:25%">授权密钥：</td>
					<td style="width:75%"><input type="password" id="password" name="password" value="<c:out value='${authorization.password}'/>" style="width:99%" /></td>
				</tr>
				<tr>
					<td class="item-name" style="width:25%">授权信息：</td>
					<td style="width:75%"><input type="text" id="alias" name="alias" value="<c:out value='${authorization.alias}'/>" style="width:99%" /></td>
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