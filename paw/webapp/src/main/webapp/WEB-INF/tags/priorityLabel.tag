<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@attribute name="priority" required="true" type="ar.edu.itba.models.Priority" %>

<c:choose>
	<c:when test="${priority.getValue() == 0}"><span class="label label-purple"><spring:message code="models.priority.low"/></span></c:when>
	<c:when test="${priority.getValue() == 1}"><span class="label label-primary"><spring:message code="models.priority.normal"/></span></c:when>
	<c:when test="${priority.getValue() == 2}"><span class="label label-success"><spring:message code="models.priority.high"/></span></c:when>
	<c:when test="${priority.getValue() == 3}"><span class="label label-warning"><spring:message code="models.priority.urgent"/></span></c:when>
	<c:when test="${priority.getValue() == 4}"><span class="label label-danger"><spring:message code="models.priority.critical"/></span></span></c:when>
</c:choose>
