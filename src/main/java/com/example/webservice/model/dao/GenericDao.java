package com.example.webservice.model.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface GenericDao <T, PK extends Serializable> {

    public PK create(T entity);

    public T read(PK key);

    public void update(PK key, T entity);

    public void delete(PK key);

    public Map<PK, T> getAll();
}
