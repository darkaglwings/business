<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>framework</title>
<link href="../style/screen.css" rel="stylesheet" />
</head>
<body>
	<%@ include file="../frame/head.jsp"%>
	<div class="DivGlobal clearfix">
		<%@ include file="../frame/left.jsp"%>
		<div class="DivMainRight">
			<!--main area-->
			<c:out value="${result}" />
			<br />
			<input type="button" id="back" name="back" value="返回" onclick="javascript: back(<c:out value='${back_url}'/>);"/>
			<!--//main area-->
		</div>
	</div>
	<%@ include file="../frame/foot.jsp"%>
</body>
</html>