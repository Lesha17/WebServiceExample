package com.example.webservice.model.operations.impl;

import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.dao.UserDaoStub;
import com.example.webservice.model.entities.User;
import com.example.webservice.model.operations.GetBalanceOperation;
import com.example.webservice.model.operations.ServerException;

import javax.inject.Inject;

public class GetBalanceOperationImpl implements GetBalanceOperation {

    private final UserDao userDao;

    @Inject
    public GetBalanceOperationImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public double getBalance(String login, String password) throws IncorrectPasswordException, UserNotExistsException {
        Long key = userDao.getByLogin(login);
        if(key == null) {
            throw new UserNotExistsException();
        }
        checkPassword(key, password);

        User user = userDao.read(key);
        return user.getBalance();
    }

    protected void checkPassword(Long key, String password) throws IncorrectPasswordException {
        if(!userDao.checkPassword(key, password)) {
            throw new IncorrectPasswordException();
        }
    }
}
