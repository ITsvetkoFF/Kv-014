package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.FamilyDto;
import edu.softserve.zoo.model.Family;
import edu.softserve.zoo.service.FamilyService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.FAMILIES;

/**
 * Rest controller for {@link Family} domain objects.
 *
 * @author Bohdan Cherniakh
 */
@RestController
@RequestMapping(FAMILIES)
public class FamilyRestController extends AbstractRestController<FamilyDto, Family> {

    @Autowired
    private FamilyService service;

    @RequestMapping(method = RequestMethod.GET)
    @Override
    public List<FamilyDto> getAll() {
        return super.getAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public FamilyDto getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    protected Service<Family> getService() {
        return service;
    }
}
