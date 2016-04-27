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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.2.0/require.min.js" data-main="/scripts/main.js"></script>    
    
</head>


<body>

    <header class="navbar navbar-default navbar-static-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Scrumlr</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <nav class="collapse navbar-collapse" id="nav-1">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#"><spring:message code="nav.view.project"/></title></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Projects <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Project Scrumlr</a></li>
                        </ul>
                    </li>
                </ul>
                

				<ul class="nav navbar-nav navbar-right">
					<li class="btn-group">
			     		<jsp:invoke fragment="actions"/>
		     		</li>
		     		<li>
		     			${user.getUsername()}
		     		</li>
		        </ul>

            </nav>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </header>

    <div class="container">
        <div class="page-header">
            <h1><jsp:invoke fragment="title"/></h1>
        </div>
        
        <jsp:doBody/>
    </div>
</body>

</html>