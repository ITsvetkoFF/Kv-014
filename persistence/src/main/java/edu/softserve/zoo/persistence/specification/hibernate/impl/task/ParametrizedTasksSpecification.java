package edu.softserve.zoo.persistence.specification.hibernate.impl.task;

import edu.softserve.zoo.core.model.Task;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * <p>Implementation of {@link DetachedCriteriaSpecification} for filtering {@link Task} with nested entities</p>
 *
 * @param <T> the type of the properties
 * @author Julia Avdeionok
 */
class ParametrizedTasksSpecification<T> implements DetachedCriteriaSpecification<Task> {
    private String fieldName;
    private T fieldValue;

    /**
     * @param fieldName  define filtering properties name
     * @param fieldValue define filtering properties value
     */
    ParametrizedTasksSpecification(String fieldName, T fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public DetachedCriteria query() {

        DetachedCriteria criteria = DetachedCriteria.forClass(Task.class, "task");
        criteria.createAlias("zone", "zone")
                .createAlias("assigner", "assigner")
                .createAlias("assignee", "assignee")
                .add(Restrictions.eq(fieldName, fieldValue));
        return criteria;
    }

}
