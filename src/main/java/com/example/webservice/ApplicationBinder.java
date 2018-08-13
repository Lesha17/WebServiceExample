package com.example.webservice;

import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.dao.UserDaoStub;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(UserDaoStub.INSTANCE).to(UserDao.class);
    }
}
