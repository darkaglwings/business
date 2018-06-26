<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<config:config key="system.struct" info="struct" />
<c:choose>
	<c:when test="${struct == 'frame'}"></c:when>
	<c:otherwise>
		<div class="DivMainLeft" id="leftmenu">
			<div class="DivBar">
				<span class="iTab_tag current"></span>
			</div>
			<!--nav-->
			<div class="iTab_child_dom sys-nav" id="sysnav">
				<c:forEach items="${account.menus}" var="menu">
					<c:if test="${menu.rank == 1}">
						<span class="iTab_tag" onclick="javascript: on_off('ul_${menu.id}'); storage('open', 'ul_${menu.id}');"><em class="item-${menu.display}"></em><a href="javascript:void(0);"><c:out value="${menu.title}" /></a></span>
						<ul id="ul_<c:out value='${menu.id}'/>" class="iTab_child_dom" style="display: none;">
							<c:forEach items="${account.menus}" var="submenu">
								<c:if test="${submenu.rank == 2 && submenu.parentid == menu.id}">
									<li><a href="javascript:void(0);" onclick="javascript: window.location.href = '<path:path type='context'/>${submenu.uri}';"><c:out value="${submenu.title}" /></a></li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if>
				</c:forEach>
			</div>
			<!--//nav-->
		</div>
		<script language="javascript">
			initMenu('open');
		</script>
	</c:otherwise>
</c:choose>