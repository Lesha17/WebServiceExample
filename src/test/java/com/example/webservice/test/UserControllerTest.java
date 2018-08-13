package com.example.webservice.test;

import com.example.webservice.ApplicationBinder;
import com.example.webservice.controller.Response;
import com.example.webservice.controller.UserController;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

public class UserControllerTest extends JerseyTest {
    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(UserController.class, JacksonFeature.class);
        config.register(new ApplicationBinder());
        return config;
    }

    @Test
    public void createUserTest() {

        String login = "12345";
        String password = "pwd123";

        Response response1 = createUser(login, password);
        Assert.assertEquals(UserController.SUCCESS_CODE, response1.getResult());

        Response response2 = createUser(login, password);
        Assert.assertEquals(UserController.USER_ALREADY_EXISTS_CODE, response2.getResult());
    }

    @Test
    public void getBalanceTest() {

        String login = "userWithBalance";
        String password = "1234";

        Response createUserResponse = createUser(login, password);
        Assert.assertEquals(UserController.SUCCESS_CODE, createUserResponse.getResult());

        checkGetBalanceSuccess(login, password);
        checkGetBalanceReturnsUserNotExists(login + "i", password);
        checkGetBalanceReturnsIncorrectPassword(login, password + "i");

    }

    private Response createUser(String login, String password) {
        Map<String, String> createUserRequest = new HashMap<>();

        createUserRequest.put(UserController.TYPE, UserController.CREATE_TYPE);
        createUserRequest.put(UserController.LOGIN, login);
        createUserRequest.put(UserController.PASSWORD, password);

        return postRequest(createUserRequest);
    }

    private void checkGetBalanceSuccess(String login, String password) {
        Response response = getBalance(login, password);

        Assert.assertEquals(UserController.SUCCESS_CODE, response.getResult());
        Assert.assertNotNull(response.getExtras());

        Object balance = response.getExtras().get(UserController.BALANCE);
        Assert.assertNotNull(balance);
        System.out.println(balance.getClass());
    }

    private void checkGetBalanceReturnsUserNotExists(String login, String password) {
        Response response = getBalance(login, password);

        Assert.assertEquals(UserController.USER_NOT_EXISTS_CODE, response.getResult());
    }

    private void checkGetBalanceReturnsIncorrectPassword(String login, String password) {
        Response response = getBalance(login, password);

        Assert.assertEquals(UserController.INCORRECT_PASSWORD_CODE, response.getResult());
    }

    private Response getBalance(String login, String password) {
        Map<String, String> getBalanceRequest = new HashMap<>();

        getBalanceRequest.put(UserController.TYPE, UserController.GET_BALANCE_TYPE);
        getBalanceRequest.put(UserController.LOGIN, login);
        getBalanceRequest.put(UserController.PASSWORD, password);

        return postRequest(getBalanceRequest);
    }


    private Response postRequest(Map<String, String> request) {
        Entity requestEntity = Entity.entity(request, MediaType.APPLICATION_JSON);
        return target("user").request().post(requestEntity, Response.class);
    }

}
