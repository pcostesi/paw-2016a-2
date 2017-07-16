package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.request.UserUpdateDetailsRequest;
import ar.edu.itba.webapp.response.UserListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.List;

@Component
@Path("user")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserController extends BaseController {

	@Autowired
	private UserService us;

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@GET
	@Path("/{username}")
	public Response getUserByUsername(@PathParam("username") final String username) {
		User user;
		try {
			user = us.getByUsername(username);
		}catch (IllegalArgumentException| IllegalStateException a){
			ErrorMessage msg;
			if(a.getClass() == IllegalArgumentException.class){
				msg = ErrorMessage.asError("400", "Bad request, invalid or empty username");
			}
			else{
				msg = ErrorMessage.asError("400", "Username already exists");
			}
			return Response.serverError().entity(msg)
					.build();
		}
		final String userLink = MessageFormat.format("/user/{0}", username);
		return Response.ok(user)
				.link(userLink, "self")
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCreateUser(UserUpdateDetailsRequest request) {
		final String username = request.getUsername();
		final String password = request.getPassword();
		final String email = request.getMail();
		User user;
		try {
			user = us.create(username, password, email);
		}catch(IllegalArgumentException | IllegalStateException e){
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
		final String userLink = MessageFormat.format("/user/{0}", username);
		return Response.ok(user)
				.link(userLink, "self")
				.build();
	}

	@GET
	public Response getAllUsers() {
		logger.debug("user list debug");
		UserListResponse userList = new UserListResponse();
		List<String> usernames = us.getUsernames();
		userList.users = usernames.toArray(new String[usernames.size()]);
		return Response.ok(userList)
				.build();
	}

	@GET
	@Path("/me")
	public Response getMe() {
		return Response.ok(getLoggedUser())
				.build();
	}


	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUpdateUser(UserUpdateDetailsRequest request, @PathParam("id") String id) {
		User loggedUser = getLoggedUser();
		User user;
		try{
			user = us.getByUsername(id);
		}catch (IllegalArgumentException | IllegalStateException a) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}
		String password = request.getPassword();
		String username = request.getUsername();

		if (!loggedUser.username().equalsIgnoreCase(user.username())) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}

		try {
			if (password != null && !password.equalsIgnoreCase("")) {
				user = us.editPassword(user, password);
			}
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		final String userLink = MessageFormat.format("/user/{0}", username);
		return Response.ok(user)
				.link(userLink, "self")
				.build();
	}
}
