package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

import java.util.List;
import java.util.Map;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_TASK_STATUS;

/**
 * Specification for getting task counts grouped by statuses
 *
 * @author Taras Zubrei
 */
public class GetTaskStatusesStatistics<T extends Task> implements HQLSpecification<T> {
    private Long employeeId;

    public GetTaskStatusesStatistics(Long employeeId) {
        this.employeeId = employeeId;
    }
    @Override
    public String query() {
        return String.format(STAT_TASK_STATUS, employeeId);
    }
}
