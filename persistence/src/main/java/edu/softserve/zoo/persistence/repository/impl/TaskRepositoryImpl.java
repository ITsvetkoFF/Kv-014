package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link TaskRepository} specific for {@link Task} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class TaskRepositoryImpl extends AbstractRepository<Task> implements TaskRepository {
}
