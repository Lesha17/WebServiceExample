package com.example.webservice;

import com.example.webservice.model.dao.UserDao;
import com.example.webservice.model.dao.UserDaoStub;
import com.example.webservice.model.operations.CreateUserOperation;
import com.example.webservice.model.operations.GetBalanceOperation;
import com.example.webservice.model.operations.impl.CreateUserOperationImpl;
import com.example.webservice.model.operations.impl.GetBalanceOperationImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(UserDaoStub.INSTANCE).to(UserDao.class);
        bind(CreateUserOperationImpl.class).to(CreateUserOperation.class);
        bind(GetBalanceOperationImpl.class).to(GetBalanceOperation.class);
    }
}
