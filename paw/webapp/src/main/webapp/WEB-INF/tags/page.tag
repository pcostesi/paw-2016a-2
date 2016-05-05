<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@tag description="Page Template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" required="true" %>
<%@attribute name="actions" fragment="true" required="false" %>
<%@attribute name="user" required="false" type="ar.edu.itba.models.User" %>

<!DOCTYPE html>

<html>

	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/3.3.6/sandstone/bootstrap.min.css"/>
	    <link rel="stylesheet" href="/grupo2/css/custom.css"/>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.2.0/require.min.js" data-main="/grupo2/scripts/main.js"></script>    
	    
	</head>

	<body>
	
	    <header class="navbar navbar-default navbar-static-top">
	        <div class="container">
	            <!-- Brand and toggle get grouped for better mobile display -->
	            <div class="navbar-header">
	                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-1" aria-expanded="false">
	                </button>
	                <a class="navbar-brand" href="/grupo2/">Scrumlr</a>
	            </div>	            
	            <nav class="collapse navbar-collapse" id="nav-1">
	                <ul class="nav navbar-nav navbar-right">
			     		<li>
			     			<a href="#">
			     				<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Logout
			     			</a>
			     		</li>		     		
			        </ul>				
	            </nav>
	            <!-- /.navbar-collapse -->
	        </div>
	        <!-- /.container -->
	    </header>
	
	    <div class="container">
	        <div class="page-header">
	            <h1><jsp:invoke fragment="title"/>
		            <div class="pull-right">	            
	                	<jsp:invoke fragment="actions"/>
		            </div>
	            </h1>
	        </div>
	        
	        <jsp:doBody/>
	    </div>
	</body>
	
</html>