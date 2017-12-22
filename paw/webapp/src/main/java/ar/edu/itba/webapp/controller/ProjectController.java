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

import javax.ws.rs.*;
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
			for (String username: request.getMembers()){
				members.add(us.getByUsername(username));
			}
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
	@Path("/{codename}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(UpdateProjectRequest request,
								  @PathParam("codename") final String codename){
		final User user = getLoggedUser();
		Project project = null;
		List<String> userList = null;
		try{
			project = ps.getProjectByCode(codename);
			userList = us.getUsernamesForProject(project);
			List<String> updatedUser = new LinkedList<>(Arrays.asList(request.getMembers()));
			ps.setDescription(user, project, request.getDescription());
			ps.setName(user, project, request.getName());
			List<String> inter = new LinkedList<>(Arrays.asList(request.getMembers().clone()));
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

	@DELETE
	@Path("/{codename}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(@PathParam("codename") final String codename) {
		try{
			final Project project = ps.getProjectByCode(codename);
			final User user = getLoggedUser();
			ps.deleteProject(user, project);
			return Response.ok().build();
		}catch(Exception e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}
}
