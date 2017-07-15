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
		Task task = ts.getTaskById(index);
        return Response.ok(task)
				.build();
    }

    @DELETE
	@Path("/{index}")
	public Response deleteByNumber(@PathParam("index") final int index) {
		Task task = ts.getTaskById(index);
		ts.deleteTask(task);
		return Response.ok(task)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCreateTask(CreateTaskRequest request,
								   @PathParam("story") final int storyN) {
    	String title = request.getTitle();
    	String description = request.getDescription();
		Story story = ss.getById(storyN);
		Status status = Status.valueOf(request.getStat());
		User owner = us.getByUsername(request.getUsername());
		Score score = Score.valueOf(request.getScoreN());
		Priority priority = Priority.valueOf(request.getPriorityN());
		Task task = ts.createTask(story, title, description, status, owner, score, priority);
		return Response.ok(task)
				.build();
	}

	@GET
	public Response getTasksForStory(@PathParam("story") final int storyN) {
		Story story = ss.getById(storyN);
        List<Task> tasks = ts.getTasksForStory(story);
		TaskListResponse tasksList = new TaskListResponse();
		tasksList.tasks = tasks.toArray(new Task[tasks.size()]);
		return Response.ok(tasksList)
				.build();
	}
}
