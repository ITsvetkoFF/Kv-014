package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.impl.Queries;
import edu.softserve.zoo.persistence.specification.jdbc.JdbcSpecification;

import java.math.BigInteger;

/**
 * @author Taras Zubrei
 */
public class FedAnimalsSpecification<T extends BigInteger> implements JdbcSpecification<BigInteger> {
    @Override
    public String query() {
        return Queries.STAT_FED_ANIMALS;
    }
}
