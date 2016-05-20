package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.util.LinkedHashMap;
import java.util.Map;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_TASK_STATUS;

/**
 * Specification for querying all employee's tasks grouped by status
 *
 * @author Taras Zubrei
 */
public class StatisticsGetEmployeeTasksStatuses implements SQLSpecification {
    private Long id;

    public StatisticsGetEmployeeTasksStatuses(Long id) {
        this.id = id;
    }

    @Override
    public String query() {
        return String.format(STAT_TASK_STATUS, id);
    }

    @Override
    public Map<String, Type> getScalar() {
        return new LinkedHashMap<String, Type>(){{
            put("status", IntegerType.INSTANCE);
            put("num", LongType.INSTANCE);
        }};
    }
}
