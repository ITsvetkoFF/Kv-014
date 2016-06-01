package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;

/**
 * <p>Specific repository for {@link Task} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
public interface TaskRepository extends Repository<Task> {

    TaskStatistics getStatistics(Long employeeId);
}
