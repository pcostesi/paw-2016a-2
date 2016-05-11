<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@tag description="Page Template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" required="false" %>
<%@attribute name="actions" fragment="true" required="false" %>
<%@attribute name="user" required="false" type="ar.edu.itba.models.User" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@attribute name="supportedLanguages" required="false" type="java.util.Locale[]" %>

<!DOCTYPE html>

<html>

	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/3.3.6/sandstone/bootstrap.min.css"/>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.2.0/require.min.js" data-main="${pageContext.request.contextPath}/scripts/main.js"></script>    
	    
	</head>

	<body>
	
	    <header class="navbar navbar-default navbar-static-top">
	        <div class="container">
	            <!-- Brand and toggle get grouped for better mobile display -->
	            <div class="navbar-header">
	                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-1" aria-expanded="false">
	                </button>
	                <a class="navbar-brand" href="${pageContext.request.contextPath}/">Scrumlr</a>
	            </div>	            
	            <nav class="collapse navbar-collapse" id="nav-1">
	                <ul class="nav navbar-nav navbar-right">
                    <li><a href="?language=en"><spring:message code="locale.en"/></a></li>
                    <li><a href="?language=es"><spring:message code="locale.es"/></a></li>
	                	<c:if test="${not empty user}">
			     		<li>
			     			<a href="${pageContext.request.contextPath}/me">
			     				<span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${user.username()}
			     			</a>
			     		</li>	
			     		<li>
			     			<a href="${pageContext.request.contextPath}/logout">
			     				<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> logout
			     			</a>
			     		</li>	
			     		</c:if>
			        </ul>				
	            </nav>
	            <!-- /.navbar-collapse -->
	        </div>
	        <!-- /.container -->
	    </header>
	
	    <div class="container">
	    	<c:if test="${not empty title}">
	        <div class="page-header">
	            <h1><jsp:invoke fragment="title"/>
		            <div class="pull-right">	            
	                	<jsp:invoke fragment="actions"/>
		            </div>
	            </h1>
	        </div>
	        </c:if>
	        <jsp:doBody/>
	    </div>
	</body>
	
</html>
