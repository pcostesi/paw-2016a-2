<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page user="${user}">
	<jsp:attribute name="title">
		<spring:message code="profile.title" arguments="${user.username()}" />
	</jsp:attribute>
		<jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/me/edit/password" class="btn btn-primary btn-sm">
		    <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Edit Password
		</a>
	</jsp:attribute>
	
	<jsp:body>
		<spring:message code="profile.email" arguments="${user.mail()}" />
    </jsp:body>
</t:page>
