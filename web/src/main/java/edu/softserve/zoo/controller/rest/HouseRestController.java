package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsParamDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.dto.AnimalDto;
import edu.softserve.zoo.dto.HouseDto;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static edu.softserve.zoo.controller.rest.Routes.HOUSES;

/**
 * House domain object REST controller that provides specific methods along with CRUD
 *
 * @author Serhii Alekseichenko
 */
@RestController
@RequestMapping(HOUSES)
@DocsClassDescription("House resource")
public class HouseRestController extends AbstractRestController<HouseDto, House> {

    @Autowired
    private HouseService houseService;

    @Override
    protected Service<House> getService() {
        return houseService;
    }

    @DocsTest(pathParameters = "1")
    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public HouseDto getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public HouseDto create(@RequestBody HouseDto dto) {
        return super.create(dto);
    }

    @DocsTest(pathParameters = "1")
    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public HouseDto update(@RequestBody HouseDto dto, @PathVariable Long id) {
        return super.update(dto, id);
    }

    @DocsTest(pathParameters = "1")
    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @DocsTest
    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<HouseDto> getAll() {
        return super.getAll();
    }

    @DocsTest(pathParameters = "1")
    @RequestMapping(method = RequestMethod.GET, params = "zoneId")
    public List<HouseDto> getAllByZooZoneId(@RequestParam @DocsParamDescription("The zone") Long zoneId) {
        List<House> allByZoneId = houseService.getAllByZooZoneId(zoneId);
        return converter.convertToDto(allByZoneId);
    }

    @DocsTest(pathParameters = "159795")
    @RequestMapping(method = RequestMethod.GET, params = "speciesId")
    public List<AnimalDto> getAllBySpeciesId(@RequestParam @DocsParamDescription("Species id") Long speciesId) {
        List<House> allBySpeciesId = houseService.getAllAcceptableForNewAnimalBySpeciesId(speciesId);
        return converter.convertToDto(allBySpeciesId);
    }

    @DocsTest
    @RequestMapping(path = "/populations", method = RequestMethod.GET)
    public Map<Long, Long> getCapacityMap() {
        return houseService.getHousesCurrentCapacityMap();
    }

    @DocsTest
    @RequestMapping(path="/count", method = RequestMethod.GET)
    @Override
    public Long count() {
        return super.count();
    }
}
