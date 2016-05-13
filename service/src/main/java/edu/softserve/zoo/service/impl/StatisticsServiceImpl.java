package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Statistics;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.service.StatisticsService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Taras on 11.05.2016.
 */
@Transactional(readOnly = true)
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    StatisticsRepository repo;

    @Override
    public Float getFedAnimalsPercentage() {
        return repo.getFedAnimalsPercentage();
    }

    @Override
    public List<ImmutablePair<Task.TaskStatus.Status, Long>> getEmployeeTasksStatuses(Long id) {
        Employee employee = new Employee();
        employee.setId(id);
        return repo.getEmployeeTasksStatuses(employee);
    }

    @Override
    public List<ImmutablePair<Task.TaskType.Type, Long>> getEmployeeTasksTypes(Long id) {
        Employee employee = new Employee();
        employee.setId(id);
        return repo.getEmployeeTasksTypes(employee);
    }

    @Override
    public Statistics getZooStatistics() {
        return repo.getZooStatistics();
    }
}
