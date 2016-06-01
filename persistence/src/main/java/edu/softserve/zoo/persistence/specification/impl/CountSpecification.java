package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

import static edu.softserve.zoo.persistence.specification.impl.Queries.COUNT;

/**
 * @author Taras Zubrei
 */
public class CountSpecification<T> implements HQLSpecification<T> {
    private final Class<T> FOR_ENTITY;

    /**
     * @param forEntity Entity class
     */
    public CountSpecification(Class<T> forEntity) {
        this.FOR_ENTITY = forEntity;
    }

    @Override
    public String query() {
        return String.format(COUNT, FOR_ENTITY.getSimpleName());
    }
}
