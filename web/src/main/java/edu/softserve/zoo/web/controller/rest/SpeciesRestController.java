package edu.softserve.zoo.web.controller.rest;

import edu.softserve.zoo.core.model.Species;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.SpeciesService;
import edu.softserve.zoo.web.annotation.DocsClassDescription;
import edu.softserve.zoo.web.annotation.DocsTest;
import edu.softserve.zoo.web.dto.SpeciesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.web.controller.rest.Routes.SPECIES;

/**
 * Species domain object REST controller that provides specific methods along with CRUD
 *
 * @author Serhii Alekseichenko
 */
@RestController
@RequestMapping(SPECIES)
@DocsClassDescription("Species resource")
public class SpeciesRestController extends AbstractRestController<SpeciesDto, Species> {

    @Autowired
    private SpeciesService speciesService;

    @Override
    protected Service<Species> getService() {
        return speciesService;
    }

    @DocsTest
    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<SpeciesDto> getAll() {
        return super.getAll();
    }
}
