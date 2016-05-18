<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="url" required="true"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<form action="${url}/delete" method="POST" >
	<button class="btn btn-danger btn-xs" type="submit" data-think="twice" ><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <spring:message code="deleteButton"/>
    </button>
</form>
