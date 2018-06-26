<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../tag/tag.jsp"%>

<script type="text/javascript" src="<path:path type='context'/>/js/frame/head.js"></script>

<config:config key="system.struct" info="struct" />
<c:choose>
	<c:when test="${struct == 'frame'}"></c:when>
	<c:otherwise>
		<div class="header clearfix">
			<h1 class="sys-sublogo">framework</h1>
			<p class="sys-opt" align="right">
				<span class="sys-sp1" style="display: none">新消息：<em>3</em> |</span>
				<span class="sys-sp2">当前时间：<em id="time"><date:date date="true" time="true" /></em> |</span>
				<span class="sys-sp3">当前用户：<em><c:out value="${account.user.username}" /></em> |</span>
				<span class="sys-sp4"><a href="javascript: logout(&quot;<path:path type='context'/>&quot;);">退出系统</a></span>
			</p>
		</div>
	</c:otherwise>
</c:choose>