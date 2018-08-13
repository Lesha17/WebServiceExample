package com.example.webservice.model.dao;

import com.example.webservice.model.entities.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDaoStub implements UserDao {

    public static final UserDao INSTANCE = new UserDaoStub();

    private final Map<Long, User> users = new HashMap<>();

    private long currentKey = 0;
    private final Object keyLock = new Object();

    private UserDaoStub() {};

    @Override
    public Long create(User entity) {
        Long key = generateKey();
        users.put(key, entity);
        return key;
    }

    @Override
    public User read(Long key) {
        return users.get(key);
    }

    @Override
    public void update(Long key, User entity) {
        users.put(key, entity);
    }

    @Override
    public void delete(Long key) {
        users.remove(key);
    }

    @Override
    public Map<Long, User> getAll() {
        return Collections.unmodifiableMap(users);
    }

    @Override
    public void createIfNotExists(User user) throws UserAlreadyExistsException {
        User foundUser = findUserByLogin(user.getLogin());
        if(foundUser == null) {
            Long key = generateKey();
            users.put(key, user);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public double checkPasswordAnGetBalance(String login, String password) throws UserNotExistsException, IncorrectPasswordException {
        User user = findUserByLogin(login);
        if(user == null) {
            throw new UserNotExistsException();
        }
        if(!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }
        return user.getBalance();
    }

    private User findUserByLogin(String login) {
        return users.values().stream().filter(user -> login.equals(user.getLogin()))
                .findAny().orElse(null);
    }

    private long generateKey() {
        synchronized (keyLock) {
            return currentKey++;
        }
    }
}
