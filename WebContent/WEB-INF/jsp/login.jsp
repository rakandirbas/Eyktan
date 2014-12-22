<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp"
  title="Eyktan - Login" css="style">
	<s:layout-component name="body">
	
	<h1>Please login:</h1>
	
	
	
	<s:form beanclass="com.rakblog.action.LoginActionBean">
			<p>Username:</p><s:text name="username"/>
			<p>Password:</p><s:password name="password"/>
			<s:submit name="go" value="Login"/>
			<s:submit name="cancel" value="Cancel"/>
	</s:form>
	
	</s:layout-component>
</s:layout-render>