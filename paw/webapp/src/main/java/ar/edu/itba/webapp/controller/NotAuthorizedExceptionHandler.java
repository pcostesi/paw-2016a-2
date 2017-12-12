package ar.edu.itba.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Provider
public class NotAuthorizedExceptionHandler implements ExceptionMapper<NotAuthorizedException> {
    private final static Logger logger = LoggerFactory.getLogger(NotAuthorizedExceptionHandler.class);

    @Override
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response toResponse(final NotAuthorizedException exception) {
        logger.error("Error occurred", exception);
        final ErrorMessage msg = new ErrorMessage();
        msg.message = exception.getMessage();
        msg.status = Response.Status.UNAUTHORIZED.name();
        return Response.serverError().entity(msg).build();
    }

    @XmlRootElement
    private static class ErrorMessage {
        @XmlElement
        public String message;

        @XmlElement
        public String status;
    }
}
