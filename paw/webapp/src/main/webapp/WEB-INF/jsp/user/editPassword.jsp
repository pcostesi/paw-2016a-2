<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="user.edit.password.placeholder" var="passwordVerifyPlaceholder"/>
<spring:message code="user.edit.password.placeholder" var="passwordPlaceholder"/>

<t:page user="${user}">
	<jsp:attribute name="title">
    	<spring:message code="user.edit.password"/>
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="editPasswordForm" action="${pageContext.request.contextPath}/me/edit/password" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input type="password" path="password" label="New Password" placeholder="${passwordPlaceholder}"/>
						<bs:input type="password" path="verifyPassword" label="Repeat password" placeholder="${passwordVerifyPlaceholder}"/>
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary"><spring:message code="user.button.submit.changes"/></button>

		</form:form>
    </jsp:body>
</t:page>
