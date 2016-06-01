package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.specification.impl.statistics.GetTaskStatusesStatistics;
import edu.softserve.zoo.persistence.specification.impl.statistics.GetTaskTypesStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Implementation of the {@link TaskRepository} specific for {@link Task} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class TaskRepositoryImpl extends AbstractRepository<Task> implements TaskRepository {
    @Autowired
    private PersistenceProvider persistenceProvider;

    @Override
    public TaskStatistics getStatistics(Long employeeId) {
        TaskStatistics statistics = new TaskStatistics();
        Map<Task.TaskStatus, Long> taskStatuses = ((List<Map>) persistenceProvider.find(new GetTaskStatusesStatistics(employeeId)))
                .stream()
                .collect(Collectors.toMap(map -> (Task.TaskStatus) map.get("0"), entry -> (Long) entry.get("1")));
        Map<Task.TaskType, Long> taskTypes = ((List<Map>) persistenceProvider.find(new GetTaskTypesStatistics(employeeId)))
                .stream()
                .collect(Collectors.toMap(map -> (Task.TaskType) map.get("0"), entry -> (Long) entry.get("1")));
        statistics.setTaskStatuses(taskStatuses);
        statistics.setTaskTypes(taskTypes);
        return statistics;
    }
}
