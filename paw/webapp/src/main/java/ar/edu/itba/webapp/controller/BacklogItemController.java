package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.BacklogService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.webapp.request.CreateBacklogItemRequest;
import ar.edu.itba.webapp.request.UpdateBacklogItemRequest;
import ar.edu.itba.webapp.response.BacklogListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("project/{proj}/backlog")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class BacklogItemController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BacklogItemController.class);
    @Autowired
    private ProjectService ps;
    @Autowired
    private BacklogService bs;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") final int id) {
        final BacklogItem backlogItem;
        try {
            backlogItem = bs.getBacklogById(id);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(backlogItem)
         .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUpdateItem(final UpdateBacklogItemRequest request, @PathParam("id") final int id) {
        final BacklogItem item;
        try {
            item = bs.getBacklogById(id);
            final String title = request.getTitle();
            final String desc = request.getDescription();
            if (!item.title().equalsIgnoreCase(title)) {
                bs.setBacklogItemTitle(item, title);
            }
            bs.setBacklogItemDescription(item, desc);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(item)
         .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateItem(final CreateBacklogItemRequest request,
                                   @PathParam("proj") final String proj) {
        final BacklogItem item;
        try {
            final Project project = ps.getProjectByCode(proj);
            final String title = request.getTitle();
            final String desc = request.getDescription();
            item = bs.createBacklogItem(project, title, desc);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(item)
         .build();
    }


    @GET
    public Response getAllItems(@PathParam("proj") final String proj) {
        try {
            final Project project = ps.getProjectByCode(proj);
            final List<BacklogItem> items = bs.getBacklogForProject(project);
            final BacklogListResponse response = new BacklogListResponse();
            response.setBacklog(items.toArray(new BacklogItem[items.size()]));
            return Response.ok(response)
             .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") final int id) {
        final BacklogItem item;
        try {
            item = bs.getBacklogById(id);
            bs.deleteBacklogItem(item);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(item)
         .build();
    }
}
