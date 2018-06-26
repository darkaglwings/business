<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<config:config key="system.struct" info="struct" />
<c:choose>
	<c:when test="${struct == 'frame'}"></c:when>
	<c:otherwise>
		<div class="footer">
			<p>copyright&copy;2012 spring mvc | framework</p>
		</div>
	</c:otherwise>
</c:choose>