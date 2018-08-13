package com.example.webservice.controller;

import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.entities.User;
import com.example.webservice.model.dao.ServerException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/user")
public class UserController {

    public static int USER_ALREADY_EXISTS_CODE = 1;
    public static int USER_NOT_EXISTS_CODE = 3;
    public static int INCORRECT_PASSWORD_CODE = 4;

    public static final String TYPE = "type";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String BALANCE = "balance";

    public static final String CREATE_TYPE = "create";
    public static final String GET_BALANCE_TYPE = "get-balance";

    @Inject
    private UserDao userDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processRequest(Map<String, String> requestData)
    {
        String type = requestData.get(TYPE);
        try {
            if (CREATE_TYPE.equals(type)) {
                return processCreateUserRequest(requestData);
            } else if (GET_BALANCE_TYPE.equals(type)) {
                return processGetBalanceRequest(requestData);
            } else {
                return processUnsupportedRequestType(type);
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return processServerException(e);
        }
    }

    private Response processCreateUserRequest(Map<String, String> requestData) throws ServerException
    {
        String login = requestData.get(LOGIN);
        String password = requestData.get(PASSWORD);

        ResponseContent responseContent;
        try {
            userDao.createIfNotExists(new User(login, password));
            responseContent = new ResponseContent(ResponseContent.SUCCESS_CODE);
        } catch (UserDao.UserAlreadyExistsException e) {
            responseContent = new ResponseContent(USER_ALREADY_EXISTS_CODE);
        }
        return createOKResponse(responseContent);
    }

    private Response processGetBalanceRequest(Map<String, String> requestData) throws ServerException
    {
        String login = requestData.get(LOGIN);
        String password = requestData.get(PASSWORD);

        ResponseContent responseContent;
        try {
            double balance = userDao.checkPasswordAnGetBalance(login, password);
            Map<String, Object> extras = new HashMap<>();
            extras.put(BALANCE, balance);
            responseContent = new ResponseContent(ResponseContent.SUCCESS_CODE, extras);
        } catch (UserDao.UserNotExistsException e) {
            responseContent = new ResponseContent(USER_NOT_EXISTS_CODE);
        } catch (UserDao.IncorrectPasswordException e) {
            responseContent = new ResponseContent(INCORRECT_PASSWORD_CODE);
        }
        return createOKResponse(responseContent);
    }

    private Response processUnsupportedRequestType(String requestType) {
        return Response.status(Response.Status.BAD_REQUEST).entity("Bad request type").build();
    }


    private Response processServerException(ServerException e) {
        ResponseContent responseContent = new ResponseContent(ResponseContent.SERVER_ERROR_CODE);
        return createServerErrorResponse(responseContent);
    }

    private Response createOKResponse(ResponseContent content) {
        return Response.status(Response.Status.OK).entity(content).build();
    }

    private Response createServerErrorResponse(ResponseContent content) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
    }
}
