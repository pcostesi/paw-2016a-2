package ar.edu.itba.webapp.controller;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.service.UserService;

@Path("sample")
@Component
public class RestyController {
	
	private static class UsernameList implements Serializable{
		
		private String[] users;

		public String[] getUsers() {
			return users;
		}

		public void setUsers(String[] users) {
			this.users = users;
		}
	}
	
	@Autowired
	private UserService us;
	
	final static Logger logger = LoggerFactory.getLogger(RestyController.class);
	
	@GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
	public Response everyone() {
		UsernameList everyoneInTheApp = new UsernameList();
		everyoneInTheApp.setUsers(us.getUsernames().toArray(new String[0]));
		logger.info("users: {}", everyoneInTheApp.getUsers().length);
		return Response.ok(everyoneInTheApp)
			.link("/sample", "self")
			.build();
	}
}
