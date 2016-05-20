package edu.softserve.zoo.persistence.provider.specification_processing.strategy.impl;

import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("SQLSpecification")
public class SQLProcessingStrategy<T> implements SpecificationProcessingStrategy<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public SQLProcessingStrategy() {
    }

    @Override
    public List<T> process(Specification<T> specification) {
        SQLSpecification<T> sqlSpecification = (SQLSpecification<T>) specification;
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlSpecification.query());
        try{
            for(Map.Entry<String, Type> entry : sqlSpecification.getScalar().entrySet())
                query = query.addScalar(entry.getKey(),entry.getValue());
            return query.list();
        } catch (NotYetImplementedException ex) {
            return query.addEntity(sqlSpecification.getType()).list();
        }
    }
}
