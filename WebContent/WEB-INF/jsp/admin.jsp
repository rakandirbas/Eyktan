<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp"
  title="Eyktan - Dashboard" css="style">
	<s:layout-component name="body">
	
	<h1>Welcome to Dashboard</h1>
	<s:form beanclass="com.rakblog.action.AdminActionBean">
		<s:submit name="startIndexing" value="Start Indexing"/> |
		<s:link beanclass="com.rakblog.action.AdminActionBean" event="stopIndexing">Stop Indexing</s:link> | 
		<s:link beanclass="com.rakblog.action.AdminActionBean" event="optimize">Optimize Index</s:link>
		<p><s:textarea name="terms" cols="60" rows="5" /></p>
	</s:form>
	
	<c:if test="${actionBean.isWorking}">
		<p>The indexing is working :)</p>
	</c:if>
	
	<c:if test="${not actionBean.isWorking}">
		<p>The indexing is STOPPED :(</p>
	</c:if>
	
	</s:layout-component>
</s:layout-render>