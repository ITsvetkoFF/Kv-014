package edu.softserve.zoo.persistence.provider.specification_processing.strategy.impl;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("SQLSpecification")
public class SQLProcessingStrategy<T extends BaseEntity> implements SpecificationProcessingStrategy<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public SQLProcessingStrategy() {
    }

    @Override
    public List<T> process(Specification<T> specification) {
        SQLSpecification<T> sqlSpecification = (SQLSpecification<T>) specification;
        return sessionFactory.getCurrentSession().createSQLQuery(sqlSpecification.query()).addEntity(sqlSpecification.getType()).list();
    }
}
