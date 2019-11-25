package com.test.api.resources;

import redis.clients.jedis.Jedis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jedis")
public class JedisResource {

    private Jedis jedis = new Jedis("redis1");

    @GET
    @Path("/put")
    @Produces(MediaType.TEXT_PLAIN)
    public String putIt() {

        jedis.set("user", "Alex");

        jedis.set("count", "0");
        jedis.incr("count");

        jedis.lpush("tasks", "1");
        jedis.lpush("tasks", "2");
        jedis.lpush("tasks", "3");

        jedis.sadd("set", "1");
        jedis.sadd("set", "1");
        jedis.sadd("set", "2");

        jedis.hset("hash", "1", "hello");
        jedis.hset("hash", "2", "world");

        return "created entry";
    }


    @GET
    @Path("/get")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {

        System.out.println(String.format("User: %s", jedis.get("user")));

        System.out.println(String.format("Count: %s", jedis.get("count")));

        System.out.println(String.format("Tasks: %s", jedis.lrange("tasks", 0, jedis.llen("tasks"))));

        System.out.println(String.format("Sets: %s", jedis.smembers("set")));

        System.out.println(String.format("Hash: %s", jedis.hgetAll("hash")));

        return "retrieved entry";
    }
}