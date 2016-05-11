package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Statistics;
import edu.softserve.zoo.model.Task;
import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.List;

/**
 * Created by Taras on 11.05.2016.
 */
public interface StatisticsService {
    Float getFedAnimalsPercentage();
    List<ImmutablePair<Task.TaskStatus.Status, Integer>> getEmployeeTasksStatuses(Integer id);
    List<ImmutablePair<Task.TaskType.Type, Integer>> getEmployeeTasksTypes(Integer id);
    Statistics getZooStatistics();
}
