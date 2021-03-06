<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@attribute name="status" required="true" type="ar.edu.itba.models.Status" %>

<c:choose>
	<c:when test="${status.getValue() == 0}"><span class="label label-danger"><spring:message code="models.status.not_started"/></span></c:when>
	<c:when test="${status.getValue() == 1}"><span class="label label-primary"><spring:message code="models.status.started"/></span></c:when>
	<c:when test="${status.getValue() == 2}"><span class="label label-success"><spring:message code="models.status.completed"/></span></c:when>
</c:choose>
