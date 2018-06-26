<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>部门信息管理</title>
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
		<h3 class="mode-tit">部门信息详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${department.id}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${department.flag}'/>"/>
				<input type="hidden" id="fullname" name="fullname" value="<c:out value='${department.fullname}'/>"/>
				<input type="hidden" id="rank" name="rank" value="<c:out value='${department.rank}'/>"/>
				<input type="text" id="parentid" name="parentid" value="<c:out value='${department.parentid}'/>" style="width:90%"/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">部门名称：</td>
					<td><input type="text" id="title" name="title" value="<c:out value='${department.title}'/>" style="width:99%" required/></td>
					<td class="item-name">上级部门：</td>
					<td>
						<input type="text" id="parentname" name="parentname" value="<c:out value='${department.parentname}'/>" style="width:90%" readonly/>
						<input type="button" value="..." style="width:7%" onclick="javascript: initTree('load.jspx', 'content', false, document.getElementById('parentid').value);"/>
					</td>
				</tr>
				<tr>
					<td class="item-name">备注：</td>
					<td colspan="3"><textarea id="remark" name="remark" style="width:99%" rows="3"><c:out value='${department.remark}'/></textarea></td>
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