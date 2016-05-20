package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.hibernate.type.FloatType;
import org.hibernate.type.Type;

import java.util.LinkedHashMap;
import java.util.Map;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_FED_ANIMALS;

/**
 * Specification for getting percentage of fed animals. Result is between 0 and 1.
 *
 * @author Taras Zubrei
 */
public class StatisticsGetFedAnimalsSpecification implements SQLSpecification {
    @Override
    public String query() {
        return STAT_FED_ANIMALS;
    }

    @Override
    public Map<String, Type> getScalar() {
        return new LinkedHashMap<String, Type>(){{
            put("result", FloatType.INSTANCE);
        }};
    }
}
