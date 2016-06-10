package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.persistence.provider.impl.JdbcPersistenceProvider;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_TASK_STATUS;
import static edu.softserve.zoo.persistence.specification.impl.Queries.STAT_TASK_TYPE;

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
        Map<Task.TaskStatus, Long> taskStatuses = taskStatusPersistenceProvider.<Object[]>findAll(String.format(STAT_TASK_STATUS, employeeId), arr -> new HashMap<Task.TaskStatus, Long>() {{put(Task.TaskStatus.values()[((BigInteger) arr[0]).intValue()], ((BigInteger) arr[1]).longValue());}})
                .stream()
                .reduce(new HashMap<>(), (resultMap, map) -> {
                    resultMap.putAll(map);
                    return resultMap;
                });
        Map<Task.TaskType, Long> taskTypes = taskTypePersistenceProvider.<Object[]>findAll(String.format(STAT_TASK_TYPE, employeeId), arr -> new HashMap<Task.TaskType, Long>() {{put(Task.TaskType.values()[((BigInteger) arr[0]).intValue()], ((BigInteger) arr[1]).longValue());}})
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
