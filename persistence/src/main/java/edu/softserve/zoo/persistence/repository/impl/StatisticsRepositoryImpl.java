package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Statistics;
import edu.softserve.zoo.model.Task.TaskStatus;
import edu.softserve.zoo.model.Task.TaskType;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetEmployeeTasksStatuses;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetEmployeeTasksTypes;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetFedAnimalsSpecification;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetZooStatistics;
import edu.softserve.zoo.utils.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Taras Zubrei
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
public class StatisticsRepositoryImpl implements StatisticsRepository {
    @Autowired
    PersistenceProvider persistenceProvider;
    @Override
    public Float getFedAnimalsPercentage() {
        return CollectionUtils.<Float>getSingleItemOrElse(persistenceProvider.find(new StatisticsGetFedAnimalsSpecification()),
                ApplicationException
                        .getBuilderFor(PersistenceException.class)
                        .withMessage("Invalid SQL result")
                        .forReason(ExceptionReason.NOT_FOUND)
                        .build(), .0F);
    }
    @Override
    public List<ImmutablePair<TaskStatus.Status, Long>> getEmployeeTasksStatuses(final Employee employee) {
        final Collection<Object[]> list = persistenceProvider.find(new StatisticsGetEmployeeTasksStatuses(employee));
        return list.stream().map(arr -> new ImmutablePair<>(TaskStatus.Status.values()[(Integer) arr[0]], (Long) arr[1])).collect(Collectors.toList());
    }
    @Override
    public List<ImmutablePair<TaskType.Type, Long>> getEmployeeTasksTypes(final Employee employee) {
        final Collection<Object[]> list = persistenceProvider.find(new StatisticsGetEmployeeTasksTypes(employee) );
        return list.stream().map(arr -> new ImmutablePair<>(TaskType.Type.values()[(Integer) arr[0]], (Long) arr[1])).collect(Collectors.toList());
    }
    @Override
    public Statistics getZooStatistics() {
        final Object[] res = CollectionUtils.<Object[]>getSingleItemOrElse(persistenceProvider.find(new StatisticsGetZooStatistics()),
                ApplicationException
                        .getBuilderFor(PersistenceException.class)
                        .withMessage("Invalid SQL result")
                        .forReason(ExceptionReason.NOT_FOUND)
                        .build(), new Object[]{0L, 0L, 0L});
        return new Statistics((Long) res[0], (Long) res[1], (Long) res[2]);
    }
}
