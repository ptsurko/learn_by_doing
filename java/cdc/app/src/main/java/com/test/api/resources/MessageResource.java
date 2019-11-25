package com.test.api.resources;

import com.test.api.dao.MessageDao;
import com.test.api.dto.MessageDTO;
import com.test.api.models.Message;
import com.test.api.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
    private Logger logger = LogManager.getLogger(MessageResource.class);

    @Inject
    private MessageDao messageDao;

    @POST
    public Response postMessage() {
        Message newMessage = new Message("{\"user\": \"john\"}");
        Message createdMessage = messageDao.add(newMessage);
        return Response.ok(new MessageDTO(createdMessage)).build();
    }

    @GET
    public Response getSomething() {
        logger.info("Getting list of users");
        List<Message> messages = messageDao.findAll();
        List<MessageDTO> dtos = messages.stream().map(u -> new MessageDTO(u)).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{user_id}")
    public Response getMessage(@PathParam("user_id") Long userId) {
        Message message = messageDao.find(userId);
        if (message == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new MessageDTO(message)).build();
    }
}