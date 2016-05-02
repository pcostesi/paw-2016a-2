<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
    	Log in
	</jsp:attribute>
	
	<jsp:body>
		<form action="/login" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
                        <div>
                        <label for="j_username">Username: <label>
						<input type="text" id="j_username" />
						</div>
                        
                        <div>
                        <label for="j_password">Password: <label>
						<input type="password" id="j_password" />
						</div>
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form>
    </jsp:body>
</t:page>
