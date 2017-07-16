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
import java.util.stream.Collectors;

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
		try{
			final String projectLink = MessageFormat.format("/project/{0}", codename);
			Project project = ps.getProjectByCode(codename);
			return Response.ok(project)
					.link(projectLink, "self")
					.build();
		} catch (IllegalArgumentException | IllegalStateException e){
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
				.build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCreateProject(ProjectUpdateDetailsRequest request) {
		final String name = request.getName();
		final String code = request.getCode();
		final String description = request.getDescription();
		final User admin = getLoggedUser();
		final Set<User> members = new HashSet<>();
		try{
			Project project = ps.createProject(admin, members, name, description, code);
			return Response.ok(project)
					.build();
		}catch (IllegalArgumentException | IllegalStateException e){
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
					.build();
		}
	}

	@GET
	public Response getProjectsForUser() {
		List<Project> project;
		try{
			project = ps.getProjectsForUser(getLoggedUser());
			ProjectListResponse projectsList = new ProjectListResponse();
            projectsList.setProjectsForResponse(project);
			return Response.ok(projectsList)
					.build();
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
				.build();
		}
	}
}
