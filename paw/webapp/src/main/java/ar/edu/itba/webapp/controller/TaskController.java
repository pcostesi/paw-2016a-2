package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.*;
import ar.edu.itba.models.*;
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
@Path("project/{proj}/iteration/{iter}/story/{story}/task")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TaskController extends BaseController {

	@Autowired
	private ProjectService ps;

	@Autowired
	private IterationService is;

	@Autowired
	private StoryService ss;

	@Autowired
	private TaskService ts;

	@Autowired
	private UserService us;

	private final static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GET
    @Path("/{index}")
    public Response getByNumber(@PathParam("proj") final String proj,
								@PathParam("iter") final int iter,
								@PathParam("story") final int storyN,
								@PathParam("index") final int index) {
		Project project = ps.getProjectByCode(proj);
		Iteration iteration = is.getIteration(project, iter);
		Story story = ss.getById(storyN);
		Task task = ts.getTaskById(index);
        return Response.ok(task)
				.build();
    }

    @DELETE
	@Path("/{index}")
	public Response deleteByNumber(@PathParam("proj") final String proj,
								@PathParam("iter") final int iter,
								@PathParam("story") final int storyN,
								@PathParam("index") final int index) {
		Project project = ps.getProjectByCode(proj);
		Iteration iteration = is.getIteration(project, iter);
		Story story = ss.getById(storyN);
		Task task = ts.getTaskById(index);
		ts.deleteTask(task);
		return Response.ok(task)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postCreateTask(@PathParam("proj") final String proj,
								   @PathParam("iter") final int iter,
								   @PathParam("story") final int storyN,
								   @FormParam("title") final String title,
								   @FormParam("description") final String description,
								   @FormParam("status") final String stat,
								   @FormParam("owner") final String username,
								   @FormParam("score") final String scoreN,
								   @FormParam("priority") final String priorityN) {
		Project project = ps.getProjectByCode(proj);
		Iteration iteration = is.getIteration(project, iter);
		Story story = ss.getById(storyN);
		Status status = Status.valueOf(stat);
		User owner = us.getByUsername(username);
		Score score = Score.valueOf(scoreN);
		Priority priority = Priority.valueOf(priorityN);
		Task task = ts.createTask(story, title, description, status, owner, score, priority);
		return Response.ok(task)
				.build();
	}

	@GET
	public Response getTasksForStory(@PathParam("proj") final String proj,
									 @PathParam("iter") final int iter,
									 @PathParam("story") final int storyN) {
		Project project = ps.getProjectByCode(proj);
		Iteration iteration = is.getIteration(project, iter);
		Story story = ss.getById(storyN);
        List<Task> tasks = ts.getTasksForStory(story);
		return Response.ok(tasks)
				.build();
	}
}
