<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>系统欢迎页</title>
<%@ include file="../../tag/tag.jsp"%>
<script type="text/javascript" src="<path:path type='context'/>/js/system/welcome.js"></script>
</head>
<body>
<config:config key="system.struct" info="struct" />
<c:choose>
	<c:when test="${struct == 'frame'}">
		<div id="sysbar" class="header clearfix">
			<iframe height="100%" id="header" name="header" src="../../frame/top.jsp" frameBorder="0" style="width: 100%;"></iframe>
		</div>
					
		<div class="DivGlobal clearfix">
			<div class="DivMainLeft" id="leftmenu">
				<div class="DivBar">
					<span id="menu_switch" class="current" onclick="javascript: menuSwitch(this);"></span>
				</div>
				<iframe id="left" height="100%" name="left" src="../../frame/menu.jsp" frameBorder="0" style="width: 240px;"></iframe>
			</div>
		
			<div class="DivMainRight">
				<!--main area-->
				<div class="Content nontit">
					<iframe height="100%" id="right" name="right" src="" frameBorder="0" style="width: 100%;"></iframe>
				</div>
				<!--//main area-->
			</div>
		</div>
		
		<div id="main_blurdiv" style="display: none"></div>
		<div class="footer">
			<iframe height="39px" id="bottom" name="bottom" src="../../frame/bottom.jsp" frameBorder="0" style="width: 100%;"></iframe>
		</div>
	</c:when>
	<c:otherwise>
		<%@ include file="../../frame/head.jsp"%>
		<section>
			<div class="DivGlobal clearfix">
				<%@ include file="../../frame/left.jsp"%>
				<div class="DivMainRight">
					<!--main area-->
					welcome
					<!--//main area-->
				</div>
			</div>
		</section>
		<%@ include file="../../frame/foot.jsp"%>
	</c:otherwise>
</c:choose>
</body>
</html>