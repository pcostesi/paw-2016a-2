<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page user="${user}">
	<jsp:attribute name="title">
		Hi ${user.username()}!
	</jsp:attribute>
	<jsp:body>
		Your email address is <a href="mailto:${user.mail()}">${user.mail()}</a>, right?
    </jsp:body>
</t:page>
