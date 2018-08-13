package com.example.webservice.controller;

import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.entities.User;
import com.example.webservice.model.dao.ServerException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/user")
public class UserController {

    public static int SUCCESS_CODE = 0;
    public static int USER_ALREADY_EXISTS_CODE = 1;
    public static int SERVER_ERROR_CODE = 2;
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
                throw new UnsupportedOperationException("Incorrect request");
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return new Response(SERVER_ERROR_CODE);
        }
    }

    private Response processCreateUserRequest(Map<String, String> requestData) throws ServerException
    {
        String login = requestData.get(LOGIN);
        String password = requestData.get(PASSWORD);

        try {
            userDao.createIfNotExists(new User(login, password));
            return new Response(SUCCESS_CODE);
        } catch (UserDao.UserAlreadyExistsException e) {
            return new Response(USER_ALREADY_EXISTS_CODE);
        }
    }

    private Response processGetBalanceRequest(Map<String, String> requestData) throws ServerException
    {
        String login = requestData.get(LOGIN);
        String password = requestData.get(PASSWORD);

        try {
            double balance = userDao.checkPasswordAnGetBalance(login, password);
            Map<String, Object> extras = new HashMap<>();
            extras.put(BALANCE, balance);
            return new Response(SUCCESS_CODE, extras);
        } catch (UserDao.UserNotExistsException e) {
            return new Response(USER_NOT_EXISTS_CODE);
        } catch (UserDao.IncorrectPasswordException e) {
            return  new Response(INCORRECT_PASSWORD_CODE);
        }
    }
}
