package edu.softserve.zoo.service;

/**
 * Service class for zoo statistics which uses autowired repository for database usage
 *
 * @author Taras Zubrei
 */
public interface StatisticsService {
    /**
     * Returns fed animals percentage which is between 0 and 1.
     *
     * @return fed animals percentage.
     */
    Float getFedAnimalsPercentage();
}
