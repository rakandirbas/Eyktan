<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp"
  title="Eyktan - Results" css="res">
	<s:layout-component name="body">
	
		<a alt="Eyktan" href="Index.action"><img id="logo" src="images/small-logo.png" width="200" height="74" /></a>
		
		
		<s:form beanclass="com.rakblog.action.ResultsActionBean">
			<s:text name="query" id="searchField"/>
			<s:submit name="search" value="Search Tweets!" id="searchButton" />
		</s:form>
		
		<div id="searchInfo">
			<p>Found <span>${ actionBean.searchResult.formattedTotalHits }</span> results in <span>${ actionBean.searchResult.formattedSearchTime }</span> seconds</p>		
		</div>
		
		
		 <c:forEach var="tweet" items="${actionBean.searchResult.tweets}">

			<div class="tweet">
				<a alt="Avatar" href="#"><img src="${ tweet.imageURL }" width="48" height="48" /></a>
				<a alt="Screen Name" href="#@username" id="screenName">${ tweet.screenName }</a>
				<p id="realName">${ tweet.name }</p>
				<p id="text">${ tweet.text }</p>
				<p id="tweetTime"><span>${ tweet.elapsedTime }</span> ${ tweet.elapsedTimeType } ago.</p>
			</div>
			
		 </c:forEach>
		 
		 	<p class="paging">
		        <c:forEach begin="${ actionBean.pageRange1 }" end="${ actionBean.pageRange2 }" var="i" >
				<s:link beanclass="com.rakblog.action.ResultsActionBean">
		         <s:param name="pageNumber" value="${ i }"/><s:param name="query" value="${ actionBean.query }"/>
		          ${ i }
				</s:link> |
			</c:forEach>
			</p>
		<s:form beanclass="com.rakblog.action.ResultsActionBean">
			<s:text name="query" id="searchField"/>
			<s:submit name="search" value="Search Tweets!" id="searchButton" />
		</s:form>

	</s:layout-component>
</s:layout-render>

