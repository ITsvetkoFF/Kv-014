package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;

/**
 * SQL Specification adapter for scalar queries
 * @author Taras Zubrei
 */
public abstract class SQLScalarSpecificationAdapter implements SQLSpecification {
    @Override
    public Class getType() {
        return null;
    }
}
