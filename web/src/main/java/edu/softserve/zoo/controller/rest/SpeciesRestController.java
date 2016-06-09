package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.SpeciesDto;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.SPECIES;

/**
 * Species domain object REST controller that provides specific methods along with CRUD
 *
 * @author Serhii Alekseichenko
 */
@RestController
@RequestMapping(SPECIES)
public class SpeciesRestController extends AbstractRestController<SpeciesDto, Species> {

    @Autowired
    private SpeciesService speciesService;

    @Override
    protected Service<Species> getService() {
        return speciesService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<SpeciesDto> getAll() {
        return super.getAll();
    }
}
