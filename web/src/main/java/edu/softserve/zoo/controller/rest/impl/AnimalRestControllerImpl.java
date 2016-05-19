package edu.softserve.zoo.controller.rest.impl;

import edu.softserve.zoo.controller.rest.AbstractRestController;
import edu.softserve.zoo.dto.AnimalDto;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.service.AnimalService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.ANIMALS;

/**
 * Animal domain object REST controller that provides specific methods along with CRUD
 *
 * @author Serhii Alekseichenko
 */
@RestController
@RequestMapping(ANIMALS)
public class AnimalRestControllerImpl extends AbstractRestController<AnimalDto, Animal> {

    @Autowired
    private AnimalService animalService;


    protected AnimalRestControllerImpl() {
        super(Animal.class, AnimalDto.class);
    }

    @Override
    protected Service<Animal> getService() {
        return animalService;
    }

    @RequestMapping(path = "/house/{id}", method = RequestMethod.GET)
    public List<AnimalDto> getAllByHouseId(@PathVariable Long id) {
        List<Animal> allByHouseId = animalService.getAllByHouseId(id);
        return convertToDto(allByHouseId);
    }

    @RequestMapping(path = "/species/{id}", method = RequestMethod.GET)
    public List<AnimalDto> getAllBySpeciesId(@PathVariable Long id) {
        List<Animal> allBySpeciesId = animalService.getAllBySpeciesId(id);
        return convertToDto(allBySpeciesId);
    }
}
