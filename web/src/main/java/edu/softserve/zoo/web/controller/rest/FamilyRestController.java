package edu.softserve.zoo.web.controller.rest;

import edu.softserve.zoo.core.model.Family;
import edu.softserve.zoo.service.FamilyService;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.web.annotation.DocsClassDescription;
import edu.softserve.zoo.web.annotation.DocsParamDescription;
import edu.softserve.zoo.web.annotation.DocsTest;
import edu.softserve.zoo.web.dto.FamilyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.web.controller.rest.Routes.FAMILIES;

/**
 * Rest controller for {@link Family} domain objects.
 *
 * @author Bohdan Cherniakh
 */
@RestController
@RequestMapping(FAMILIES)
@DocsClassDescription("Family resource")
public class FamilyRestController extends AbstractRestController<FamilyDto, Family> {

    @Autowired
    private FamilyService service;

    @DocsTest
    @RequestMapping(method = RequestMethod.GET)
    @Override
    public List<FamilyDto> getAll() {
        return super.getAll();
    }

    @DocsTest(pathParameters = "159811")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public FamilyDto getById(@PathVariable @DocsParamDescription("The family id") Long id) {
        return super.getById(id);
    }

    @Override
    protected Service<Family> getService() {
        return service;
    }
}
