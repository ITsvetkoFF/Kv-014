package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.util.LinkedHashMap;
import java.util.Map;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_GENERAL;

/**
 * Specification for getting overall zoo statistics
 *
 * @author Taras Zubrei
 */
public class StatisticsGetZooStatistics implements SQLSpecification {
    @Override
    public String query() {
        return STAT_GENERAL;
    }

    @Override
    public Map<String, Type> getScalar() {
        return new LinkedHashMap<String, Type>(){{
            put("animals", LongType.INSTANCE);
            put("houses", LongType.INSTANCE);
            put("employees", LongType.INSTANCE);
        }};
    }
}
