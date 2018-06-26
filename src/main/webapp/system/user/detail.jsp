<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>人员信息管理</title>
<script type="text/javascript" src="../../js/system/user/detail.js" ></script>
</head>
<body>
<%@ include file="../../frame/head.jsp"%>
<config:config key="login.password.allownull" info="allowNull"/>
<config:config key="related.type.department" info="department"/>
<config:config key="related.type.post" info="post"/>
<config:config key="user.related" info="related"/>
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
		<h3 class="mode-tit">人员信息详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type="hidden" id="id" name="id" value="<c:out value='${user.id}'/>"/>
				<input type="hidden" id="flag" name="flag" value="<c:out value='${user.flag}'/>"/>
				<input type="hidden" id="online" name="online" value="<c:out value='${user.online}'/>"/>
				<input type="hidden" id="allowNull" name="allowNull" value="<c:out value='${allowNull}'/>"/>
				<c:if test="${related == 'all' || fn:contains(related, 'department')}"><input type="text" id="department" name="department" value="<c:out value='${user.department}'/>" style="width:90%"/></c:if>
				<c:if test="${related == 'all' || fn:contains(related, 'post')}"><input type="text" id="post" name="post" value="<c:out value='${user.post}'/>" style="width:90%"/></c:if>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">姓名：</td>
					<td><input type="text" id="title" name="title" value="<c:out value='${user.title}'/>" style="width:99%" required/></td>
					<td class="item-name">性别：</td>
					<td><c:choose><c:when test="${sex == null || fn:length(sex) < 1}"><input type="text" id="sex" name="sex" value="<c:out value='${user.sex}'/>" style="width:99%"/></c:when><c:otherwise><select:select id="sex" name="sex" data="sex" code="code" value="meaning" selected="${user.sex}"/></c:otherwise></c:choose></td>
				</tr>
				<tr>
					<td class="item-name">用户名：</td>
					<td><input type="text" id="username" name="username" value="<c:out value='${user.username}'/>" style="width:99%" onkeyup="javascript: if (this.value != '') ajax('POST', 'validate.jspx', 'username=' + this.value, true, 'verified(msg);');"/><span id="validate"></span></td>
					<td class="item-name">密码：</td>
					<td><input type="password" id="password" name="password" value="<c:out value='${user.password}'/>" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">所属部门：</td>
					<td>
						<c:choose>
						<c:when test="${related == 'all' || fn:contains(related, 'department')}">
							<input type="text" id="departmentname" name="departmentname" value="<c:out value='${user.departmentname}'/>" style="width:90%"/>
							<c:if test="${department == 'single'}">
								<input type="button" value="..." style="width:7%" onclick="javascript: selector = 'department'; initTree('../department/load.jspx', 'content', false, document.getElementById('department').value);"/>
							</c:if>
							<c:if test="${department == 'multi'}">
								<input type="button" value="..." style="width:7%" onclick="javascript: selector = 'department'; initTree('../department/load.jspx', 'content', true, document.getElementById('department').value, { 'Y' : '', 'N' : '' });"/>
							</c:if>
						</c:when>
						<c:otherwise>
							<input type="text" id="department" name="department" value="<c:out value='${user.department}'/>" style="width:99%"/>
						</c:otherwise>
						</c:choose>
					</td>
					<td class="item-name">人员职务：</td>
					<td>
						<c:choose>
						<c:when test="${related == 'all' || fn:contains(related, 'post')}">
							<input type="text" id="postname" name="postname" value="<c:out value='${user.postname}'/>" style="width:90%"/>
							<c:if test="${post == 'single'}">
								<input type="button" value="..." style="width:7%" onclick="javascript: selector = 'post'; initTree('../post/load.jspx', 'content', false, document.getElementById('post').value);"/>
							</c:if>
							<c:if test="${post == 'multi'}">
								<input type="button" value="..." style="width:7%" onclick="javascript: selector = 'post'; initTree('../post/load.jspx', 'content', true, document.getElementById('post').value, { 'Y' : '', 'N' : '' });"/>
							</c:if>
						</c:when>
						<c:otherwise>
							<input type="text" id="post" name="post" value="<c:out value='${user.post}'/>" style="width:99%"/>
						</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="item-name">联系电话：</td>
					<td><input type="text" id="telephone" name="telephone" value="<c:out value='${user.telephone}'/>" class="telephone" style="width:99%"/></td>
					<td class="item-name">移动电话：</td>
					<td><input type="text" id="cellphone" name="cellphone" value="<c:out value='${user.cellphone}'/>" class="cellphone" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">电子邮件：</td>
					<td colspan="3"><input type="email" id="email" name="email" value="<c:out value='${user.email}'/>" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">联系地址：</td>
					<td colspan="3"><input type="text" id="address" name="address" value="<c:out value='${user.address}'/>" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">备注：</td>
					<td colspan="3"><textarea id="remark" name="remark" style="width:99%" rows="3"><c:out value='${user.remark}'/></textarea></td>
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
</section>
<%@ include file="../../frame/foot.jsp"%>
</body>
</html>