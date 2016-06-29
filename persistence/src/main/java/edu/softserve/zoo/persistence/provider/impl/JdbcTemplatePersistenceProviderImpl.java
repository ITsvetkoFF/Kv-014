package edu.softserve.zoo.persistence.provider.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.persistence.exception.PersistenceProviderException;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.provider.JdbcTemplatePersistenceProvider;
import edu.softserve.zoo.persistence.specification.hibernate.SqlGetMapSpecification;
import edu.softserve.zoo.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of JdbcTemplate based persistence provider for native SQL queries.
 *
 * @author Serhii Alekseichenko
 */
@Component
public class JdbcTemplatePersistenceProviderImpl implements JdbcTemplatePersistenceProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplatePersistenceProviderImpl.class);
    private static final String ERROR_LOG_TEMPLATE = "An exception occurred during {} operation. Message: {}";

    @Autowired
    private JdbcTemplate template;

    /**
     * {@inheritDoc}
     */
    public <K extends Number, V extends Number> Map<K, V> getMap(SqlGetMapSpecification<K, V> specification) {
        try {
            Validator.notNull(specification, ApplicationException.getBuilderFor(PersistenceProviderException.class)
                    .forReason(PersistenceProviderException.Reason.SPECIFICATION_IS_NULL)
                    .withMessage("Specification can not be null")
                    .build());
            Validator.notNull(specification.getKeyType(), ApplicationException.getBuilderFor(SpecificationException.class)
                    .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                    .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null key type")
                    .build());
            Validator.notNull(specification.getValueType(), ApplicationException.getBuilderFor(SpecificationException.class)
                    .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                    .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null value type")
                    .build());
            Map<K, V> map = new HashMap<>();
            template.query(specification.query(), rs -> {
                K key = rs.getObject(1, specification.getKeyType());
                V value = rs.getObject(2, specification.getValueType());
                map.put(key, value);
            });
            return map;
        } catch (DataAccessException ex) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "getMap", ex.getMessage());
            throw ApplicationException.getBuilderFor(PersistenceProviderException.class)
                    .forReason(PersistenceProviderException.Reason.JDBC_TEMPLATE_QUERY_FAILED)
                    .causedBy(ex).withMessage(ex.getMessage()).build();
        }
    }
}