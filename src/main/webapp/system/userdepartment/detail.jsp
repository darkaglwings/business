<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>人员部门管理</title>
<script type="text/javascript" src="../../js/function/user.js" ></script>
<script type="text/javascript" src="../../js/system/userdepartment/detail.js" ></script>
</head>
<body>
<%@ include file="../../frame/head.jsp"%>
<section>
<config:config key="related.type.department" info="department"/>
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
		<h3 class="mode-tit">人员部门详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${userDepartment.id}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${userDepartment.flag}'/>"/>
				<input type="text" id="user" name="user" value="<c:out value='${userDepartment.user}'/>" style="width:90%" required/>
				<input type="text" id="department" name="department" value="<c:out value='${userDepartment.department}'/>" style="width:90%" required/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">人员名称：</td>
					<td>
						<input type="text" id="username" name="username" value="<c:out value='${userDepartment.username}'/>" style="width:90%" readonly required/>
						<input type="button" value="..." style="width:7%" onclick="javascript: selector='user'; initDepartment(JSON.stringify([{'user':document.getElementById('user').value}]), '../department/load.jspx', '../user/load.jspx', 'content', false);"/>
					</td>
					<td class="item-name">部门名称：</td>
					<td>
						<input type="text" id="departmentname" name="departmentname" value="<c:out value='${userDepartment.departmentname}'/>" style="width:90%" readonly required/>
						<input type="button" value="..." style="width:7%" onclick="javascript: selector='department'; initTree('../department/load.jspx', 'content', false, document.getElementById('department').value);"/>
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