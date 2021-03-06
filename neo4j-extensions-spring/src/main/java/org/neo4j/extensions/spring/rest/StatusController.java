package org.neo4j.extensions.spring.rest;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * The status controller.
 *
 * @author bradnussbaum
 * @version 0.1.0
 * @since 0.1.0
 */
@Controller
@Path("/status")
public class StatusController {

    private static final Logger LOGGER = Logger.getLogger(StatusController.class.getName());

    @Context
    private GraphDatabaseService db;

    /**
     * @return Status 200 on success.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response healthCheck() {
        Transaction txn = null;
        try {
            txn = db.beginTx();
            txn.success();
            LOGGER.info("STATUS: success");
        } catch (Exception e) {
            txn.failure();
            LOGGER.info("STATUS: failure");
            throw new WebApplicationException(e);
        } finally {
            if (txn != null) {
                txn.close();
            }
        }
        return Response.ok().entity("ok").build();
    }

}
