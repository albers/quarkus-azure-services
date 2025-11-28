package io.quarkiverse.azure.servicebus.deployment;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.jboss.logging.Logger;

@Path("/reload")
class ReloadResource {

    @Inject
    Logger logger;

    @GET
    @Produces("text/plain")
    public String reload() {
        logger.info("'/reload' endpoint called");
        return "OK";
    }
}
