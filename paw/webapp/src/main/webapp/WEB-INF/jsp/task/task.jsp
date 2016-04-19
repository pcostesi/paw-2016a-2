<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page user="${user}">
	<jsp:attribute name="title">
    	${task.getTitle()} <small>Task #${task.getTaskId()}</small>
	</jsp:attribute>
	
	<jsp:body>
		${task.getDescription()}
    </jsp:body>
</t:page>
