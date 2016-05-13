package edu.softserve.zoo.service;


import edu.softserve.zoo.model.BaseEntity;

public interface Service<T extends BaseEntity> {

    T save(T entity);

    void delete(T entity);

    T update(T entity);

}
