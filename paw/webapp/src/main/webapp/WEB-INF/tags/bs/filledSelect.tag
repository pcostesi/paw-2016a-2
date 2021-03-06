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
<%@attribute name="multiple" required="false" type="java.lang.Boolean"%>
<%@attribute name="tokenize" required="false" type="java.lang.String"%>
<%@attribute name="nullOption" required="false" type="java.lang.String"%>

<spring:bind path="${path}">
	<div class="form-group ${status.error ? 'has-error' : '' } ${cssClass}">
		<div class="input-group">
	  		<span for="${path}" class="input-group-addon">${label}<c:if test="${required}"><span class="required">*</span></c:if></span>
	        <form:select path="${path}" class="form-control ${tokenize}" multiple="${multiple}">
	        	<c:if test="${not empty nullOption}">
	        		<form:option label="${nullOption}" value=""/>
	        	</c:if>
	        	<form:options items="${items}"/>
	        </form:select>
		    <c:if test="${status.error}">
	            <span class="help-block">${status.errorMessage}</span>
	        </c:if>
	    </div>
	</div>
</spring:bind>