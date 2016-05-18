<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page user="${user}">
	<jsp:attribute name="title">
    	<spring:message code="login.title"/>
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/user/new" class="btn btn-primary">
		    <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Register
		</a>
	</jsp:attribute>
	
	<jsp:body>
		<c:url value="/login" var="loginUrl" />
		<form action="${loginUrl}" method="POST" enctype="application/x-www-form-urlencoded">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        
                        <div class="form-group input-group">
		                   	<span class="input-group-addon" for="j_username" id="username-addon"><spring:message code="login.username"/></span>
							<input class="form-control" type="text" id="j_username" name="j_username" aria-describedby="username-addon"/>
						</div>
		                <div class="form-group input-group">
		                   	<span class="input-group-addon" for="j_password" id="password-addon"><spring:message code="login.password"/></span>
							<input class="form-control" type="password" id="j_password" name="j_password" aria-describedby="password-addon"/>
						</div>
						
						<div class="form-group">
							<input name="j_rememberme" id="j_rememberme" type="checkbox" checked="checked"
							data-on="<spring:message code='login.remember_me'/>" 
							data-off="<spring:message code='login.remember_me_not'/>" 
							data-toggle="toggle" />
						</div>
							
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary"><spring:message code="login.submit"/></button>

		</form>
    </jsp:body>
</t:page>
