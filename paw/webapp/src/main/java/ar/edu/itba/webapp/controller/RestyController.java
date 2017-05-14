package ar.edu.itba.webapp.controller;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.auth.RestCredentials;
import ar.edu.itba.webapp.auth.RestToken;

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
	
	private final static Logger logger = LoggerFactory.getLogger(RestyController.class);
	
	@GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
	public Response everyone() {
		logger.info("info info info info request dummy");

		UsernameList everyoneInTheApp = new UsernameList();
		everyoneInTheApp.setUsers(us.getUsernames().toArray(new String[0]));
		logger.info("users: {}", everyoneInTheApp.getUsers().length);
		return Response.ok(everyoneInTheApp)
			.link("/sample", "self")
			.build();
	}
}
