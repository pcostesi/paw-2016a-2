package ar.edu.itba.webapp.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.UserService;


@Component
public class RestyController {

	@Autowired
	private UserService us;
	
	final static Logger logger = LoggerFactory.getLogger(RestyController.class);
	
	@Path("/sample")
	@GET
	@Produces("*/*")
	public Response everyone() {
		List<String> everyoneInTheApp = us.getUsernames();
		return Response.ok(everyoneInTheApp)
			.link("/sample", "self")
			.build();
	}
}
