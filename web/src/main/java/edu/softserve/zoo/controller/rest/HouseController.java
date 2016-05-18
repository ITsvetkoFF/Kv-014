package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.HouseDto;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static edu.softserve.zoo.controller.rest.Routes.HOUSE;

/**
 * @author Ilya Doroshenko
 */
@RestController
@RequestMapping(HOUSE)
public class HouseController extends AbstractRestController<HouseDto, House>{

    @Autowired
    private HouseService houseService;

    public HouseController() {
        super(House.class, HouseDto.class);
    }

    @Override
    protected Service<House> getService() {
        return houseService;
    }

    @Override
    public HouseDto getById(@PathVariable Long id) {
        return convertToDto(houseService.findOne(id, House.class));
    }
}
