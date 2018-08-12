package com.example.webservice.model.operations;

public interface GetBalanceOperation {

    public double getBalance(String login, String password) throws  UserNotExistsException, IncorrectPasswordException, ServerException;

    public class UserNotExistsException extends Exception {}

    public class IncorrectPasswordException extends Exception {}
}
