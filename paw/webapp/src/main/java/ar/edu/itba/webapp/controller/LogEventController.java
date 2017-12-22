package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.EventService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.models.event.LogEvent;
import ar.edu.itba.webapp.response.LogEventListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("feed")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class LogEventController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LogEventController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    @GET
    public Response getByUser() {
        List<? extends LogEvent> events;
        LogEventListResponse response = new LogEventListResponse();
        try {
            events = eventService.getEventsForActor(getLoggedUser());
            response.setEvents(events);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(response)
            .build();
    }


    @GET
    @Path("/user/{username}")
    public Response getByUserProfile(@PathParam("username") final String username) {
        List<? extends LogEvent> events;
        LogEventListResponse response = new LogEventListResponse();
        try {
            User user = userService.getByUsername(username);
            events = eventService.getEventsForActor(user);
            response.setEvents(events);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(response)
         .build();
    }

    @GET
    @Path("/project/{project}")
    public Response getByProject(@PathParam("project") final String code) {
        List<? extends LogEvent> events;
        LogEventListResponse response = new LogEventListResponse();
        try {
            Project project = projectService.getProjectByCode(code);
            events = eventService.getEventsForProject(project);
            response.setEvents(events);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(response)
         .build();
    }
}
