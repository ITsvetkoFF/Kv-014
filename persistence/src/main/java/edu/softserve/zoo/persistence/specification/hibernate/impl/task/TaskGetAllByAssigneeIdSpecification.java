package edu.softserve.zoo.persistence.specification.hibernate.impl.task;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;

/**
 * <p>Implementation of for filtering {@link Task} by assigneeId parameter</p>
 *
 * @author Julia Avdeionok
 */
public class TaskGetAllByAssigneeIdSpecification  extends ParametrizedTasksSpecification<Long> {
    static private String fieldName ="assignee.id";

    /**
     * @param assigneeId {@link Employee} define filtering properties value
     */
    public TaskGetAllByAssigneeIdSpecification(Long assigneeId) {
        super(fieldName, assigneeId);
    }
}