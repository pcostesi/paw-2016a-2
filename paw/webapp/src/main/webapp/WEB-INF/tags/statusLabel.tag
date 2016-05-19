<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@attribute name="taskStatus" required="true" type="ar.edu.itba.models.Status" %>

<c:choose>
	<c:when test="${taskStatus.value == 0}"><span class="label label-danger">${taskStatus.label}</span></c:when>
	<c:when test="${taskStatus.value == 1}"><span class="label label-primary">${taskStatus.label}</span></c:when>
	<c:when test="${taskStatus.value == 2}"><span class="label label-success">${taskStatus.label}</span></c:when>
</c:choose>
