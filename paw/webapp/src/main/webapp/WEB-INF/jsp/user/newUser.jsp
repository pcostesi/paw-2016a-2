<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="user.edit.password.placeholder" var="userEditPasswordPlaceholder"/>
<spring:message code="login.username.placeholder" var="userNamePlaceholder"/>


<t:page user="${user}">
	<jsp:attribute name="title">
    	<spring:message code="register.title"/>
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="userForm" action="${pageContext.request.contextPath}/user/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input path="user" label="User" placeholder="${userNamePlaceholder}"/>
						<bs:input type="password" path="password" label="Password" placeholder="${userEditPasswordPlaceholder}"/>
						<bs:input type="password" path="verifyPassword" label="Repeat password" placeholder="${userEditConfirmPasswordPlaceholder}"/>
						<bs:input path="mail" label="E-mail" />
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary"><spring:message code="register.title"/></button>

		</form:form>
    </jsp:body>
</t:page>
