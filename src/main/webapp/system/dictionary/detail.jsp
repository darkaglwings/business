<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>数据字典管理</title>
<script type="text/javascript" src="../../js/system/dictionary/detail.js"></script>
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
		<h3 class="mode-tit">数据字典详情</h3>
		<div class="tab-area">
			<div style="display:none">
				<input type ="hidden" id="id" name="id" value="<c:out value='${dictionary.id}'/>"/>
				<input type ="hidden" id="flag" name="flag" value="<c:out value='${dictionary.flag}'/>"/>
			</div>
			<table cellpadding="0" cellspacing="0" class="tab-style2 tab-detail">
				<tr>
					<td class="item-name">字典类型：</td>
					<td><input type="text" id="sort" name="sort" value="<c:out value='${dictionary.sort}'/>" style="width:99%" required/></td>
					<td class="item-name">上级字典：</td>
					<td>
						<select:select id="parent_sort" name="parent_sort" data="sort" code="sort" value="sort" selected="${parent_sort}" top="--请选择上级字典类型--" onChange="subset('load.jspx', this, 'parent_sort', 'initSubset(msg)');"/>
						<select:select id="parentid" name="parentid" data="type" code="id" value="meaning" selected="${dictionary.parentid}" top="--请选择上级字典--"/>
					</td>
				</tr>
				<tr>
					<td class="item-name">字典代码：</td>
					<td><input type="text" id="code" name="code" value="<c:out value='${dictionary.code}'/>" style="width:99%" required/></td>
					<td class="item-name">字典排序：</td>
					<td><input type="text" id="display" name="display" value="<c:out value='${dictionary.display}'/>" style="width:99%"/></td>
				</tr>
				<tr>
					<td class="item-name">字典内容：</td>
					<td colspan="3"><input type="text" id="meaning" name="meaning" value="<c:out value='${dictionary.meaning}'/>" style="width:99%" required/></td>
				</tr>
				<tr>
					<td class="item-name">备注：</td>
					<td colspan="3"><textarea id="remark" name="remark" style="width:99%" rows="3"><c:out value='${dictionary.remark}'/></textarea></td>
				</tr>
			</table>
		</div>
	</div>
	</form>
	<!--//main area-->
	</div>
</div>
</section>
<%@ include file="../../frame/foot.jsp"%>
</body>
</html>