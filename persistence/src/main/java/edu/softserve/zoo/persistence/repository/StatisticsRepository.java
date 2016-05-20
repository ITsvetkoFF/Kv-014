package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;

import java.util.Map;

/**
 * Repository class for zoo statistics
 *
 * @author Taras Zubrei
 */
public interface StatisticsRepository {
    /**
     * Returns fed animals percentage which is between 0 and 1.
     *
     * @return fed animals percentage.
     */
    Float getFedAnimalsPercentage();
    /**
     * Returns map of {@link Task.TaskStatus} enum as key and its count as value.
     *
     * @param id - {@link Employee} id which tasks statuses will be analyzed.
     * @return map of TaskStatus enum and its count.
     */
    Map<Task.TaskStatus, Long> getEmployeeTasksStatuses(Long id);
    /**
     * Returns map of {@link Task.TaskType} enum as key and its count as value.
     *
     * @param id - {@link Employee} id which tasks types will be analyzed.
     * @return map of TaskType enum and its count.
     */
    Map<Task.TaskType, Long> getEmployeeTasksTypes(Long id);
    /**
     * Returns Map of strings as key and count as value which include zoo general statistics
     *
     * @return overall statistics.
     */
    Map<String, Long> getZooStatistics();
}
