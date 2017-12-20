package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.webapp.request.CreateStoryRequest;
import ar.edu.itba.webapp.request.UpdateStoryRequest;
import ar.edu.itba.webapp.response.StoryListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Component
@Path("project/{proj}/iteration/{iter}/story")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StoryController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(StoryController.class);
    @Autowired
    private ProjectService ps;
    @Autowired
    private IterationService is;
    @Autowired
    private StoryService ss;

    @GET
    @Path("/{index}")
    public Response getStoryByNumber(@PathParam("proj") final String proj,
                                     @PathParam("iter") final int iter,
                                     @PathParam("index") final int index) {
        final String projectLink = MessageFormat.format("/project/{0}/iteration/{1}/story/{2}",
         proj, iter, index);
        final Story story;
        try {
            story = ss.getById(index);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(story)
         .link(projectLink, "self")
         .build();
    }

    @GET
    public Response getAll(@PathParam("proj") final String proj,
                           @PathParam("iter") final int iter) {
        try {
            final Map<Story, List<Task>> result;
            final Project project = ps.getProjectByCode(proj);
            final Iteration iteration = is.getIteration(project, iter);
            result = ss.getStoriesWithTasksForIteration(iteration);
            final StoryListResponse response = new StoryListResponse();
            response.setStories(result.entrySet().stream().map(Map.Entry::getKey).toArray(Story[]::new));
            return Response.ok(response)
             .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateStory(final CreateStoryRequest request,
                                    @PathParam("proj") final String proj,
                                    @PathParam("iter") final int iter) {
        final Story story;
        try {
            final Project project = ps.getProjectByCode(proj);
            final Iteration iteration = is.getIteration(project, iter);
            final String title = request.getTitle();
            story = ss.create(iteration, title);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(story)
         .build();
    }

    @DELETE
    @Path("/{index}")
    public Response deleteStory(@PathParam("index") final int index) {
        try {
            final Story story = ss.getById(index);
            ss.deleteStory(story);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/{index}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStory(UpdateStoryRequest request,
                                @PathParam("index") final int index){
        try {
            final Story story = ss.getById(index);
            ss.setName(story, request.getTitle());
            return Response.ok(ss.getById(index)).build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
                    .build();
        }
    }
}
