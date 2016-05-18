package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya Doroshenko
 */
@Service
public class HouseServiceImpl extends AbstractService<House> implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Override
    public House save(House entity) {
        House house = super.save(entity);
        return houseRepository.findOne(house.getId(),House.class);
    }

    @Override
    Repository<House> getRepository() {
        return houseRepository;
    }
}
