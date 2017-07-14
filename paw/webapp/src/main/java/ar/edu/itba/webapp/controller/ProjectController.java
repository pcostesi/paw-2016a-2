package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.request.ProjectUpdateDetailsRequest;
import ar.edu.itba.webapp.response.ProjectListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Path("project")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService ps;

	private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@GET
	@Path("/{codename}")
	public Response getProjectByCodename(@PathParam("codename") final String codename) {
		final String projectLink = MessageFormat.format("/project/{0}", codename);
		final Project project = ps.getProjectByCode(codename);
		return Response.ok(project)
				.link(projectLink, "self")
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCreateProject(ProjectUpdateDetailsRequest request) {
		final String name = request.getName();
		final String code = request.getCode();
		final String description = request.getDescription();
		final User admin = getLoggedUser();
		final Set<User> members = new HashSet<>();
		final Project project = ps.createProject(admin, members, name, description, code);
		return Response.ok(project)
				.build();
	}

	@GET
	public Response getProjectsForUser() {
		final List<Project> project = ps.getProjectsForUser(getLoggedUser());
		ProjectListResponse projectsList = new ProjectListResponse();
		projectsList.projects = project.toArray(new Project[project.size()]);
		return Response.ok(projectsList)
				.build();
	}
}
