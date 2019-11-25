package com.test.resources;

import com.test.filters.namebindings.AdminAuthorization;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/admin/user")
@AdminAuthorization
public class AdminResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@Context ContainerRequestContext crc) {
        System.out.println("Admin user endpoint");
        return "Admin resource";
    }
}
