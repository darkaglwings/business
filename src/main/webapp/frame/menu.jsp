<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>framework</title>
<%@ include file="../tag/tag.jsp"%>
</head>
<body>
	<div class="iTab_child_dom sys-nav" id="sysnav">
		<!--nav-->
		<c:forEach items="${account.menus}" var="menu">
			<c:if test="${menu.rank == 1}">
				<span class="iTab_tag" onclick="javascript: on_off('ul_${menu.id}'); storage('open', 'ul_${menu.id}');"><em class="item-${menu.display}"></em><a href="javascript:void(0);"><c:out value="${menu.title}" /></a></span>
				<ul id="ul_<c:out value='${menu.id}'/>" class="iTab_child_dom" style="display: none;">
					<c:forEach items="${account.menus}" var="submenu">
						<c:if test="${submenu.rank == 2 && submenu.parentid == menu.id}">
							<li><a href="javascript:void(0);" onclick="load('<path:path type='context'/>${submenu.uri}');"><c:out value="${submenu.title}"/></a></li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
		</c:forEach>
	</div>
</body>
</html>