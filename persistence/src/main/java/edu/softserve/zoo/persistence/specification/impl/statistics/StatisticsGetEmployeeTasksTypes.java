package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.util.LinkedHashMap;
import java.util.Map;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_TASK_TYPE;

/**
 * Specification for querying all employee's tasks grouped by type
 *
 * @author Taras Zubrei
 */
public class StatisticsGetEmployeeTasksTypes implements SQLSpecification {
    private Long id;

    public StatisticsGetEmployeeTasksTypes(Long id) {
        this.id = id;
    }

    @Override
    public String query() {
        return String.format(STAT_TASK_TYPE, id);
    }

    @Override
    public Map<String, Type> getScalar() {
        return new LinkedHashMap<String, Type>(){{
            put("type", IntegerType.INSTANCE);
            put("num", LongType.INSTANCE);
        }};
    }
}
