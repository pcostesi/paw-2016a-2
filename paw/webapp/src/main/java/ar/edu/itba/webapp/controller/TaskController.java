package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.interfaces.service.TaskService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.request.CreateTaskRequest;
import ar.edu.itba.webapp.request.UpdateTaskRequest;
import ar.edu.itba.webapp.response.TaskListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("project/{proj}/iteration/{iter}/story/{story}/task")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TaskController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(TaskController.class);
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

    @GET
    @Path("/{index}")
    public Response getByNumber(@PathParam("index") final int index) {
        try {
            final Task task = ts.getTaskById(index);
            return Response.ok(task)
             .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
    }

    @DELETE
    @Path("/{index}")
    public Response deleteByNumber(@PathParam("index") final int index) {
        final Task task;
        try {
            task = ts.getTaskById(index);
            ts.deleteTask(task);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(task)
         .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateTask(final CreateTaskRequest request,
                                   @PathParam("story") final int storyN) {
        final String title = request.getTitle();
        final String description = request.getDescription();
        final Task task;
        try {
            final Story story = ss.getById(storyN);
            final Status status = Status.valueOf(request.getStat());
            final User owner = us.getByUsername(request.getUsername());
            final Score score = Score.valueOf(request.getScoreN());
            final Priority priority = Priority.valueOf(request.getPriorityN());
            task = ts.createTask(story, title, description, status, owner, score, priority);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(task)
         .build();
    }

    @GET
    public Response getTasksForStory(@PathParam("story") final int storyN) {
        final TaskListResponse tasksList;
        try {
            final Story story = ss.getById(storyN);
            final List<Task> tasks = ts.getTasksForStory(story);
            tasksList = new TaskListResponse();
            tasksList.tasks = tasks.toArray(new Task[tasks.size()]);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(tasksList)
         .build();
    }

    @PUT
    @Path("/{index}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateTask(final UpdateTaskRequest request,
                                   @PathParam("index") final int index) {
        final String title = request.getTitle();
        try {
            final Task task = ts.getTaskById(index);
            final User owner = us.getByUsername(request.getUsername());
            ts.changeDescription(task, request.getDescription());
            ts.changeOwnership(task, owner);
            ts.changeStatus(task, Status.valueOf(request.getStat()));
            ts.changeScore(task, Score.getByValue(request.getScore()));
            ts.changePriority(task, Priority.valueOf(request.getPriority()));
            ts.changeTitle(task, request.getTitle());
            return Response.ok(ts.getTaskById(index))
                    .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
                    .build();
        }
    }
}
