package com.example.webservice.test;

import com.example.webservice.ApplicationBinder;
import com.example.webservice.model.connection.ConnectionFactory;
import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.dao.UserDaoImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.sql.Connection;

public class TestBinder extends ApplicationBinder {
    private final ConnectionFactory connectionFactory;

    public TestBinder(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    protected void configure() {
        bind(connectionFactory).to(ConnectionFactory.class);

        super.configure();
    }
}
