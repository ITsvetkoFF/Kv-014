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
    List<ImmutablePair<Task.TaskStatus.Status, Long>> getEmployeeTasksStatuses(Long id);
    List<ImmutablePair<Task.TaskType.Type, Long>> getEmployeeTasksTypes(Long id);
    Statistics getZooStatistics();
}
