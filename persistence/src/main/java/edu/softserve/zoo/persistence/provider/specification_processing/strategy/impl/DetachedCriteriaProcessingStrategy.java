package edu.softserve.zoo.persistence.provider.specification_processing.strategy.impl;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("DetachedCriteriaSpecification")
public class DetachedCriteriaProcessingStrategy<T extends BaseEntity> implements SpecificationProcessingStrategy<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public DetachedCriteriaProcessingStrategy() {

    }

    @Override
    public List<T> process(Specification<T> specification) {
        DetachedCriteriaSpecification<T> detachedCriteriaSpecification
                = (DetachedCriteriaSpecification<T>) specification;
        return detachedCriteriaSpecification.query().getExecutableCriteria(sessionFactory.getCurrentSession()).list();
    }
}
