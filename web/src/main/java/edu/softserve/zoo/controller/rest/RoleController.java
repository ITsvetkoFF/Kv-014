package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.RoleDto;
import edu.softserve.zoo.model.Role;
import edu.softserve.zoo.service.RoleService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.AbstractRestController.API_V1;
import static edu.softserve.zoo.controller.rest.RoleController.ROLE;

/**
 * @author Julia Siroshtan
 */
@RestController
@RequestMapping(API_V1 + ROLE)
public class RoleController extends AbstractRestController<RoleDto, Role> {

    static final String ROLE = "/role";

    @Autowired
    private RoleService roleService;

    public RoleController() {
        super(Role.class, RoleDto.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<RoleDto> getAll() {
        Collection<Role> roles = roleService.getAll();
        return roles.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleDto getOne(@PathVariable Integer id) {
        return convertToDto(roleService.findOne(id));
    }

    @Override
    protected Service<Role> getService() {
        return roleService;
    }
}
