<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page user="${user}">
	<jsp:attribute name="title">
    	Register
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="userForm" action="${pageContext.request.contextPath}/user/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input path="user" label="User" />
						<bs:input type="password" path="password" label="Password" />
						<bs:input type="password" path="verifyPassword" label="Repeat password" />
						<bs:input path="mail" label="E-mail" />
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Register</button>

		</form:form>
    </jsp:body>
</t:page>
