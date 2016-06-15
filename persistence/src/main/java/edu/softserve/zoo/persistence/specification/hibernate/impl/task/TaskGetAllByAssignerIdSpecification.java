package edu.softserve.zoo.persistence.specification.hibernate.impl.task;

import edu.softserve.zoo.core.model.Employee;
import edu.softserve.zoo.core.model.Task;

/**
 * <p>Implementation of for filtering {@link Task} by assignerId parameter</p>
 *
 * @author Julia Avdeionok
 */
public class TaskGetAllByAssignerIdSpecification extends ParametrizedTasksSpecification<Long> {
    static private String fieldName = "assigner.id";

    /**
     * @param assignerId {@link Employee} define filtering properties value
     */
    public TaskGetAllByAssignerIdSpecification(Long assignerId) {
        super(fieldName, assignerId);
    }
}

