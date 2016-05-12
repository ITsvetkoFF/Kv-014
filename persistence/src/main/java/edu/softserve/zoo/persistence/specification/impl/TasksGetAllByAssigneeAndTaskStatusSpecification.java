package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * Created by Julia Avdeionok
 */
public class TasksGetAllByAssigneeAndTaskStatusSpecification implements HQLSpecification<Task> {

    private static final String ENTITY_NAME = Task.class.getSimpleName();
    private final Integer assigneeId;
    private final Task.TaskStatus.Status taskStatus;

    public TasksGetAllByAssigneeAndTaskStatusSpecification(Integer assigneeId, Task.TaskStatus.Status taskStatus) {
        this.assigneeId = assigneeId;
        this.taskStatus = taskStatus;
    }

    @Override
    public String query() {
         String HQL_QUERY = "from " + ENTITY_NAME + " task"
                + " where task.status.status=%d"
                + " and task.assignee=%d";

        return String.format(HQL_QUERY, taskStatus.ordinal(), assigneeId);

    }

}
