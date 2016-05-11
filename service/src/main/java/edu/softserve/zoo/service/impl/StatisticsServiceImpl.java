package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Statistics;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.impl.StatisticsRepositoryImpl;
import edu.softserve.zoo.service.StatisticsService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Taras on 11.05.2016.
 */
@Transactional(readOnly = true)
@Service
public class StatisticsServiceImpl extends StatisticsRepositoryImpl implements StatisticsService {

    @Override
    public Float getFedAnimalsPercentage() {
        return super.getFedAnimalsPercentage();
    }

    @Override
    public List<ImmutablePair<Task.TaskStatus.Status, Integer>> getEmployeeTasksStatuses(Integer id) {
        Employee employee = new Employee();
        employee.setId(id);
        return super.getEmployeeTasksStatuses(employee);
    }

    @Override
    public List<ImmutablePair<Task.TaskType.Type, Integer>> getEmployeeTasksTypes(Integer id) {
        Employee employee = new Employee();
        employee.setId(id);
        return super.getEmployeeTasksTypes(employee);
    }

    @Override
    public Statistics getZooStatistics() {
        return super.getZooStatistics();
    }
}
