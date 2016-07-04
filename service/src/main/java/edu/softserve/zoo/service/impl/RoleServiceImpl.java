package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Role;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.RoleRepository;
import edu.softserve.zoo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ilya Doroshenko
 */
@Service
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    Repository<Role> getRepository() {
        return roleRepository;
    }

    @Override
    Class<Role> getType() {
        return Role.class;
    }
}
