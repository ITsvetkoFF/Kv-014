package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_TASK_TYPE;

/**
 * Specification for getting task counts grouped by types
 *
 * @author Taras Zubrei
 */
public class GetTaskTypesStatistics implements HQLSpecification {
    private Long id;

    public GetTaskTypesStatistics(Long id) {
        this.id = id;
    }
    @Override
    public String query() {
        return String.format(STAT_TASK_TYPE, id);
    }
}
