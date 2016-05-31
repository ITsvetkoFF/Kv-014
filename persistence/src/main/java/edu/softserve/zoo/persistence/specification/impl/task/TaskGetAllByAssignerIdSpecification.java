package edu.softserve.zoo.persistence.specification.impl.task;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * <p>Implementation of {@link HQLSpecification} for filtering {@link Task}
 * by assignerId parameter with nested entities</p>
 *
 * @author Julia Avdeionok
 */
public class TaskGetAllByAssignerIdSpecification implements HQLSpecification<Task> {
    private static final String ENTITY_NAME = Task.class.getSimpleName();
    private Long assignerId;

    /**
     * @param assignerId {@link Employee} define filter for tasks
     */
    public TaskGetAllByAssignerIdSpecification(Long assignerId) {
        this.assignerId = assignerId;
    }

    @Override
    public String query() {
        String HQL_QUERY = "from " + ENTITY_NAME + " task "
                + "inner join fetch task.assignee "
                + "inner join fetch task.assigner "
                + "inner join fetch task.zone"
                + " where task.assigner=%d";
        return String.format(HQL_QUERY, assignerId);
    }

}