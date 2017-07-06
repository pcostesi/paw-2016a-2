package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.BacklogService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
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
        BacklogItem backlogItem = bs.getBacklogById(id);
		return Response.ok(backlogItem)
				.build();
    }

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postCreateItem(@PathParam("proj") String proj,
								   @FormParam("title") String title,
								   @FormParam("description") String desc) {
		Project project = ps.getProjectByCode(proj);
        BacklogItem item = bs.createBacklogItem(project, title, desc);
		return Response.ok(item)
				.build();
	}

	@GET
	public Response getAllItems(@PathParam("proj") String proj) {
        Project project = ps.getProjectByCode(proj);
		List<BacklogItem> items = bs.getBacklogForProject(project);
		return Response.ok(items)
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteItem(@PathParam("id") int id) {
		BacklogItem item = bs.getBacklogById(id);
        bs.deleteBacklogItem(item);
		return Response.ok(item)
				.build();
	}
}
