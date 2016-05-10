package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task.*;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.StatisticsRepository;
import edu.softserve.zoo.persistence.specification.hibernate.SQLScalarSpecification;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
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
        return (Float) persistenceProvider.find(new SQLScalarSpecification() {
            @Override
            public String query() {
                return "SELECT 1 - num/CAST(animals AS FLOAT) as result FROM (SELECT count(*) AS num FROM ZOO.TASKS WHERE TASK_TYPE_ID = 0 AND STATUS_ID IN (1,3)), (SELECT count(*) AS animals FROM ZOO.ANIMALS) WHERE animals > 0";
            }

            @Override
            public List<ImmutablePair<String, Type>> scalarValues() {
                return Collections.singletonList(new ImmutablePair<>("result", FloatType.INSTANCE));
            }
        }).stream().findFirst().orElse(.0f);
    }
    @Override
    public List<ImmutablePair<TaskStatus.Status, Integer>> getEmployeeTasksStatuses(final Employee employee) {
        final Collection<Object[]> list = persistenceProvider.find(new SQLScalarSpecification() {
            @Override
            public String query() {
                return "SELECT STATUS_ID as status, count(*) as num from ZOO.TASKS WHERE ASSIGNEE_ID = " + employee.getId() + " GROUP BY status";
            }

            @Override
            public List<ImmutablePair<String, Type>> scalarValues() {
                return Arrays.asList(new ImmutablePair<>("status", IntegerType.INSTANCE),
                        new ImmutablePair<>("num", IntegerType.INSTANCE));
            }
        });
        return list.stream().map(arr -> new ImmutablePair<>(TaskStatus.Status.values()[(Integer) arr[0]], (Integer) arr[1])).collect(Collectors.toList());
    }
    @Override
    public List<ImmutablePair<TaskType.Type, Integer>> getEmployeeTasksTypes(final Employee employee) {
        final Collection<Object[]> list = persistenceProvider.find(new SQLScalarSpecification() {
            @Override
            public String query() {
                return "SELECT TASK_TYPE_ID as type, count(*) as num from ZOO.TASKS WHERE ASSIGNEE_ID = " + employee.getId() + " GROUP BY type";
            }

            @Override
            public List<ImmutablePair<String, Type>> scalarValues() {
                return Arrays.asList(new ImmutablePair<>("type", IntegerType.INSTANCE),
                        new ImmutablePair<>("num", IntegerType.INSTANCE));
            }
        });
        return list.stream().map(arr -> new ImmutablePair<>(TaskType.Type.values()[(Integer) arr[0]], (Integer) arr[1])).collect(Collectors.toList());
    }
    @Override
    public ImmutableTriple<Integer, Integer, Integer> getZooStatistics() {
        final Object[] res = (Object[]) persistenceProvider.find(new SQLScalarSpecification() {
            @Override
            public String query() {
                return "SELECT * FROM" +
                        " (SELECT count(*) AS animals FROM ZOO.ANIMALS)," +
                        " (SELECT count(*) AS houses FROM ZOO.HOUSES)," +
                        " (SELECT count(*) AS employees FROM ZOO.EMPLOYEES)";
            }

            @Override
            public List<ImmutablePair<String, Type>> scalarValues() {
                return Arrays.asList(new ImmutablePair<>("animals", IntegerType.INSTANCE),
                        new ImmutablePair<>("houses", IntegerType.INSTANCE),
                        new ImmutablePair<>("employees", IntegerType.INSTANCE));
            }
        }).stream().findFirst().get();
        return new ImmutableTriple<>((Integer) res[0], (Integer) res[1], (Integer) res[2]);
    }
}
