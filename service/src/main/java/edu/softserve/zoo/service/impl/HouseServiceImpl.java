package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.house.GetAllByZooZoneIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.house.HouseGetAllBySpeciesIdSpecification;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link HouseService} logic for {@link House} entity
 *
 * @author Serhii Alekseichenko
 */
@Service
public class HouseServiceImpl extends AbstractService<House> implements HouseService {

    @Autowired
    private HouseRepository repository;

    @Autowired
    private SpeciesService speciesService;

    @Override
    @Transactional
    public List<House> getAllByZooZoneId(Long id) {
        return repository.find(new GetAllByZooZoneIdSpecification(id));
    }

    @Override
    @Transactional
    public List<House> getAllAcceptableForNewAnimalBySpeciesId(Long speciesId) {
        List<House> houses = repository.find(new HouseGetAllBySpeciesIdSpecification(speciesId));
        Species species = speciesService.findOne(speciesId);

        return houses.stream().filter(house -> {
            Long houseCapacity = repository.getCapacityMap().get(house.getId());
            return house.getMaxCapacity() - houseCapacity >= species.getAnimalsPerHouse();
        }).collect(Collectors.toList());
    }


    @Override
    public Long getHouseCurrentCapacity(Long houseId) {
        return repository.getCapacityMap().get(houseId);
    }

    @Override
    @Transactional
    public List<House> getAllBySpeciesId(Long speciesId) {
        return repository.find(new HouseGetAllBySpeciesIdSpecification(speciesId));
    }

    @Override
    public void increaseHouseCapacity(Long houseId, Integer animalPerHouse) {
        Long houseCapacity = getHouseCurrentCapacity(houseId) + animalPerHouse;
        repository.getCapacityMap().put(houseId, houseCapacity);
    }

    @Override
    public void decreaseHouseCapacity(Long houseId, Integer animalPerHouse) {
        Long houseCapacity = getHouseCurrentCapacity(houseId) - animalPerHouse;
        repository.getCapacityMap().put(houseId, houseCapacity);
    }

    @Override
    Repository<House> getRepository() {
        return repository;
    }

    @Override
    Class<House> getType() {
        return House.class;
    }
}
