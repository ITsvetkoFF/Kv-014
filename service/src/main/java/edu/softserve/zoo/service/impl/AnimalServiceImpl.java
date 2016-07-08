package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.repository.AnimalRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalFindOneWithBirthdayHouseAndSpeciesSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalGetAllByHouseIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalGetAllBySpeciesIdSpecification;
import edu.softserve.zoo.service.AnimalService;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.SpeciesService;
import edu.softserve.zoo.service.exception.AnimalException;
import edu.softserve.zoo.service.exception.HouseException;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link AnimalService} logic for {@link Animal} entity
 *
 * @author Serhii Alekseichenko
 */
@Service
public class AnimalServiceImpl extends AbstractService<Animal> implements AnimalService {

    @Autowired
    private AnimalRepository repository;

    @Autowired
    private HouseService houseService;

    @Autowired
    private SpeciesService speciesService;

    @Override
    @Transactional
    public List<Animal> getAllByHouseId(Long houseId) {
        return repository.find(new AnimalGetAllByHouseIdSpecification(houseId));
    }

    @Override
    @Transactional
    public List<Animal> getAllBySpeciesId(Long speciesId) {
        return repository.find(new AnimalGetAllBySpeciesIdSpecification(speciesId));
    }

    @Override
    public Animal findOneWithBirthdayHouseAndSpecies(Long id) {
        return repository.findOne(new AnimalFindOneWithBirthdayHouseAndSpeciesSpecification(id));
    }

    @Override
    Repository<Animal> getRepository() {
        return repository;
    }

    @Override
    Class<Animal> getType() {
        return Animal.class;
    }

    @Override
    @Transactional
    public Animal save(Animal entity) {
        try {
            Species species = speciesService.findOneWithAnimalsPerHouse(entity.getSpecies().getId());
            Validator.notNull(species, ApplicationException.getBuilderFor(AnimalException.class)
                    .forReason(AnimalException.Reason.WRONG_SPECIES)
                    .withMessage("Wrong animal species provided").build());
            validateHouseForNewAnimal(entity.getHouse().getId(), species);
            Animal animal = super.save(entity);
            houseService.increaseHouseCapacity(animal.getHouse().getId(), species.getAnimalsPerHouse());
            return animal;
        } catch (ApplicationException ex) {
            throw ApplicationException.getBuilderFor(AnimalException.class)
                    .forReason(AnimalException.Reason.SAVE_FAILED)
                    .causedBy(ex)
                    .withMessage("Animal save is failed")
                    .withQualificationReason(ex.getReason())
                    .build();
        }
    }

    @Override
    @Transactional
    public Animal update(Animal entity) {
        try {
            Animal animal = findOneWithBirthdayHouseAndSpecies(entity.getId());
            Validator.notNull(animal, ApplicationException.getBuilderFor(ValidationException.class)
                    .forReason(NotFoundException.Reason.BY_ID)
                    .withMessage("Animal for update is not founded").build());
            Long oldHouseId = animal.getHouse().getId();
            Long oldSpeciesId = animal.getSpecies().getId();
            Validator.isTrue(Objects.equals(oldSpeciesId, entity.getSpecies().getId()),
                    ApplicationException.getBuilderFor(AnimalException.class)
                            .forReason(AnimalException.Reason.SPECIES_CHANGED)
                            .withMessage("Attempt to change animal species").build());
            Validator.isTrue(Objects.equals(animal.getBirthday(), entity.getBirthday()),
                    ApplicationException.getBuilderFor(AnimalException.class)
                            .forReason(AnimalException.Reason.BIRTHDAY_CHANGED)
                            .withMessage("Attempt to change animal birthday").build());
            Long newHouseId = entity.getHouse().getId();
            Species species = speciesService.findOneWithAnimalsPerHouse(animal.getSpecies().getId());
            BeanUtils.copyProperties(entity, animal);
            if (!Objects.equals(oldHouseId, newHouseId)) {
                validateHouseForNewAnimal(newHouseId, species);
                animal = super.update(animal);
                houseService.decreaseHouseCapacity(oldHouseId, species.getAnimalsPerHouse());
                houseService.increaseHouseCapacity(newHouseId, species.getAnimalsPerHouse());
            } else {
                animal = super.update(animal);
            }
            return animal;
        } catch (ApplicationException ex) {
            throw ApplicationException.getBuilderFor(AnimalException.class)
                    .forReason(AnimalException.Reason.UPDATE_FAILED)
                    .causedBy(ex)
                    .withQualificationReason(ex.getReason())
                    .withMessage("Animal update is failed")
                    .build();
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            validateNullableArgument(id);
            Animal animal = findOneWithBirthdayHouseAndSpecies(id);
            Validator.notNull(animal,
                    ApplicationException.getBuilderFor(NotFoundException.class)
                            .forReason(NotFoundException.Reason.BY_ID)
                            .withMessage("Not found animal with id: " + id).build());
            Species species = speciesService.findOneWithAnimalsPerHouse(animal.getSpecies().getId());
            super.delete(id);
            houseService.decreaseHouseCapacity(animal.getHouse().getId(), species.getAnimalsPerHouse());
        } catch (ApplicationException ex) {
            throw ApplicationException.getBuilderFor(AnimalException.class)
                    .forReason(AnimalException.Reason.DELETE_FAILED)
                    .causedBy(ex)
                    .withQualificationReason(ex.getReason())
                    .withMessage("Animal deletion is failed")
                    .build();
        }
    }

    private void validateHouseForNewAnimal(Long houseId, Species species) {
        House house = houseService.findOne(houseId);
        Long currentCapacity = houseService.getHouseCurrentCapacity(house.getId());
        boolean isHouseCanApplyNewAnimal = currentCapacity + species.getAnimalsPerHouse() <= house.getMaxCapacity();
        Validator.isTrue(isHouseCanApplyNewAnimal, ApplicationException.getBuilderFor(HouseException.class)
                .forReason(HouseException.Reason.HOUSE_IS_FULL)
                .withMessage("Attempt to put animal in full house").build());
        List<House> acceptableForNewAnimal = houseService.getAllAcceptableForNewAnimalBySpeciesId(species.getId());
        acceptableForNewAnimal.stream().map(BaseEntity::getId).filter(acceptableHouseId -> acceptableHouseId.equals(houseId))
                .findAny().orElseThrow(() -> ApplicationException.getBuilderFor(HouseException.class)
                .forReason(HouseException.Reason.WRONG_HOUSE)
                .withMessage("House is not suitable for this type of animal species").build());
    }
}
