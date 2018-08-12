package com.example.webservice.controller;

import com.example.webservice.model.dao.UserDaoStub;
import com.example.webservice.model.operations.CreateUserOperation;
import com.example.webservice.model.operations.GetBalanceOperation;
import com.example.webservice.model.operations.ServerException;
import com.example.webservice.model.operations.impl.CreateUserOperationImpl;
import com.example.webservice.model.operations.impl.GetBalanceOperationImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/user")
public class UserController {

    private static int SUCCESS_CODE = 0;
    private static int USER_ALREADY_EXISTS_CODE = 1;
    private static int SERVER_ERROR_CODE = 2;
    private static int USER_NOT_EXISTS_CODE = 3;
    private static int INCORRECT_PASSWORD_CODE = 4;

    private CreateUserOperation createUserOperation = new CreateUserOperationImpl(UserDaoStub.INSTANCE);
    private GetBalanceOperation getBalanceOperation = new GetBalanceOperationImpl(UserDaoStub.INSTANCE);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processRequest(Map<String, String> requestData)
    {
        String type = requestData.get("type");
        if("create".equals(type)){
            return processCreateUserRequest(requestData);
        } else if("get-balance".equals(type)) {
            return processGetBalanceRequest(requestData);
        } else {
            throw new UnsupportedOperationException("Incorrect request");
        }
    }

    private Response processCreateUserRequest(Map<String, String> requestData)
    {
        String login = requestData.get("login");
        String password = requestData.get("password");

        try {
            createUserOperation.createUser(login, password);
            return new Response(SUCCESS_CODE);
        } catch (CreateUserOperation.UserAlreadyExistsException e) {
            return new Response(USER_ALREADY_EXISTS_CODE);
        } catch (ServerException e) {
            return new Response(SERVER_ERROR_CODE);
        }
    }

    private Response processGetBalanceRequest(Map<String, String> requestData)
    {
        String login = requestData.get("login");
        String password = requestData.get("password");

        try {
            double balance = getBalanceOperation.getBalance(login, password);
            Map<String, Object> extras = new HashMap<>();
            extras.put("balance", balance);
            return new Response(SUCCESS_CODE, extras);
        } catch (GetBalanceOperation.UserNotExistsException e) {
            return new Response(USER_NOT_EXISTS_CODE);
        } catch (GetBalanceOperation.IncorrectPasswordException e) {
            return  new Response(INCORRECT_PASSWORD_CODE);
        } catch (ServerException e) {
            return new Response(SERVER_ERROR_CODE);
        }
    }
}
