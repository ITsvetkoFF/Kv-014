package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.impl.Queries;
import edu.softserve.zoo.persistence.specification.jdbc.JdbcSpecification;

/**
 * @author Taras Zubrei
 */
public class TaskTypesSpecification<T> implements JdbcSpecification<T> {
    private Long employeeId;

    public TaskTypesSpecification(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String query() {
        return String.format(Queries.STAT_TASK_TYPE, employeeId);
    }
}
