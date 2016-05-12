package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Role;

import java.util.Collection;

/**
 * @author Julia Siroshtan
 */
public interface RoleService extends Service<Role> {
    Collection<Role> getAll();
    Role findOne(Integer id);
}
