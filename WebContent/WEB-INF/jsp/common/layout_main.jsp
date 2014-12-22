<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:layout-definition>

	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${title}</title>
	<link rel="stylesheet" type="text/css"	href="${contextPath}/css/${css}.css">
	<script type="text/javascript">
				function setFocus()
				{
				document.getElementById("searchField").focus();
				}
	</script>
</head>

<body onload="setFocus()">
	<s:messages/>
	<s:errors/>
	<s:layout-component name="body" />
</body>
</html>
</s:layout-definition>
