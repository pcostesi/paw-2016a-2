<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="project.edit.name.placeholder" var ="pryectNamePlaceholder"/>
<spring:message code="project.edit.code.placeholder" var ="proyectCodePlaceholder"/>
<spring:message code="project.edit.desc.placeholder" var = "proyectDescriptionPlaceholder"/> 

<t:page>
	<jsp:attribute name="title">
    	<spring:message code="project.new.title"/>
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="projectForm" action="${pageContext.request.contextPath}/project/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="oldName"/>
						<form:hidden path="oldCode"/>
						<bs:input path="name" label="Name" placeholder="${pryectNamePlaceholder}"/>
						<bs:input path="code" label="Code" placeholder="${proyectCodePlaceholder}"/>
						<bs:input path="description" label="Description" placeholder="${proyectDescriptionPlaceholder}"/>
						<bs:filledSelect path="members" label="Members" items="${usernames}" multiple="true" tokenize="tokenize"/>
					</fieldset>
				</div>
			</div>
			<bs:submit/>

		</form:form>
    </jsp:body>
</t:page>
