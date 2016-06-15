package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.core.model.Role;
import edu.softserve.zoo.persistence.repository.RoleRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link RoleRepository} specific for {@link Role} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {
}
