package com.example.webservice;

import com.example.webservice.controller.ResponseContent;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class ApplicationExceptionMapper implements ExceptionMapper {
    @Override
    public Response toResponse(Throwable throwable) {
        throwable.printStackTrace(); // It's the easiest way of logging

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ResponseContent(ResponseContent.SERVER_ERROR_CODE))
                .build();
    }
}
