package com.example.webservice.model.dao;

import com.example.webservice.model.entities.User;

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
    public Long getByLogin(String login) {
        return users.entrySet().stream().filter(e -> login.equals(e.getValue().getLogin())).map(Map.Entry::getKey)
                .findAny().orElse(null);
    }

    @Override
    public boolean checkPassword(Long key, String password) {
        User user = read(key);
        if(user == null) {
            throw new IllegalArgumentException();
        }
        return password.equals(user.getPassword());
    }

    private long generateKey() {
        synchronized (keyLock) {
            return currentKey++;
        }
    }
}
