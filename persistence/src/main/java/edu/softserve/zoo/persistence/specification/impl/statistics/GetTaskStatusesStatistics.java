package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_TASK_STATUS;

/**
 * Specification for getting task counts grouped by statuses
 *
 * @author Taras Zubrei
 */
public class GetTaskStatusesStatistics implements HQLSpecification {
    private Long id;

    public GetTaskStatusesStatistics(Long id) {
        this.id = id;
    }
    @Override
    public String query() {
        return String.format(STAT_TASK_STATUS, id);
    }
}
