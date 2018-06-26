<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>人员角色管理</title>
<script type="text/javascript" src="../../js/function/user.js" ></script>
<script type="text/javascript" src="../../js/system/userrole/detail.js" ></script>
</head>
<body>
<%@ include file="../../frame/head.jsp"%>
<section>
<config:config key="user.related" info="related"/>
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
		<h3 class="mode-tit">人员角色详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type ="hidden" id="id" name="id" value="<c:out value='${userRole.id}'/>"/>
				<input type ="hidden" id="flag" name="flag" value="<c:out value='${userRole.flag}'/>"/>
				<c:if test="${related == 'all' || fn:contains(related, 'department')}">
					<input type="text" id="user" name="user" value="<c:out value='${userRole.user}'/>" style="width:90%" required/>
				</c:if>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">人员名称：</td>
					<td>
						<c:choose>
						<c:when test="${related == 'all' || fn:contains(related, 'department')}">
							<input type="text" id="username" name="username" value="<c:out value='${userRole.username}'/>" style="width:90%" readonly required/>
							<input type="button" value="..." style="width:7%" onclick="javascript: selector='user'; initDepartment(JSON.stringify([{'user':document.getElementById('user').value}]), '../department/load.jspx', '../user/load.jspx', 'content', false);"/>
						</c:when>
						<c:otherwise>
							<select:select id="user" name="user" data="user" code="id" value="title" selected="${userRole.user}" style="width:99%" top="--请选择--"/>
						</c:otherwise>
						</c:choose>
					</td>
					<td class="item-name">角色名称：</td>
					<td><select:select id="role" name="role" data="roles" code="id" value="title" selected="${userRole.role}" style="width:99%" required="true"/></td>
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