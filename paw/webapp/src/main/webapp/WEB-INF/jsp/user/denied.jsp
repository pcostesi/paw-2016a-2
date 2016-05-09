<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page>
	<jsp:attribute name="title">
    	<spring:message code="access.forbidden.title"/>
	</jsp:attribute>
	<jsp:body>
		<c:url value="/logout" var="logoutUrl" />
		<div class="jumbotron">
			<h1><spring:message code="access.forbidden.whoa_there"/></h1>
			<p><spring:message code="access.forbidden.you_cant_be_here"/></p>
			<p>
				<a class="btn btn-primary btn-lg" href="${logoutUrl}" role="button">
					<spring:message code="access.forbidden.security_kick_this_intruder_out"/>
				</a>
			</p>
		</div>
    </jsp:body>
</t:page>
