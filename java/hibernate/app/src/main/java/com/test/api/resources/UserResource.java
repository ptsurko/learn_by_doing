package com.test.api.resources;

import com.test.api.dao.UserDao;
import com.test.api.dto.UserDTO;
import com.test.api.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private Logger logger = LogManager.getLogger(UserResource.class);

    @Inject
    private UserDao userDao;

    @POST
    public Response createUser(UserDTO dto) {
        User newUser = new User(dto.getName());
        User createdUser = userDao.add(newUser);
        return Response.ok(new UserDTO(createdUser)).build();
    }

    @GET
    public Response getSomething() {
        logger.info("Getting list of users");
        List<User> users = userDao.findAll();
        List<UserDTO> dtos = users.stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{user_id}")
    public Response getUser(@PathParam("user_id") Long userId) {
        User user = userDao.find(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new UserDTO(user)).build();
    }
}