package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.response.UserListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;

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
        final User user = us.getByUsername(username);
        final String userLink = MessageFormat.format("/user/{0}", username);
        return Response.ok(user)
                .link(userLink, "self")
                .build();
    }

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postCreateUser(@FormParam("username") String username, @FormParam("password") String password,
								   @FormParam("email") String email) {
        User user = us.create(username, password, email);
		final String userLink = MessageFormat.format("/user/{0}", username);
		return Response.ok(user)
				.link(userLink, "self")
	    		.build();
	}

	@GET
	public Response getAllUsers() {
        logger.debug("user list debug");
        UserListResponse userList = new UserListResponse();
		return Response.ok(userList)
			.build();
	}
}
