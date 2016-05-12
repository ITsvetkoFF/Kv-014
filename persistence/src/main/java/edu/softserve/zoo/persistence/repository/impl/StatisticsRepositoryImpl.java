package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Statistics;
import edu.softserve.zoo.model.Task.*;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.persistence.specification.hibernate.SQLScalarSpecification;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetEmployeeTasksStatuses;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetEmployeeTasksTypes;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetFedAnimalsSpecification;
import edu.softserve.zoo.persistence.specification.impl.statistics.StatisticsGetZooStatistics;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Taras Zubrei on 03.05.2016.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
public class StatisticsRepositoryImpl implements StatisticsRepository {
    @Autowired
    PersistenceProvider persistenceProvider;
    @Override
    public Float getFedAnimalsPercentage() {
        return (Float) persistenceProvider.find(new StatisticsGetFedAnimalsSpecification()).stream().findFirst().orElse(.0f);
    }
    @Override
    public List<ImmutablePair<TaskStatus.Status, Integer>> getEmployeeTasksStatuses(final Employee employee) {
        final Collection<Object[]> list = persistenceProvider.find(new StatisticsGetEmployeeTasksStatuses(employee));
        return list.stream().map(arr -> new ImmutablePair<>(TaskStatus.Status.values()[(Integer) arr[0]], (Integer) arr[1])).collect(Collectors.toList());
    }
    @Override
    public List<ImmutablePair<TaskType.Type, Integer>> getEmployeeTasksTypes(final Employee employee) {
        final Collection<Object[]> list = persistenceProvider.find(new StatisticsGetEmployeeTasksTypes(employee) );
        return list.stream().map(arr -> new ImmutablePair<>(TaskType.Type.values()[(Integer) arr[0]], (Integer) arr[1])).collect(Collectors.toList());
    }
    @Override
    public Statistics getZooStatistics() {
        final Object[] res = (Object[]) persistenceProvider.find(new StatisticsGetZooStatistics()).stream().findFirst().get();
        return new Statistics((Integer) res[0], (Integer) res[1], (Integer) res[2]);
    }
}
