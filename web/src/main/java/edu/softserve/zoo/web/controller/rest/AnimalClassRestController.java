package edu.softserve.zoo.web.controller.rest;

import edu.softserve.zoo.core.model.AnimalClass;
import edu.softserve.zoo.service.AnimalClassService;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.web.annotation.DocsClassDescription;
import edu.softserve.zoo.web.annotation.DocsParamDescription;
import edu.softserve.zoo.web.annotation.DocsTest;
import edu.softserve.zoo.web.dto.AnimalClassDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.web.controller.rest.Routes.ANIMAL_CLASSES;

/**
 * Rest controller for {@link AnimalClass} domain objects.
 *
 * @author Bohdan Cherniakh
 */
@RestController
@RequestMapping(ANIMAL_CLASSES)
@DocsClassDescription("Class resource")
public class AnimalClassRestController extends AbstractRestController<AnimalClassDto, AnimalClass> {

    @Autowired
    private AnimalClassService service;

    @DocsTest
    @RequestMapping(method = RequestMethod.GET)
    @Override
    public List<AnimalClassDto> getAll() {
        return super.getAll();
    }

    @DocsTest(pathParameters = "1")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public AnimalClassDto getById(@PathVariable @DocsParamDescription("The class id") Long id) {
        return super.getById(id);
    }

    @Override
    protected Service<AnimalClass> getService() {
        return service;
    }
}
