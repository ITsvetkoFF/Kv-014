package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import java.util.List;

/**
 * Created by Taras Zubrei on 04.05.2016.
 */
public interface StatisticsRepository {
    Float getFedAnimalsPercentage();
    List<ImmutablePair<Task.TaskStatus.Status, Integer>> getEmployeeTasksStatuses(Employee employee);
    List<ImmutablePair<Task.TaskType.Type, Integer>> getEmployeeTasksTypes(Employee employee);
    ImmutableTriple<Integer, Integer, Integer> getZooStatistics();
}
