package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.User;
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
@Path("project/{proj}/iteration/{iter}/story")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StoryController extends BaseController {

	@Autowired
	private ProjectService ps;

	@Autowired
	private IterationService is;

	@Autowired
	private StoryService ss;

	private final static Logger logger = LoggerFactory.getLogger(StoryController.class);

    @GET
    @Path("/{index}")
    public Response getStoryByNumber(@PathParam("proj") final String proj,
									 @PathParam("iter") final int iter,
									 @PathParam("index") final int index) {
        final String projectLink = MessageFormat.format("/project/{0}/iteration/{1}/story/{2}",
				proj, iter, index);
		final Project project = ps.getProjectByCode(proj);
		final Iteration iteration = is.getIteration(project, iter);
		final Story story = ss.getById(index);
        return Response.ok(story)
                .link(projectLink, "self")
                .build();
    }

	@GET
	public Response getProjectsForUser() {
		final List<Project> project = ps.getProjectsForUser(getLoggedUser());
		return Response.ok()
				.build();
	}

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postCreateStory(@PathParam("proj") final String proj,
									@PathParam("iter") final int iter,
									@FormParam("title") final String title) {
		final Project project = ps.getProjectByCode(proj);
		final Iteration iteration = is.getIteration(project, iter);
       	final Story story = ss.create(iteration, title);
		return Response.ok(story)
				.build();
	}

	@DELETE
	public Response deleteStory(@PathParam("proj") final String proj,
								@PathParam("iter") final int iter,
								@PathParam("index") final int index) {

		final Project project = ps.getProjectByCode(proj);
		final Iteration iteration = is.getIteration(project, iter);
		final Story story = ss.getById(index);
		ss.deleteStory(story);
		return Response.ok().build();
	}
}
