<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@attribute name="status" required="true" type="ar.edu.itba.models.Status" %>

<c:choose>
	<c:when test="${status.getValue() == 0}"><span class="status status-not-started"></span></c:when>
	<c:when test="${status.getValue() == 1}"><span class="status status-started"></span></c:when>
	<c:when test="${status.getValue() == 2}"><span class="status status-done"></span></c:when>
</c:choose>
