package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_FED_ANIMALS;

/**
 * Specification for getting percentage of fed animals. Result is between 0 and 1.
 *
 * @author Taras Zubrei
 */
public class StatisticsGetFedAnimalTasksSpecification implements HQLSpecification {
    @Override
    public String query() {
        return STAT_FED_ANIMALS;
    }
}
