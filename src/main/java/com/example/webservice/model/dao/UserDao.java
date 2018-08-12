package com.example.webservice.model.dao;

import com.example.webservice.model.entities.User;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.util.Map;

public interface UserDao extends GenericDao<User, Long> {

    public Long getByLogin(String login);

    public boolean checkPassword(Long key, String password);
}
