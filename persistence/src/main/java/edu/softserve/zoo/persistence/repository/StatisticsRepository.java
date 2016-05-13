package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Statistics;
import edu.softserve.zoo.model.Task;
import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.List;

/**
 * @author Taras Zubrei
 */
public interface StatisticsRepository {
    Float getFedAnimalsPercentage();
    List<ImmutablePair<Task.TaskStatus.Status, Long>> getEmployeeTasksStatuses(Employee employee);
    List<ImmutablePair<Task.TaskType.Type, Long>> getEmployeeTasksTypes(Employee employee);
    Statistics getZooStatistics();
}
