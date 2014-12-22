<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp"
  title="Eyktan - Tweets got better!" css="style">
	<s:layout-component name="body">
	
		<div class="logo"><a alt="Eyktan" href="Index.action"><img id="lg" src="images/logo.png" width="335" height="136" alt="Eyktan" /></a></div>
		<div class="form">
		<s:form beanclass="com.rakblog.action.ResultsActionBean">
			<s:text name="query" class="searchField" id="searchField"/>
			<div class="searchButtonLayout">
			<s:submit name="search" value="Search Tweets!" class="searchButton" />
			</div>
		</s:form>
		</div>
		
		<p class="indexInfo">Indexed <span class="emp">${actionBean.info.indexDocumentsNumber}</span> tweets so far!</p>
		<p class="footer">Eyktan 2011 &#169;</p>
	</s:layout-component>
</s:layout-render>