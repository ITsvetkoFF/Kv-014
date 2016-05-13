package edu.softserve.zoo.persistence.specification.impl.statistics;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.impl.SQLScalarSpecificationAdapter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.util.Arrays;
import java.util.List;

/**
 *Specification for querying all employee's tasks grouped by type
 * @author Taras Zubrei
 */
public class StatisticsGetEmployeeTasksTypes extends SQLScalarSpecificationAdapter {
    private Employee employee;

    public StatisticsGetEmployeeTasksTypes(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String query() {
        return "SELECT TASK_TYPE_ID as type, count(*) as num from ZOO.TASKS WHERE ASSIGNEE_ID = " + employee.getId() + " GROUP BY type";
    }

    @Override
    public List<ImmutablePair<String, Type>> scalarValues() {
        return Arrays.asList(new ImmutablePair<>("type", IntegerType.INSTANCE),
                new ImmutablePair<>("num", LongType.INSTANCE));
    }
}
