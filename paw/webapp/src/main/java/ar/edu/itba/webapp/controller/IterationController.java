package ar.edu.itba.webapp.controller;

import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.webapp.request.CreateIterationRequest;
import ar.edu.itba.webapp.request.UpdateIterationRequest;
import ar.edu.itba.webapp.response.IterationListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

@Component
@Path("project/{project}/iteration")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class IterationController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(IterationController.class);
    @Autowired
    private ProjectService ps;
    @Autowired
    private IterationService is;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateIteration(final CreateIterationRequest request,
                                        @PathParam("project") final String code) {
        final String start = request.getStart();
        final String end = request.getEnd();
        final Iteration iteration;
        try {
            final Project proj = ps.getProjectByCode(code);
            final LocalDate startDate = LocalDate.parse(start);
            final LocalDate endDate = LocalDate.parse(end);
            iteration = is.createIteration(proj, startDate, endDate);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(iteration)
         .build();
    }

    @GET
    public Response getAll(@PathParam("project") final String project) {
        final IterationListResponse iterationList;
        try {
            final Project proj = ps.getProjectByCode(project);
            final List<Iteration> iterations = is.getIterationsForProject(proj);
            iterationList = new IterationListResponse();
            iterationList.setIterations(iterations.toArray(new Iteration[iterations.size()]));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok(iterationList)
         .build();
    }

    @DELETE
    @Path("/{index}")
    public Response deleteIteration(@PathParam("project") final String project,
                                    @PathParam("index") final int index) {
        try {
            final Project proj = ps.getProjectByCode(project);
            final Iteration iteration = is.getIteration(proj, index);
            is.deleteIteration(iteration);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
             .build();
        }
        return Response.ok()
         .build();
    }

    @PUT
    @Path("/{index}")
    public Response updateIteration(UpdateIterationRequest request,
                                    @PathParam("project") final String project,
                                    @PathParam("index") final int index){
        try {
            final Project proj = ps.getProjectByCode(project);
            final Iteration iteration = is.getIteration(proj, index);
            is.setDates(iteration, request.getBegindate(), request.getEnddate());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.serverError().entity(ErrorMessage.asError("400", e.getMessage()))
                    .build();
        }
        return Response.ok()
                .build();
    }

}
