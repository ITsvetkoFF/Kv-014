package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.HouseDto;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class HouseRestController extends AbstractRestController<HouseDto, House> {

    @Autowired
    private HouseService houseService;

    protected HouseRestController() {
        super(House.class);
    }

    @Override
    protected Service<House> getService() {
        return houseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<HouseDto> getHousesFiltered(@RequestParam Map<String,String> allRequestParams) {
        List<House> result = houseService.find(allRequestParams);
        return converter.convertToDto(result);
    }
}
