package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Andrii Abramov on 11-May-16.
 */
public abstract class GetByIdSpecification<T> implements HQLSpecification<T> {

    private final String ENTITY_NAME;
    private final int ID;

    public GetByIdSpecification(String entityName, Integer id) {
        this.ID = id;
        this.ENTITY_NAME = entityName;
    }


    @Override
    public String query() {
        return String.format("from %s w where w.id = %d", ENTITY_NAME, ID);
    }
}
