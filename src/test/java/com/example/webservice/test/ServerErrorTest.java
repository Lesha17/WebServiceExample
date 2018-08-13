package com.example.webservice.test;

import com.example.webservice.ApplicationBinder;
import com.example.webservice.controller.UserController;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class ServerErrorTest extends JerseyTest {
    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(ServerErrorWebService.class, JacksonFeature.class);
        config.register(new ApplicationBinder());
        return config;
    }

    @Test
    public void testServerError() {
        Response response = target("/error").request().get();
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        Assert.assertNotNull(response.getEntity());
    }

}
