package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.request.ProjectUpdateDetailsRequest;
import ar.edu.itba.webapp.request.UpdateProjectRequest;
import ar.edu.itba.webapp.response.ProjectListResponse;
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
import java.util.*;

@Component
@Path("project")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProjectController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService ps;
	@Autowired
	private UserService us;

	@GET
	@Path("/{codename}")
	public Response getProjectByCodename(@PathParam("codename") final String codename) {
		try {
			final String projectLink = MessageFormat.format("/project/{0}", codename);
			final Project project = ps.getProjectByCode(codename);
			return Response.ok(project)
					.link(projectLink, "self")
					.build();
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCreateProject(final ProjectUpdateDetailsRequest request) {
		final String name = request.getName();
		final String code = request.getCode();
		final String description = request.getDescription();
		final User admin = getLoggedUser();
		final Set<User> members = new HashSet<>();
		try {
			final Project project = ps.createProject(admin, members, name, description, code);
			return Response.ok(project)
					.build();
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}

	@GET
	public Response getProjectsForUser() {
		final List<Project> project;
		try {
			project = ps.getProjectsForUser(getLoggedUser());
			final ProjectListResponse projectsList = new ProjectListResponse();
			projectsList.setProjectsForResponse(project);
			return Response.ok(projectsList)
					.build();
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}

	@PUT
	@Path("/{codename}/user/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(UpdateProjectRequest request,
								  @PathParam("codename") final String codename,
								  @PathParam("username") final String username){
		try{
			final Project project = ps.getProjectByCode(codename);
			final User user = us.getByUsername(username);
			List<String> userList = us.getUsernamesForProject(project);
			List<String> updatedUser = java.util.Arrays.asList(request.getUsers());
			ps.setCode(user, project, request.getProjectCode());
			ps.setDescription(user, project, request.getDescription());
			ps.setName(user, project, request.getProjectName());

			List<String> inter = java.util.Arrays.asList(request.getUsers().clone());
			inter.retainAll(userList);
			userList.removeAll(inter);
			for (String name: userList) {
				ps.deleteUserFromProject(user, project, us.getByUsername(name));
			}
			updatedUser.removeAll(inter);
			for (String name : updatedUser) {
				ps.addUserToProject(user, project, us.getByUsername(name));
			}
			return Response.ok(ps.getProjectById(project.projectId()))
					.build();
		}catch(Exception e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}
	/*@PUT
	@Path("/{codename}/user")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUserToProject(UpdateProjectRequest request,
									 @PathParam("codename") final String codename){
		try {
			final User userToAdd = us.getByUsername(request.getProjectName());
			final Project project = ps.getProjectByCode(codename);
			ps.addUserToProject(getLoggedUser(), project, userToAdd);
			return Response.ok(project)
					.build();
		} catch(IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}

	@DELETE
	@Path("/{codename}/user/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUserFromProject(@PathParam("codename") final String codename,
										  @PathParam("username") final String username){
		try {
			final User userToDelete = us.getByUsername(username);
			final Project project = ps.getProjectByCode(codename);
			ps.deleteUserFromProject(getLoggedUser(), project, userToDelete);
			return Response.ok(project)
					.build();
		} catch(IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}*/

}
