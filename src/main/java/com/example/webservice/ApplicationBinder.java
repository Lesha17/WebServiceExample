package com.example.webservice;

import com.example.webservice.model.connection.ConnectionFactory;
import com.example.webservice.model.connection.PostgreSQLConnectionFactory;
import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.dao.UserDaoImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.sql.Connection;
import java.sql.SQLException;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(PostgreSQLConnectionFactory.class).to(ConnectionFactory.class);
        bind(UserDaoImpl.class).to(UserDao.class);
    }
}
