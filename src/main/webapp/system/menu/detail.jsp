<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>系统菜单管理</title>
<script type="text/javascript" src="../../js/system/menu/detail.js"></script>
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
		<h3 class="mode-tit">系统菜单详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${menu.id}'/>"/>
				<input type="hidden" id="parentid" name="parentid" value="<c:out value='${menu.parentid}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${menu.flag}'/>"/>
				<input type="text" id="parentid" name="parentid" value="<c:out value='${menu.parentid}'/>" style="width:90%"/>
				<input type="text" id="rank" name="rank" value="<c:out value='${menu.rank}'/>" style="width:99%" readonly/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">菜单名称：</td>
					<td><input type="text" id="title" name="title" value="<c:out value='${menu.title}'/>" style="width:99%" required/></td>
					<td class="item-name">所属系统：</td>
					<td><input type="text" id="system" name="system" value="<c:out value='${menu.system}'/>" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">上级菜单：</td>
					<td>
						<input type="text" id="parentname" name="parentname" value="<c:out value='${menu.parentname}'/>" style="width:90%" readonly/>
						<input type="button" value="..." style="width:7%" onclick="javascript: initTree('load.jspx', 'content', false, document.getElementById('parentid').value);"/>
					</td>
					<td class="item-name">显示顺序：</td>
					<td><input type="number" id="display" name="display" value="<c:out value='${menu.display}'/>" class="number" style="width:99%" min="0"/></td>
				</tr>
				<tr>
					<td class="item-name">菜单地址：</td>
					<td colspan="3"><input type="text" id="uri" name="uri" value="<c:out value='${menu.uri}'/>" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">备注：</td>
					<td colspan="3"><textarea id="remark" name="remark" style="width:99%" rows="3"><c:out value='${menu.remark}'/></textarea></td>
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