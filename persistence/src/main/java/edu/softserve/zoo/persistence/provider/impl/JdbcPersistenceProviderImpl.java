package edu.softserve.zoo.persistence.provider.impl;

import edu.softserve.zoo.persistence.provider.JdbcPersistenceProvider;
import edu.softserve.zoo.persistence.specification.jdbc.JdbcSpecification;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Persistence provider for native sql queries and jdbc processing
 *
 * @author Taras Zubrei
 */
@Component
public class JdbcPersistenceProviderImpl implements JdbcPersistenceProvider {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    public <T, K> List<T> findAll(JdbcSpecification<K> specification, Function<K, T> processor) {
        List<K> list = sessionFactory.getCurrentSession().createSQLQuery(specification.query()).list();
        return list.stream().map(processor).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public <T, K> T findOne(JdbcSpecification<K> specification, Function<K, T> processor) {
        return processor.apply((K) sessionFactory.getCurrentSession().createSQLQuery(specification.query()).uniqueResult());
    }
}
