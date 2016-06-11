package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.impl.Queries;
import edu.softserve.zoo.persistence.specification.jdbc.JdbcSpecification;

/**
 * @author Taras Zubrei
 */
public class TaskStatusesSpecification<T> implements JdbcSpecification<T> {
    private Long employeeId;

    public TaskStatusesSpecification(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String query() {
        return String.format(Queries.STAT_TASK_STATUS, employeeId);
    }
}
