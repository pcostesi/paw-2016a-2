<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="href" required="true"%>
<%@attribute name="btnClass" required="false"%>

<div class="dropdown fix-inline">
  <button class="btn btn-default btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
    <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Options
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
    <li>
    	<a href="${href}/edit">
    		<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
    	</a>
    </li>
    <li>
    	<form action="${href}/delete" method="POST" >
    		<button class="button-link" type="submit" data-think="twice" ><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
    		</button>
    	</form>
  	</li>
  </ul>
</div>
