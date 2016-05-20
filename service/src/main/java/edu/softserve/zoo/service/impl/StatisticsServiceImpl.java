package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Taras Zubrei
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
    public Map<Task.TaskStatus, Long> getEmployeeTasksStatuses(final Long id) {
        return repo.getEmployeeTasksStatuses(id);
    }

    @Override
    public Map<Task.TaskType, Long> getEmployeeTasksTypes(Long id) {
        return repo.getEmployeeTasksTypes(id);
    }

    @Override
    public Map<String, Long> getZooStatistics() {
        return repo.getZooStatistics();
    }

}
