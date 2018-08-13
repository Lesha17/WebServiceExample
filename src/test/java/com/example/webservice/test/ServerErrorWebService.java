package com.example.webservice.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/error")
public class ServerErrorWebService {

    @GET
    public Response makeError() {
        throw new RuntimeException("Server error");
    }
}
