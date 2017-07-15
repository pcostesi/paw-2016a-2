package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.BacklogService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.request.CreateBacklogItemRequest;
import ar.edu.itba.webapp.request.UpdateBacklogItemRequest;
import ar.edu.itba.webapp.response.UserListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.List;

@Component
@Path("project/{proj}/backlog")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class BacklogItemController extends BaseController {

	@Autowired
	private ProjectService ps;

	@Autowired
	private BacklogService bs;
	
	private final static Logger logger = LoggerFactory.getLogger(BacklogItemController.class);

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") final int id) {
		BacklogItem backlogItem;
    	try {
        	backlogItem = bs.getBacklogById(id);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
        return Response.ok(backlogItem)
				.build();
    }

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putUpdateItem(UpdateBacklogItemRequest request) {
		BacklogItem item;
    	try {
			item = bs.getBacklogById(request.getId());
			String title = request.getTitle();
			String desc = request.getDescription();
			bs.setBacklogItemDescription(item, desc);
			bs.setBacklogItemTitle(item, title);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
        return Response.ok(item)
				.build();
	}
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	public Response postCreateItem(CreateBacklogItemRequest request,
                                   @PathParam("proj") String proj) {
		BacklogItem item;
		try {
			Project project = ps.getProjectByCode(proj);
			String title = request.getTitle();
			String desc = request.getDescription();
			item = bs.createBacklogItem(project, title, desc);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
		return Response.ok(item)
			.build();
	}

	@GET
	public Response getAllItems(@PathParam("proj") String proj) {
		List<BacklogItem> items;
    	try {
			Project project = ps.getProjectByCode(proj);
			items = bs.getBacklogForProject(project);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
		return Response.ok(items)
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteItem(@PathParam("id") int id) {
		BacklogItem item;
    	try { 
			item = bs.getBacklogById(id);
        	bs.deleteBacklogItem(item);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.serverError().entity(new  ErrorMessage("400", e.getMessage()))
					.build();
		}
        return Response.ok(item)
			.build();
	}
}
