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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Path("project")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProjectController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ProjectService ps;

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
}
