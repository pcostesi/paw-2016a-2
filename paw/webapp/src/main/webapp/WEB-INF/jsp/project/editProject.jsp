<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
		<spring:message code="project.edit.title" arguments="${project.name()}"/>
    	Edit project 
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="projectForm" action="${pageContext.request.contextPath}/project/${project.code()}/edit" method="POST">

			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="oldName" value ="${project.name()}"/>
						<form:hidden path="oldCode" value="${project.code()}"/>
						<bs:input path="name" label="Name" placeholder="Super project"/>
						<bs:input path="code" label="Code" placeholder="super.proj"/>
						<bs:input path="description" label="Description" placeholder="This project is super"/>
					</fieldset>
				</div>
			</div>
			<bs:submit/>

		</form:form>
    </jsp:body>
</t:page>
