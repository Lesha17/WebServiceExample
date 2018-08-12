package com.example.webservice.model.operations.impl;

import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.dao.UserDaoStub;
import com.example.webservice.model.entities.User;
import com.example.webservice.model.operations.CreateUserOperation;
import com.example.webservice.model.operations.ServerException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CreateUserOperationImpl implements CreateUserOperation {
    private final UserDao userDao;

    public CreateUserOperationImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(String login, String password) throws UserAlreadyExistsException {
        Long key = userDao.getByLogin(login);
        if(key != null) {
            throw new UserAlreadyExistsException();
        }

        User user = new User(login, password);
        userDao.create(user);
    }
}
