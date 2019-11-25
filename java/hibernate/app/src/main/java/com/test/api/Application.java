package com.test.api;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/app")
public class Application extends ResourceConfig {
    public Application() {
        packages("com.test");
        register(JacksonFeature.class);
        register(GuiceFeature.class);
    }
}
