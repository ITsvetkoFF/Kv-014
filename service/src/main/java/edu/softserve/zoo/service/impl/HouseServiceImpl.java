package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.composite.fields.HouseField;
import edu.softserve.zoo.persistence.specification.hibernate.impl.house.GetAllHousesByZooZoneId;
import edu.softserve.zoo.persistence.specification.hibernate.impl.house.HouseGetAllBySpeciesIdSpecification;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public House save(House entity) {
        House savedHouse = super.save(entity);
        repository.getCapacityMap().put(savedHouse.getId(), 0L);
        return savedHouse;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
        repository.getCapacityMap().remove(id);
    }

    @Override
    @Transactional
    public List<House> getAllByZooZoneId(Long id) {
        return getAllByZooZoneId(id, null);
    }

    @Override
    @Transactional
    public List<House> getAllByZooZoneId(Long id, Set<HouseField> fields) {
        GetAllHousesByZooZoneId specification = new GetAllHousesByZooZoneId(id);
        specification.setEntityFields(fields);
        return repository.find(specification);
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
    public Map<Long, Long> getHousesCurrentCapacityMap() {
        return repository.getCapacityMap();
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
