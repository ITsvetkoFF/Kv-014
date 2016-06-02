package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.dto.AnimalDto;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.service.AnimalService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.ANIMALS;

/**
 * Animal domain object REST controller that provides specific methods along with CRUD
 *
 * @author Serhii Alekseichenko
 */
@RestController
@RequestMapping(ANIMALS)
public class AnimalRestController extends AbstractRestController<AnimalDto, Animal> {

    @Autowired
    private AnimalService animalService;

    @Override
    protected Service<Animal> getService() {
        return animalService;
    }

    @DocsTest
    @RequestMapping(method = RequestMethod.GET)
    @Override
    public List<AnimalDto> getAll() {
        return super.getAll();
    }

    @DocsTest(pathParameters = "1")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public AnimalDto getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Override
    public AnimalDto create(@RequestBody AnimalDto dto) {
        return super.create(dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    @Override
    public AnimalDto update(@RequestBody AnimalDto dto, @PathVariable Long id) {
        return super.update(dto, id);
    }

    @DocsTest(pathParameters = "1")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @Override
    public ResponseEntity delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @DocsTest(pathParameters = "1")
    @RequestMapping(path = "/house/{id}", method = RequestMethod.GET)
    public List<AnimalDto> getAllByHouseId(@PathVariable Long id) {
        List<Animal> allByHouseId = animalService.getAllByHouseId(id);
        return converter.convertToDto(allByHouseId);
    }
    @DocsTest(pathParameters = "161130")
    @RequestMapping(path = "/species/{id}", method = RequestMethod.GET)
    public List<AnimalDto> getAllBySpeciesId(@PathVariable Long id) {
        List<Animal> allBySpeciesId = animalService.getAllBySpeciesId(id);
        return converter.convertToDto(allBySpeciesId);
    }
}
