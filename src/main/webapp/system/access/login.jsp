<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>用户登录</title>
<%@ include file="../../tag/tag.jsp"%>
<script type="text/javascript" src="../../js/system/access/login.js"></script>
</head>
<body>
<config:config key="login.password.allownull" info="allowNull" />
<section>
<form id="login" action="login.jspx" method="post">
<div class="login-main">
	<div class="login-warp">
		<h1 class="sys-logo">framework</h1>
		<ul class="sys-login">
			<li class="login-area">
				<div id="result">
					<c:if test="${info == '-1'}">
						<span style="color:red">登录失败：未找到相关账号</span>
					</c:if>
					<c:if test="${info == -2}">
						<span style="color:red">登录失败：密码错误</span>
					</c:if>
					<c:if test="${info == -3}">
						<span style="color:red">登录失败：找到多条账号匹配记录，请与管理员联系</span>
					</c:if>
					<c:if test="${info == -4}">
						<span style="color:red">登录失败：服务器内部错误，请稍后重试</span>
					</c:if>
					<c:if test="${info == 0}">
						<span style="color:red">登录成功</span>
					</c:if>
				</div>
				<input type="text" id="username" name="username" class="ltxt1" id="itxt1" placeholder="请输入用户名" autofocus required/>
				<input type="password" id="password" name="password" class="ltxt2" id="itxt2" placeholder="请输入密码" <c:if test="${allowNull != 'true'}">required</c:if>/>
				<input type="button" id="submit" name="submit" class="lbtn" value="登录" onclick="javascript: access(<c:out value='${allowNull}'/>);"/>
			</li>
			<li class="login-tip">没有帐号？ 请和系统管理员联系!</li>
		</ul>
	</div>
</div>
</form>
</section>
<footer>
<div class="login-footer">
	<span>copyright&copy;2012 spring mvc | framework</span>
</div>
</footer>
</body>
</html>