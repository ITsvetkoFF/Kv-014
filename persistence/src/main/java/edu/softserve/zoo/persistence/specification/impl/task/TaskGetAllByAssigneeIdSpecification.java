package edu.softserve.zoo.persistence.specification.impl.task;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * <p>Implementation of {@link HQLSpecification} for filtering {@link Task}
 * by assigneeId parameter with nested entities</p>
 *
 * @author Julia Avdeionok
 */
public class TaskGetAllByAssigneeIdSpecification implements HQLSpecification<Task> {
    private static final String ENTITY_NAME = Task.class.getSimpleName();
    private Long assigneeId;

    /**
     * @param assigneeId {@link Employee} define filter for tasks
     */
    public TaskGetAllByAssigneeIdSpecification(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Override
    public String query() {
        String HQL_QUERY = "from " + ENTITY_NAME + " task "
                + "inner join fetch task.assignee "
                + "inner join fetch task.assigner "
                + "inner join fetch task.zone"
                + " where task.assignee=%d";

        return String.format(HQL_QUERY, assigneeId);

    }
}
