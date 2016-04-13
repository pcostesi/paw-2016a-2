<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="btnId" required="false"%>
<%@attribute name="btnClass" required="false"%>


<button type="button" class="btn navbar-btn btn-sm ${btnClass}" id="${btnId}">
	<jsp:doBody/>
</button>
