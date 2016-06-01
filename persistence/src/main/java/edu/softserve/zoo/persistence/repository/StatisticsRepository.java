package edu.softserve.zoo.persistence.repository;

/**
 * Repository class for zoo statistics
 *
 * @author Taras Zubrei
 */
public interface StatisticsRepository {
    /**
     * Returns fed animals.
     *
     * @return fed animals.
     */
    Long getFedAnimals();
}
