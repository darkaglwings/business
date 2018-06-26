<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>职务菜单管理</title>
<script type="text/javascript" src="../../js/system/postmenu/detail.js" ></script>
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
		<h3 class="mode-tit">职务菜单详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${postMenu.id}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${postMenu.flag}'/>"/>
				<input type="text" id="post" name="post" value="<c:out value='${postMenu.post}'/>" style="width:90%" required/>
				<input type="text" id="menu" name="menu" value="<c:out value='${postMenu.menu}'/>" style="width:90%" required/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">职务名称：</td>
					<td>
						<input type="text" id="postname" name="postname" value="<c:out value='${postMenu.postname}'/>" style="width:90%" readonly required/>
						<input type="button" value="..." style="width:7%" onclick="javascript: selector = 'post'; initTree('../post/load.jspx', 'content', false, document.getElementById('post').value);"/>
					</td>
					<td class="item-name">菜单名称：</td>
					<td>
						<input type="text" id="menuname" name="menuname" value="<c:out value='${postMenu.menuname}'/>" style="width:90%" readonly required/>
						<input type="button" value="..." style="width:7%" onclick="javascript: selector = 'menu'; initTree('../menu/load.jspx', 'content', false, document.getElementById('menu').value);"/>
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