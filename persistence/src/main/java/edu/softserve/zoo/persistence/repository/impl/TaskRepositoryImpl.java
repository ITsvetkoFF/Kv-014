package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.persistence.provider.JdbcPersistenceProvider;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.specification.impl.statistics.TaskStatusesSpecification;
import edu.softserve.zoo.persistence.specification.impl.statistics.TaskTypesSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Implementation of the {@link TaskRepository} specific for {@link Task} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class TaskRepositoryImpl extends AbstractRepository<Task> implements TaskRepository {
    @Autowired
    private JdbcPersistenceProvider<Map<Task.TaskStatus, Long>> taskStatusPersistenceProvider;
    @Autowired
    private JdbcPersistenceProvider<Map<Task.TaskType, Long>> taskTypePersistenceProvider;

    @Override
    public TaskStatistics getStatistics(Long employeeId) {
        TaskStatistics statistics = new TaskStatistics();
        Map<Task.TaskStatus, Long> taskStatuses = taskStatusPersistenceProvider.findAll(new TaskStatusesSpecification<Object[]>(employeeId),
                arr -> new HashMap<Task.TaskStatus, Long>() {{put(Task.TaskStatus.values()[((BigInteger) arr[0]).intValue()], ((BigInteger) arr[1]).longValue());}})
                .stream()
                .reduce(new HashMap<>(), (resultMap, map) -> {
                    resultMap.putAll(map);
                    return resultMap;
                });
        Map<Task.TaskType, Long> taskTypes = taskTypePersistenceProvider.findAll(new TaskTypesSpecification<Object[]>(employeeId),
                arr -> new HashMap<Task.TaskType, Long>() {{put(Task.TaskType.values()[((BigInteger) arr[0]).intValue()], ((BigInteger) arr[1]).longValue());}})
                .stream()
                .reduce(new HashMap<>(), (resultMap, map) -> {
                    resultMap.putAll(map);
                    return resultMap;
                });
        statistics.setTaskStatuses(taskStatuses);
        statistics.setTaskTypes(taskTypes);
        return statistics;
    }
}
