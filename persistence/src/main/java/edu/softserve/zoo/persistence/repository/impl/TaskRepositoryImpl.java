package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.persistence.provider.JdbcPersistenceProvider;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.specification.impl.statistics.TaskStatusesSpecification;
import edu.softserve.zoo.persistence.specification.impl.statistics.TaskTypesSpecification;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>Implementation of the {@link TaskRepository} specific for {@link Task} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class TaskRepositoryImpl extends AbstractRepository<Task> implements TaskRepository {
    @Autowired
    private JdbcPersistenceProvider<ImmutablePair<Task.TaskStatus, Long>> taskStatusPersistenceProvider;
    @Autowired
    private JdbcPersistenceProvider<ImmutablePair<Task.TaskType, Long>> taskTypePersistenceProvider;

    @Override
    public TaskStatistics getStatistics(Long employeeId) {
        TaskStatistics statistics = new TaskStatistics();
        Map<Task.TaskStatus, Long> taskStatuses = taskStatusPersistenceProvider.findAll(new TaskStatusesSpecification<Object[]>(employeeId),
                arr -> new ImmutablePair<>(Task.TaskStatus.values()[((BigInteger) arr[0]).intValue()], ((BigInteger) arr[1]).longValue()))
                .stream()
                .collect(toLinkedMap(pair -> pair.left, pair -> pair.right));
        Map<Task.TaskType, Long> taskTypes = taskTypePersistenceProvider.findAll(new TaskTypesSpecification<Object[]>(employeeId),
                arr -> new ImmutablePair<>(Task.TaskType.values()[((BigInteger) arr[0]).intValue()], ((BigInteger) arr[1]).longValue()))
                .stream()
                .collect(toLinkedMap(pair -> pair.left, pair -> pair.right));
        statistics.setTaskStatuses(taskStatuses);
        statistics.setTaskTypes(taskTypes);
        return statistics;
    }

    private <T, K, U> Collector<T, ?, Map<K,U>> toLinkedMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper)
    {
        return Collectors.toMap(keyMapper, valueMapper,
                (u, v) -> {throw new IllegalStateException(String.format("Duplicate key %s", u));},
                LinkedHashMap::new);
    }
}
