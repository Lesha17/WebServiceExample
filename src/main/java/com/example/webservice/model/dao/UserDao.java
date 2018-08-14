package com.example.webservice.model.dao;

import com.example.webservice.model.entities.User;

/**
 * User Data Access Object.
 */
public interface UserDao extends GenericDao<User, Long> {

    public void createIfNotExists(User user) throws UserAlreadyExistsException, ServerException;

    public double checkPasswordAnGetBalance(String login, String password)
            throws UserNotExistsException, IncorrectPasswordException, ServerException;

    public static class UserAlreadyExistsException extends Exception {
    }

    public class UserNotExistsException extends Exception {
    }

    public class IncorrectPasswordException extends Exception {
    }
}
