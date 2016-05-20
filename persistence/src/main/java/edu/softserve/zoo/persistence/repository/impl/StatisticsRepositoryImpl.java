package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task.TaskStatus;
import edu.softserve.zoo.model.Task.TaskType;
import edu.softserve.zoo.persistence.exception.PersistenceReason;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetEmployeeTasksStatuses;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetEmployeeTasksTypes;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetFedAnimalsSpecification;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetZooStatistics;
import edu.softserve.zoo.util.CollectionUtils;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Taras Zubrei
 */
@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {
    @Autowired
    PersistenceProvider persistenceProvider;
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Float getFedAnimalsPercentage() {
        return CollectionUtils.<Float>getSingleItemOrElse(persistenceProvider.find(new StatisticsGetFedAnimalsSpecification()),
                ApplicationException
                        .getBuilderFor(PersistenceException.class)
                        .forReason(PersistenceReason.HIBERNATE_QUERY_FAILED)
                        .withMessage("Invalid SQL result")
                        .build(), .0F);
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Map<TaskStatus, Long> getEmployeeTasksStatuses(final Long id) {
        Validator.notNull(persistenceProvider.findOne(id, Employee.class),
                ApplicationException
                        .getBuilderFor(NotFoundException.class)
                        .forReason(PersistenceReason.NOT_FOUND)
                        .withMessage("Invalid employee id supplied")
                        .build());
        final Collection<Object[]> list = persistenceProvider.find(new StatisticsGetEmployeeTasksStatuses(id));
        return list.stream().collect(toLinkedMap(arr -> TaskStatus.values()[(Integer) arr[0]], arr -> (Long) arr[1]));
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Map<TaskType, Long> getEmployeeTasksTypes(final Long id) {
        Validator.notNull(persistenceProvider.findOne(id, Employee.class),
                ApplicationException
                        .getBuilderFor(NotFoundException.class)
                        .forReason(PersistenceReason.NOT_FOUND)
                        .withMessage("Invalid employee id supplied")
                        .build());
        final Collection<Object[]> list = persistenceProvider.find(new StatisticsGetEmployeeTasksTypes(id));
        return list.stream().collect(toLinkedMap(arr -> TaskType.values()[(Integer) arr[0]], arr -> (Long) arr[1]));
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Map<String, Long> getZooStatistics() {
        final Object[] res = CollectionUtils.<Object[]>getSingleItemOrElse(persistenceProvider.find(new StatisticsGetZooStatistics()),
                ApplicationException
                        .getBuilderFor(PersistenceException.class)
                        .forReason(PersistenceReason.HIBERNATE_QUERY_FAILED)
                        .withMessage("Invalid SQL result")
                        .build(), new Object[]{0L, 0L, 0L});
        return new HashMap<String, Long>() {{
            put("animalsCount", (Long) res[0]);
            put("housesCount", (Long) res[1]);
            put("employeesCount", (Long) res[2]);
        }};
    }
    private <T, K, U> Collector<T, ?, Map<K,U>> toLinkedMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper)
    {
        return Collectors.toMap(keyMapper, valueMapper,
                (u, v) -> {
                    throw new IllegalStateException(String.format("Duplicate key %s", u));
                },
                LinkedHashMap::new);
    }
}
