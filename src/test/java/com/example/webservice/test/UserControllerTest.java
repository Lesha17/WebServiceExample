package com.example.webservice.test;

import com.example.webservice.controller.ResponseContent;
import com.example.webservice.controller.UserController;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

public class UserControllerTest extends JerseyTest {

    private Connection testConnection;

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(UserController.class, JacksonFeature.class);
        config.register(new TestBinder(() -> createConnection()));
        return config;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Class.forName("org.h2.Driver");
        this.testConnection = createConnection();

        RunScript.execute(testConnection, new FileReader(getClass().getResource("/sql/createtable/users.sql").getFile()));
    }

    @After
    public void shutdown() throws Exception {
        RunScript.execute(testConnection, new StringReader("DROP TABLE users"));

        testConnection.close();
    }

    @Test
    public void createUserTest() {

        String login = "12345";
        String password = "pwd123";

        ResponseContent response1 = createUser(login, password);
        Assert.assertEquals(ResponseContent.SUCCESS_CODE, response1.getResult());

        ResponseContent response2 = createUser(login, password);
        Assert.assertEquals(UserController.USER_ALREADY_EXISTS_CODE, response2.getResult());
    }

    @Test
    public void getBalanceTest() {

        String login = "userWithBalance";
        String password = "1234";

        ResponseContent createUserResponse = createUser(login, password);
        Assert.assertEquals(ResponseContent.SUCCESS_CODE, createUserResponse.getResult());

        checkGetBalanceSuccess(login, password);
        checkGetBalanceReturnsUserNotExists(login + "i", password);
        checkGetBalanceReturnsIncorrectPassword(login, password + "i");
    }

    @Test
    public void testCreateUserInManyThreads() throws Exception {
        int threadsCount = 100;
        int waitingTimeInSeconds = 60;

        String userName = "usr1234";
        String password = "1234";

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

        Collection<ResponseContent> results = Collections.synchronizedCollection(new ArrayList<>());
        for(int i = 0; i < threadsCount; ++i) {
            executor.submit(() -> results.add(createUser(userName, password)));
        }
        executor.shutdown();
        executor.awaitTermination(waitingTimeInSeconds, TimeUnit.SECONDS);

        Assert.assertEquals("Not all results collected", threadsCount, results.size());

        long successCount = results.stream()
                .filter(response -> response.getResult() == ResponseContent.SUCCESS_CODE)
                .count();
        long userAlreadyExistsCount = results.stream()
                .filter(response -> response.getResult() == UserController.USER_ALREADY_EXISTS_CODE)
                .count();

        Assert.assertEquals(1, successCount);
        Assert.assertEquals(threadsCount - 1, userAlreadyExistsCount);
    }

    private ResponseContent createUser(String login, String password) {
        Map<String, String> createUserRequest = new HashMap<>();

        createUserRequest.put(UserController.TYPE, UserController.CREATE_TYPE);
        createUserRequest.put(UserController.LOGIN, login);
        createUserRequest.put(UserController.PASSWORD, password);

        return postRequest(createUserRequest);
    }

    private void checkGetBalanceSuccess(String login, String password) {
        ResponseContent response = getBalance(login, password);

        Assert.assertEquals(ResponseContent.SUCCESS_CODE, response.getResult());
        Assert.assertNotNull(response.getExtras());

        Object balance = response.getExtras().get(UserController.BALANCE);
        Assert.assertNotNull(balance);
    }

    private void checkGetBalanceReturnsUserNotExists(String login, String password) {
        ResponseContent response = getBalance(login, password);

        Assert.assertEquals(UserController.USER_NOT_EXISTS_CODE, response.getResult());
    }

    private void checkGetBalanceReturnsIncorrectPassword(String login, String password) {
        ResponseContent response = getBalance(login, password);

        Assert.assertEquals(UserController.INCORRECT_PASSWORD_CODE, response.getResult());
    }

    private ResponseContent getBalance(String login, String password) {
        Map<String, String> getBalanceRequest = new HashMap<>();

        getBalanceRequest.put(UserController.TYPE, UserController.GET_BALANCE_TYPE);
        getBalanceRequest.put(UserController.LOGIN, login);
        getBalanceRequest.put(UserController.PASSWORD, password);

        return postRequest(getBalanceRequest);
    }


    private ResponseContent postRequest(Map<String, String> request) {
        Entity requestEntity = Entity.entity(request, MediaType.APPLICATION_JSON);
        return target("user").request().post(requestEntity, ResponseContent.class);
    }

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:h2:mem:default",null, null);
    }

    private Connection getConnectionProxy(Connection connection) {
        return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{Connection.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getName().equals("close") && method.getParameterCount() == 0) {
                    return null;
                } else {
                    return method.invoke(connection, args);
                }
            }
        });
    }


}
