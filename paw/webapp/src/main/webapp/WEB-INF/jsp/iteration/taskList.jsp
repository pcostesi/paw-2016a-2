<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Iteration #1 <small>for Project Scrumlr</small>
    </jsp:attribute>
    
    <jsp:attribute name="actions">
	  <t:navbutton btnClass="btn-danger">
	    Delete
	  </t:navbutton>
	  <t:navbutton btnClass="btn-default">
	    Edit
	  </t:navbutton>
	  <t:navbutton btnClass="btn-primary">
	    New Task
	  </t:navbutton>
     </jsp:attribute>
        
        
     <jsp:body>     	
        <div class="panel-group" id="tasks-list" role="tablist" aria-multiselectable="true">
	     	<t:taskPanel panelParent="#tasks-list" taskId="1">Download tools</t:taskPanel>
		 	<t:taskPanel panelParent="#tasks-list" taskId="2">Clone repository</t:taskPanel>
		 	<t:taskPanel panelParent="#tasks-list" taskId="3">Mock HTML views</t:taskPanel>
		 	<t:taskPanel panelParent="#tasks-list" taskId="4">Spring tutorial</t:taskPanel>
		 	<t:taskPanel panelParent="#tasks-list" taskId="5">Read emails</t:taskPanel>
		 	<t:taskPanel panelParent="#tasks-list" taskId="6">Maven tutorial</t:taskPanel>
		</div>
    </jsp:body>    
</t:page>