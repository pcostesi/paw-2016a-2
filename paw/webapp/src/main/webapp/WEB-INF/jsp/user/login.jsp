<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page>
	<jsp:attribute name="title">
    	Log in
	</jsp:attribute>
	
	<jsp:body>
		<c:url value="/login" var="loginUrl" />
		<form action="${loginUrl}" method="POST" enctype="application/x-www-form-urlencoded">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div>
                        <label for="j_username"><spring:message code="login.username"/><label>
						<input type="text" id="j_username" name="j_username" />
						</div>
                        
                        <div>
                        <label for="j_password"><spring:message code="login.password"/><label>
						<input type="password" id="j_password" name="j_password" />
						</div>
						
						<div>
						<label><input name="j_rememberme" id="j_rememberme" type="checkbox" 
						data-on="<spring:message code='login.remember_me'/>" 
						data-off="<spring:message code='login.remember_me_not'/>" 
						data-toggle="toggle" /> <spring:message code="login.remember_me"/></label>
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form>
    </jsp:body>
</t:page>
