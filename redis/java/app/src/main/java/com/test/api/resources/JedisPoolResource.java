package com.test.api.resources;

import redis.clients.jedis.Jedis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jedispool")
public class JedisPoolResource {

    private Jedis jedis = new Jedis("redis1");

    @GET
    @Path("/put")
    @Produces(MediaType.TEXT_PLAIN)
    public String putIt() {

        jedis.set("user", "Alex");

        return "created entry";
    }


    @GET
    @Path("/get")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {

        System.out.println(String.format("User: %s", jedis.get("user")));

        return "retrieved entry";
    }
}