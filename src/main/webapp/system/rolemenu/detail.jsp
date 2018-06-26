<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>角色菜单管理</title>
<script type="text/javascript" src="../../js/system/rolemenu/detail.js" ></script>
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
		<h3 class="mode-tit">角色菜单详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${roleMenu.id}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${roleMenu.flag}'/>"/>
				<input type="text" id="menu" name="menu" value="<c:out value='${roleMenu.menu}'/>" style="width:90%" required/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">角色名称：</td>
					<td><select:select id="role" name="role" data="roles" code="id" value="title" selected="${roleMenu.role}" style="width:99%" required="true"/></td>
					<td class="item-name">菜单名称：</td>
					<td>
						<input type="text" id="menuname" name="menuname" value="<c:out value='${roleMenu.menuname}'/>" style="width:90%" readonly required/>
						<input type="button" value="..." style="width:7%" onclick="javascript: initTree('../menu/load.jspx', 'content', false, document.getElementById('menu').value);"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	</form>
	<!--//main area-->
	<div id="content" class="iPopup_wrap" style="width:305px;height:578px;">
		<%@ include file="../../util/tree.jsp"%>
	</div>
	</div>
</div>
</section>
<%@ include file="../../frame/foot.jsp"%>
</body>
</html>