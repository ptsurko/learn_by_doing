package com.test.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/queue")
public class QueueResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String putIt() {
        return "created entry";
    }
}