package edu.softserve.zoo.service.test;

import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.service.AnimalService;
import edu.softserve.zoo.service.HouseService;
import edu.softserve.zoo.service.SpeciesService;
import edu.softserve.zoo.service.config.ServiceConfig;
import edu.softserve.zoo.service.exception.AnimalException;
import edu.softserve.zoo.service.exception.HouseException;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Serhii Alekseichenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@Transactional
public class AnimalServiceTest {

    private static final Long EXISTENT_ANIMAL_ID = 1L;
    private static final Long EXISTENT_HOUSE_ID = 1L;
    private static final Long NEW_HOUSE_ID = 13L;
    private static final Long NOT_SUITABLE_HOUSE_ID = 10L;
    private static final Long NOT_EXISTENT_HOUSE_ID = 100L;
    private static final Long EXISTENT_SPECIES_ID = 161130L;
    private static final Long VALID_SPECIES_ID = 159793L;
    private static final int ANIMALS_IN_EXISTENT_HOUSE = 4;
    private Long existentHouseCapacity;
    private Integer existentAnimalAnimalsPerHouse;


    @Autowired
    private AnimalService animalService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private SpeciesService speciesService;

    @PostConstruct
    public void init() {
        existentHouseCapacity = houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID);
        existentAnimalAnimalsPerHouse = speciesService.findOne(EXISTENT_SPECIES_ID).getAnimalsPerHouse();
    }

    @Test
    public void getAllByHouseId() throws Exception {
        List<Animal> allByHouseId = animalService.getAllByHouseId(EXISTENT_HOUSE_ID);
        Assert.assertEquals(ANIMALS_IN_EXISTENT_HOUSE, allByHouseId.size());
        allByHouseId.forEach(animal -> Assert.assertEquals(EXISTENT_HOUSE_ID, animal.getHouse().getId()));
    }

    @Test(expected = SpecificationException.class)
    public void getAllByHouseIdNullId() throws Exception {
        animalService.getAllByHouseId(null);
    }

    @Test
    public void getAllBySpeciesId() throws Exception {
        List<Animal> allBySpeciesId = animalService.getAllBySpeciesId(EXISTENT_SPECIES_ID);
        Assert.assertEquals(1, allBySpeciesId.size());
        allBySpeciesId.forEach(animal -> Assert.assertEquals(EXISTENT_SPECIES_ID, animal.getSpecies().getId()));
    }

    @Test(expected = SpecificationException.class)
    public void getAllBySpeciesIdNullId() throws Exception {
        animalService.getAllBySpeciesId(null);
    }

    @Test
    public void findOneWithBirthdayHouseAndSpecies() throws Exception {
        Animal actualAnimal = animalService.findOneWithBirthdayHouseAndSpecies(EXISTENT_ANIMAL_ID);
        Assert.assertNotNull(actualAnimal.getBirthday());
        Assert.assertNotNull(actualAnimal.getHouse());
        Assert.assertNotNull(actualAnimal.getSpecies());
        Assert.assertNull(actualAnimal.getNickname());
        Assert.assertNull(actualAnimal.getFoodConsumption());
    }

    @Test(expected = ValidationException.class)
    public void findOneWithBirthdayHouseAndSpeciesNullId() throws Exception {
        animalService.findOneWithBirthdayHouseAndSpecies(null);
    }

    @Test
    public void save() throws Exception {
        try {
            Animal animalToSave = getValidAnimal();
            House house = new House();
            house.setId(NEW_HOUSE_ID);
            animalToSave.setHouse(house);
            Long houseCapacityBeforeSave = houseService.getHouseCurrentCapacity(NEW_HOUSE_ID);
            Animal actualAnimal = animalService.save(animalToSave);
            assertByPrimaryFields(animalToSave, actualAnimal);
            Long houseCapacityAfterSave = houseService.getHouseCurrentCapacity(NEW_HOUSE_ID);
            Assert.assertEquals(houseCapacityBeforeSave + existentAnimalAnimalsPerHouse, houseCapacityAfterSave.longValue());
        } finally {
            houseService.decreaseHouseCapacity(NEW_HOUSE_ID, existentAnimalAnimalsPerHouse);
            Assert.assertEquals(existentHouseCapacity, houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID));
        }
    }

    @Test(expected = HouseException.class)
    public void saveFullHouse() throws Exception {
        animalService.save(getValidAnimal());
    }

    @Test(expected = NotFoundException.class)
    public void saveWrongHouse() throws Exception {
        Animal animalToSave = getValidAnimal();
        House house = new House();
        house.setId(NOT_EXISTENT_HOUSE_ID);
        animalToSave.setHouse(house);
        animalService.save(animalToSave);
    }

    @Test(expected = AnimalException.class)
    public void saveWrongBirthday() throws Exception {
        Animal animalToSave = getValidAnimal();
        animalToSave.setBirthday(LocalDate.MAX);
        animalService.save(animalToSave);
    }

    @Test(expected = ValidationException.class)
    public void saveNullEntity() throws Exception {
        animalService.save(null);
    }

    @Test
    public void update() throws Exception {
        Animal animal = getValidAnimal();
        animal.setNickname("Testy");
        Animal updatedAnimal = animalService.update(animal);
        assertByPrimaryFields(animal, updatedAnimal);
    }

    @Test(expected = HouseException.class)
    public void updateWrongHouse() throws Exception {
        Animal animal = getValidAnimal();
        House house = new House();
        house.setId(NOT_SUITABLE_HOUSE_ID);
        animal.setHouse(house);
        animalService.update(animal);
    }

    @Test
    public void updateHouseChanged() throws Exception {
        Long newHouseCapacity = houseService.getHouseCurrentCapacity(NEW_HOUSE_ID);
        try {
            Animal animal = getValidAnimal();
            House house = new House();
            house.setId(NEW_HOUSE_ID);
            animal.setHouse(house);
            animalService.update(animal);
            Long oldHouseCapacityAfterSave = houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID);
            Long newHouseCapacityAfterSave = houseService.getHouseCurrentCapacity(NEW_HOUSE_ID);
            Assert.assertEquals(existentHouseCapacity - existentAnimalAnimalsPerHouse, oldHouseCapacityAfterSave.longValue());
            Assert.assertEquals(newHouseCapacity + existentAnimalAnimalsPerHouse, newHouseCapacityAfterSave.longValue());
        } finally {
            houseService.increaseHouseCapacity(EXISTENT_HOUSE_ID, existentAnimalAnimalsPerHouse);
            houseService.decreaseHouseCapacity(NEW_HOUSE_ID, existentAnimalAnimalsPerHouse);
            Assert.assertEquals(existentHouseCapacity, houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID));
            Assert.assertEquals(newHouseCapacity, houseService.getHouseCurrentCapacity(NEW_HOUSE_ID));
        }
    }

    @Test(expected = AnimalException.class)
    public void updateSpeciesChanged() throws Exception {
        Animal animal = getValidAnimal();
        Species species = speciesService.findOne(VALID_SPECIES_ID);
        animal.setSpecies(species);
        animalService.update(animal);
    }

    @Test(expected = AnimalException.class)
    public void updateBirthdayChanged() throws Exception {
        Animal animal = getValidAnimal();
        animal.setBirthday(LocalDate.now());
        animalService.update(animal);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        try {
            Long houseCapacityBeforeDelete = houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID);
            animalService.delete(EXISTENT_ANIMAL_ID);
            Long houseCapacityAfterDelete = houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID);
            Assert.assertEquals(houseCapacityBeforeDelete - existentAnimalAnimalsPerHouse, houseCapacityAfterDelete.longValue());
            animalService.findOne(EXISTENT_ANIMAL_ID);
        } finally {
            houseService.increaseHouseCapacity(EXISTENT_HOUSE_ID, existentAnimalAnimalsPerHouse);
            Assert.assertEquals(existentHouseCapacity, houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID));
        }
    }

    @Test(expected = ValidationException.class)
    public void deleteNullId() throws Exception {
        animalService.delete(null);
    }

    private Animal getValidAnimal() {
        Animal animal = new Animal();
        animal.setId(EXISTENT_ANIMAL_ID);
        animal.setNickname("Locuroumee");
        Species species = new Species();
        species.setId(EXISTENT_SPECIES_ID);
        animal.setSpecies(species);
        House house = new House();
        house.setId(EXISTENT_HOUSE_ID);
        animal.setHouse(house);
        animal.setBirthday(LocalDate.of(2015, 5, 15));
        animal.setFoodConsumption(10);
        return animal;
    }

    private void assertByPrimaryFields(Animal expected, Animal actual) {
        assertEquals(expected.getNickname(), actual.getNickname());
        assertNotNull(actual.getSpecies());
        assertEquals(expected.getSpecies().getId(), actual.getSpecies().getId());
        assertNotNull(actual.getHouse());
        assertEquals(expected.getHouse().getId(), actual.getHouse().getId());
        assertEquals(expected.getBirthday(), actual.getBirthday());
        assertEquals(expected.getFoodConsumption(), actual.getFoodConsumption());
    }

}