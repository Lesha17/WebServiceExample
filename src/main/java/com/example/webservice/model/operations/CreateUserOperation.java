package com.example.webservice.model.operations;

import com.example.webservice.model.entities.User;

public interface CreateUserOperation {

    public void createUser(String login, String password) throws UserAlreadyExistsException, ServerException;

    public static class UserAlreadyExistsException extends Exception {};
}
