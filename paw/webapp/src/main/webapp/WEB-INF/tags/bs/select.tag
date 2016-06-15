<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="label" required="false" type="java.lang.String"%>
<%@attribute name="required" required="false" type="java.lang.Boolean"%>
<%@attribute name="items" required="false" type="java.util.List"%>
<%@attribute name="map" required="false" type="java.util.Map"%>

<jsp:doBody var="body"/>
<c:if test="${not empty body}">
	<c:set var="label" value="${body}"/>
</c:if>

<c:if test="${empty label}">
    <c:set var="label" value="${fn:toUpperCase(fn:substring(path, 0, 1))}${fn:toLowerCase(fn:substring(path, 1,fn:length(path)))}" />
</c:if>
<spring:bind path="${path}">
	<div class="form-group ${status.error ? 'has-error' : '' } ${cssClass}">
		<div class="input-group">
		  	<span for="${path}" class="input-group-addon">${label}<c:if test="${required}"><span class="required">*</span></c:if></span>
		  	<c:choose>
		  		<c:when test="${not empty map}">
				  	<form:select id="${path}" path="${path}" class="form-control" items="${map}"/>
		  		</c:when>
		  		<c:otherwise>
				  	<form:select id="${path}" path="${path}" class="form-control" items="${items}"/>
		  		</c:otherwise>
		  	</c:choose>
		    <c:if test="${status.error}">
	            <span class="help-block">${status.errorMessage}</span>
	        </c:if>
	    </div>
	</div>
</spring:bind>