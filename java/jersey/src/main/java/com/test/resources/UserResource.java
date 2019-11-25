package com.test.resources;

import com.test.domain.User;
import com.test.services.AppService;
import com.test.services.ConfigService;
import com.test.services.TestService;
import com.test.validation.Create;
import com.test.validation.Update;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    private TestService testService;

    @Inject
    private ConfigService configService;

    @Inject
    private Provider<AppService> appServiceProvider;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@Context ContainerRequestContext crc) {
        System.out.println("User endpoint");
        System.out.println("User: " + crc.getProperty("User"));
        this.testService.doSomething();
        this.configService.configure();
        this.appServiceProvider.get().app();
        return "Got it!";
    }

    @POST
    public User createUser(@Valid @ConvertGroup(to = Create.class) User user) {
        System.out.println("Creating user: " + user);
        return user;
    }

    @PUT
    public User updateUser(@Valid @ConvertGroup(to = Update.class) User user) {
        System.out.println("Updating user: " + user);
        return user;
    }

    @POST
    @Path("/validate")
    public User validate(@Valid User user) {
        System.out.println("Validating user: " + user);
        return user;
    }

    @POST
    @Path("/customvalidate")
    public User customValidate(User user) {
        if (user.id == null || user.name == null) {
            throw new BadRequestException();
        }

        System.out.println("Validating user: " + user);
        return user;
    }

}