package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Role;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.impl.RoleRepository;
import edu.softserve.zoo.persistence.specification.impl.RoleGetAllSpecification;
import edu.softserve.zoo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Julia Siroshtan
 */
@Service
public class RoleServiceImpl extends ServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public Collection<Role> getAll() {
        return roleRepository.find(new RoleGetAllSpecification());
    }

    @Transactional
    @Override
    public Role findOne(Integer id) {
        Collection<Role> roles = getAll();
        return roles.stream().filter(r -> r.getId().equals(id)).findFirst().get();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        roleRepository.delete(findOne(id));
    }

    @Override
    Repository<Role> getRepository() {
        return roleRepository;
    }
}
