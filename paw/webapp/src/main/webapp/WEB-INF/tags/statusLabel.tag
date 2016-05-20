<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@attribute name="status" required="true" type="ar.edu.itba.models.Status" %>

<c:choose>
	<c:when test="${status.value == 0}"><span class="label label-danger"><spring:message code="taskStatus.${status}"/></span></c:when>
	<c:when test="${status.value == 1}"><span class="label label-primary"><spring:message code="taskStatus.${status}"/></span></c:when>
	<c:when test="${statuss.value == 2}"><span class="label label-success"><spring:message code="taskStatus.${status}"/></span></c:when>
</c:choose>
