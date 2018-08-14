package com.example.webservice.model.dao;

import java.io.Serializable;
import java.util.Map;

/**
 * Data Access Object base.
 *
 * @param <T> entity type
 * @param <PK> primary key type
 */
public interface GenericDao<T, PK extends Serializable> {

    public PK create(T entity);

    public T read(PK key);

    public void update(PK key, T entity);

    public void delete(PK key);

    public Map<PK, T> getAll();
}
