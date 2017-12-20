package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.request.GetProjectsForUserRequest;
import ar.edu.itba.webapp.request.UserUpdateDetailsRequest;
import ar.edu.itba.webapp.response.UserListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;

@Component
@Path("user")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService us;
    @Autowired
    private ProjectService ps;

    @GET
    @Path("/{username}")
    public Response getUserByUsername(@PathParam("username") final String username) {
        final User user;
        try {
            user = us.getByUsername(username);
        } catch (IllegalArgumentException | IllegalStateException a) {
            final ErrorMessage msg;
            if (a.getClass() == IllegalArgumentException.class) {
                msg = ErrorMessage.asError("400", "Bad request, invalid or empty username");
            } else {
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
    public Response postCreateUser(final UserUpdateDetailsRequest request) {
        final String username = request.getUsername();
        final String password = request.getPassword();
        final String email = request.getMail();
        final User user;
        try {
            user = us.create(username, password, email);
        } catch (IllegalArgumentException | IllegalStateException e) {
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
        final UserListResponse userList = new UserListResponse();
        final List<String> usernames = us.getUsernames();
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
    public Response postUpdateUser(final UserUpdateDetailsRequest request, @PathParam("id") final String id) {
        final User loggedUser = getLoggedUser();
        User user;
        try {
            user = us.getByUsername(id);
        } catch (IllegalArgumentException | IllegalStateException a) {
            return Response.status(Response.Status.BAD_REQUEST)
             .build();
        }
        final String password = request.getPassword();
        final String username = request.getUsername();

        if (!loggedUser.username().equalsIgnoreCase(user.username())) {
            return Response.status(Response.Status.BAD_REQUEST)
             .build();
        }

        try {
            if (password != null && !"".equalsIgnoreCase(password)) {
                user = us.editPassword(user, password);
            }
        } catch (final IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final String userLink = MessageFormat.format("/user/{0}", username);
        return Response.ok(user)
         .link(userLink, "self")
         .build();
    }

    @GET
    @Path("/{username}/projects")
    public Response getProjects(GetProjectsForUserRequest request,
                                @PathParam("username") final String username){
        try {
            final User user = us.getByUsername(username);
            List<Project> projects = ps.getProjectsForUser(user);
            Project[] result = projects.stream()
                                        .sorted(Comparator.comparing(Project::startDate))
                                        .limit(request.getAmount())
                                        .toArray(Project[]::new);
            return Response.ok(result)
                    .build();
        } catch(IllegalArgumentException | IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
