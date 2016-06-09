package edu.softserve.zoo.persistence.provider.specification_processing.strategy.impl;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("HQLSpecification")
public class HQLProcessingStrategy<T extends BaseEntity> implements SpecificationProcessingStrategy<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public HQLProcessingStrategy() {
    }

    @Override
    public List<T> process(Specification<T> specification) {
        HQLSpecification<T> hqlSpecification = (HQLSpecification<T>) specification;
        return sessionFactory.getCurrentSession().createQuery(hqlSpecification.query()).list();
    }
}
