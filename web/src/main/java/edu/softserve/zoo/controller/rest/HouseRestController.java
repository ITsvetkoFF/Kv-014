package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.HouseDto;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.HOUSES;

/**
 * House domain object REST controller that provides specific methods along with CRUD
 *
 * @author Serhii Alekseichenko
 */
@RestController
@RequestMapping(HOUSES)
public class HouseRestController extends AbstractRestController<HouseDto, House> {

    @Autowired
    private HouseService houseService;

    @Override
    protected Service<House> getService() {
        return houseService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<HouseDto> getAll() {
        return super.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, params = "zoneId")
    public List<HouseDto> getAllByZooZoneId(@RequestParam Long zoneId) {
        List<House> allByZoneId = houseService.getAllByZooZoneId(zoneId);
        return converter.convertToDto(allByZoneId);
    }
}
