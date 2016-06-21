package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.jdbc.JdbcSpecification;

import javax.persistence.Table;
import java.math.BigInteger;

/**
 * @author Taras Zubrei
 */
public class CountSpecification<T extends BigInteger> implements JdbcSpecification<T> {
    private Class<? extends BaseEntity> entity;

    public CountSpecification(Class<? extends BaseEntity> entity) {
        this.entity = entity;
    }

    @Override
    public String query() {
        return String.format(Queries.COUNT, entity.getAnnotation(Table.class).name());
    }
}
