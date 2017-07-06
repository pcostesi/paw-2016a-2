package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Path("project/{project}/iteration")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class IterationController extends BaseController {

	@Autowired
	private ProjectService ps;

    @Autowired
    private IterationService is;

	private final static Logger logger = LoggerFactory.getLogger(IterationController.class);

    @GET
    @Path("/{index}")
    public Response getByIndex(@PathParam("project") final String project,
                               @PathParam("index") final int index) {
        final String link = MessageFormat.format("/project/{0}/iteration/{1}", project, index);
		final Project proj = ps.getProjectByCode(project);
        final Iteration iteration = is.getIteration(proj, index);
        return Response.ok(iteration)
                .link(link, "self")
                .build();
    }

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postCreateIteration(@PathParam("project") final String code,
									    @FormParam("start") final String start,
                                        @FormParam("end") final String end) {
        Project proj = ps.getProjectByCode(code);
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        Iteration iteration = is.createIteration(proj, startDate, endDate);
		return Response.ok(iteration)
	    		.build();
	}

	@GET
	public Response getAll(@PathParam("project") final String project) {
        Project proj = ps.getProjectByCode(project);
        List<Iteration> iterations = is.getIterationsForProject(proj);
		return Response.ok(iterations)
			.build();
	}

	@DELETE
    @Path("/{index}")
    public Response deleteIteration(@PathParam("project") final String project,
                                    @PathParam("index") final int index) {
        Project proj = ps.getProjectByCode(project);
        Iteration iteration = is.getIteration(proj, index);
        is.deleteIteration(iteration);
        return Response.ok()
                .build();
    }
}
