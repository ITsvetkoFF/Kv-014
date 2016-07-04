package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.dto.RoleDto;
import edu.softserve.zoo.model.Role;
import edu.softserve.zoo.service.RoleService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.ROLES;

/**
 * @author Ilya Doroshenko
 */
@RestController
@RequestMapping(ROLES)
@DocsClassDescription("Roles resource")
public class RoleRestController extends AbstractRestController<RoleDto, Role>{

    @Autowired
    private RoleService roleService;

    @DocsTest
    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<RoleDto> getAll() {
        return super.getAll();
    }

    @Override
    protected Service<Role> getService() {
        return roleService;
    }
}
