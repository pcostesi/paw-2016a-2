package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.*;
import ar.edu.itba.models.*;
import ar.edu.itba.webapp.request.CreateTaskRequest;
import ar.edu.itba.webapp.response.TaskListResponse;
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
    public Response getByNumber(@PathParam("index") final int index) {
    	try{
			Task task = ts.getTaskById(index);
			return Response.ok(task)
					.build();
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
	}

    @DELETE
	@Path("/{index}")
	public Response deleteByNumber(@PathParam("index") final int index) {
		Task task;
		try {
			task = ts.getTaskById(index);
			ts.deleteTask(task);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
		return Response.ok(task)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCreateTask(CreateTaskRequest request,
								   @PathParam("story") final int storyN) {
    	final String title = request.getTitle();
    	final String description = request.getDescription();
		Task task;
		try {
			Story story = ss.getById(storyN);
			Status status = Status.valueOf(request.getStat());
			User owner = us.getByUsername(request.getUsername());
			Score score = Score.valueOf(request.getScoreN());
			Priority priority = Priority.valueOf(request.getPriorityN());
			task = ts.createTask(story, title, description, status, owner, score, priority);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
		return Response.ok(task)
				.build();
	}

	@GET
	public Response getTasksForStory(@PathParam("story") final int storyN) {
		TaskListResponse tasksList;
    	try {
			Story story = ss.getById(storyN);
			List<Task> tasks = ts.getTasksForStory(story);
			tasksList = new TaskListResponse();
			tasksList.tasks = tasks.toArray(new Task[tasks.size()]);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
		return Response.ok(tasksList)
				.build();
	}
}
