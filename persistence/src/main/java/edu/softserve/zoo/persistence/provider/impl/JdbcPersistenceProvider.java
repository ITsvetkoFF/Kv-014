package edu.softserve.zoo.persistence.provider.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Taras Zubrei
 */
@Component
public class JdbcPersistenceProvider<T> {
    @Autowired
    private SessionFactory sessionFactory;
    public <K> List<T> findAll(String sqlQuery, Function<K,T> processor) {
        List<K> list = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).list();
        return list.stream().map(processor).collect(Collectors.toList());
    }
    public <K> T findOne(String sqlQuery, Function<K,T> processor) {
        return processor.apply((K) sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).uniqueResult());
    }
}
