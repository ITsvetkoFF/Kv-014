package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.TasksRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Julia Avdeionok
 */
@Repository
public class TasksRepositoryImpl extends AbstractRepository<Task> implements TasksRepository {
}

