<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="url" required="true"%>
<%@attribute name="disabled" required="false" type="java.lang.String"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<div class="dropdown fix-inline">
	<button class="btn btn-default btn-xs dropdown-toggle ${disabled}" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
		<span class="glyphicon glyphicon-cog" aria-hidden="true"></span> <spring:message code="dropdownEditDelete.options"/>
		<span class="caret"></span>
	</button>
	<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
		<li>	
	    	<a href="${url}/edit">
	    		<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> <spring:message code="dropdownEditDelete.edit"/>
	    	</a>
    	</li>
    	<li>
	    	<form action="${url}/delete" method="POST" >
				<button class="button-link" type="submit" data-think="twice" ><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <spring:message code="dropdownEditDelete.delete"/>
			    </button>
			</form>
		</li>
	</ul>
</div>
