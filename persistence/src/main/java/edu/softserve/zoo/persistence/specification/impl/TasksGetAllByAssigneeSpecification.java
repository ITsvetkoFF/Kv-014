package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * Created by Julia Avdeionok
 */
public class TasksGetAllByAssigneeSpecification implements HQLSpecification<Task> {

    private static final String ENTITY_NAME = Task.class.getSimpleName();
    private final Integer assigneeId;

    public TasksGetAllByAssigneeSpecification(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Override
    public String query() {
        String HQL_QUERY = "from " + ENTITY_NAME + " task"
                + " where task.assignee=%d";

        return String.format(HQL_QUERY, assigneeId);

    }


}
