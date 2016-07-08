package edu.softserve.zoo.service.test;

import edu.softserve.zoo.exceptions.NotFoundException;
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

import static org.junit.Assert.*;

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
    private static final Long NOT_EXISTENT_ANIMAL_ID = 100L;
    private static final Long NOT_EXISTENT_SPECIES_ID = 1L;
    private static final Long EXISTENT_SPECIES_ID = 161130L;
    private static final Long VALID_SPECIES_ID = 159793L;
    private static final int ANIMALS_IN_EXISTENT_HOUSE = 4;
    private static final String VERY_LONG_NICKNAME = "Looooooooooooooooooooooooooooooooooooooong nickname";
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
        assertEquals(ANIMALS_IN_EXISTENT_HOUSE, allByHouseId.size());
        allByHouseId.forEach(animal -> assertEquals(EXISTENT_HOUSE_ID, animal.getHouse().getId()));
    }

    @Test
    public void getAllByHouseWrongId() throws Exception {
        List<Animal> allByHouseId = animalService.getAllByHouseId(NOT_EXISTENT_HOUSE_ID);
        assertTrue(allByHouseId.isEmpty());
    }

    @Test
    public void getAllByHouseIdNullId() throws Exception {
        try {
            animalService.getAllByHouseId(null);
            fail();
        } catch (SpecificationException ex) {
            assertEquals(ex.getReason(), SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION);
        }
    }

    @Test
    public void getAllBySpeciesId() throws Exception {
        List<Animal> allBySpeciesId = animalService.getAllBySpeciesId(EXISTENT_SPECIES_ID);
        assertEquals(1, allBySpeciesId.size());
        allBySpeciesId.forEach(animal -> assertEquals(EXISTENT_SPECIES_ID, animal.getSpecies().getId()));
    }

    @Test
    public void getAllBySpeciesWrongId() throws Exception {
        List<Animal> allBySpeciesId = animalService.getAllBySpeciesId(NOT_EXISTENT_SPECIES_ID);
        assertTrue(allBySpeciesId.isEmpty());
    }

    @Test
    public void getAllBySpeciesIdNullId() throws Exception {
        try {
            animalService.getAllBySpeciesId(null);
            fail();
        } catch (SpecificationException ex) {
            assertEquals(ex.getReason(), SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION);
        }
    }

    @Test
    public void findOneWithBirthdayHouseAndSpecies() throws Exception {
        Animal actualAnimal = animalService.findOneWithBirthdayHouseAndSpecies(EXISTENT_ANIMAL_ID);
        assertNotNull(actualAnimal.getBirthday());
        assertNotNull(actualAnimal.getHouse());
        assertNotNull(actualAnimal.getSpecies());
        assertNull(actualAnimal.getNickname());
        assertNull(actualAnimal.getFoodConsumption());
    }

    @Test
    public void findOneWithBirthdayHouseAndSpeciesNullId() throws Exception {
        try {
            animalService.findOneWithBirthdayHouseAndSpecies(null);
            fail();
        } catch (SpecificationException ex) {
            assertEquals(ex.getReason(), SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION);
        }
    }

    @Test
    public void findOneWithBirthdayHouseAndSpeciesWrongId() throws Exception {
        Animal animal = animalService.findOneWithBirthdayHouseAndSpecies(NOT_EXISTENT_ANIMAL_ID);
        assertNull(animal);
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
            assertEquals(houseCapacityBeforeSave + existentAnimalAnimalsPerHouse, houseCapacityAfterSave.longValue());
        } finally {
            houseService.decreaseHouseCapacity(NEW_HOUSE_ID, existentAnimalAnimalsPerHouse);
            assertEquals(existentHouseCapacity, houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID));
        }
    }

    @Test
    public void saveFullHouse() throws Exception {
        try {
            animalService.save(getValidAnimal());
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.SAVE_FAILED);
            assertEquals(ex.getQualificationReason(), HouseException.Reason.HOUSE_IS_FULL);
        }
    }

    @Test
    public void saveWrongHouse() throws Exception {
        try {
            Animal animalToSave = getValidAnimal();
            House house = new House();
            house.setId(NOT_EXISTENT_HOUSE_ID);
            animalToSave.setHouse(house);
            animalService.save(animalToSave);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.SAVE_FAILED);
            assertEquals(ex.getQualificationReason(), NotFoundException.Reason.BY_ID);
        }

    }

    @Test
    public void saveWrongSpecies() throws Exception {
        try {
            Animal animalToSave = getValidAnimal();
            Species species = new Species();
            species.setId(NOT_EXISTENT_SPECIES_ID);
            animalToSave.setSpecies(species);
            animalService.save(animalToSave);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.SAVE_FAILED);
            assertEquals(ex.getQualificationReason(), AnimalException.Reason.WRONG_SPECIES);
        }

    }

    @Test
    public void saveWrongBirthday() throws Exception {
        try {
            Animal animalToSave = getValidAnimal();
            animalToSave.setBirthday(LocalDate.MAX);
            animalService.save(animalToSave);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.SAVE_FAILED);
        }
    }

    @Test
    public void saveWrongFoodConsumption() throws Exception {
        try {
            Animal animalToSave = getValidAnimal();
            animalToSave.setFoodConsumption(0);
            animalService.save(animalToSave);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.SAVE_FAILED);
        }
    }

    @Test
    public void saveLongNickname() throws Exception {
        try {
            Animal animalToSave = getValidAnimal();
            animalToSave.setNickname(VERY_LONG_NICKNAME);
            animalService.save(animalToSave);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.SAVE_FAILED);
        }
    }

    @Test
    public void update() throws Exception {
        Animal animal = getValidAnimal();
        animal.setNickname("Testy");
        Animal updatedAnimal = animalService.update(animal);
        assertByPrimaryFields(animal, updatedAnimal);
    }

    @Test
    public void updateWrongHouse() throws Exception {
        try {
            Animal animal = getValidAnimal();
            House house = new House();
            house.setId(NOT_SUITABLE_HOUSE_ID);
            animal.setHouse(house);
            animalService.update(animal);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.UPDATE_FAILED);
            assertEquals(ex.getQualificationReason(), HouseException.Reason.WRONG_HOUSE);
        }
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
            assertEquals(existentHouseCapacity - existentAnimalAnimalsPerHouse, oldHouseCapacityAfterSave.longValue());
            assertEquals(newHouseCapacity + existentAnimalAnimalsPerHouse, newHouseCapacityAfterSave.longValue());
        } finally {
            houseService.increaseHouseCapacity(EXISTENT_HOUSE_ID, existentAnimalAnimalsPerHouse);
            houseService.decreaseHouseCapacity(NEW_HOUSE_ID, existentAnimalAnimalsPerHouse);
            assertEquals(existentHouseCapacity, houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID));
            assertEquals(newHouseCapacity, houseService.getHouseCurrentCapacity(NEW_HOUSE_ID));
        }
    }

    @Test
    public void updateSpeciesChanged() throws Exception {
        try {
            Animal animal = getValidAnimal();
            Species species = speciesService.findOne(VALID_SPECIES_ID);
            animal.setSpecies(species);
            animalService.update(animal);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.UPDATE_FAILED);
            assertEquals(ex.getQualificationReason(), AnimalException.Reason.SPECIES_CHANGED);
        }
    }

    @Test
    public void updateBirthdayChanged() throws Exception {
        try {
            Animal animal = getValidAnimal();
            animal.setBirthday(LocalDate.now());
            animalService.update(animal);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.UPDATE_FAILED);
            assertEquals(ex.getQualificationReason(), (AnimalException.Reason.BIRTHDAY_CHANGED));
        }
    }

    @Test
    public void delete() throws Exception {
        try {
            Long houseCapacityBeforeDelete = houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID);
            animalService.delete(EXISTENT_ANIMAL_ID);
            Long houseCapacityAfterDelete = houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID);
            assertEquals(houseCapacityBeforeDelete - existentAnimalAnimalsPerHouse, houseCapacityAfterDelete.longValue());
            animalService.findOne(EXISTENT_ANIMAL_ID);
            fail();
        } catch (NotFoundException ex) {
            assertEquals(ex.getReason(), NotFoundException.Reason.BY_ID);
        } finally {
            houseService.increaseHouseCapacity(EXISTENT_HOUSE_ID, existentAnimalAnimalsPerHouse);
            assertEquals(existentHouseCapacity, houseService.getHouseCurrentCapacity(EXISTENT_HOUSE_ID));
        }
    }

    @Test
    public void deleteNullId() throws Exception {
        try {
            animalService.delete(null);
            fail();
        } catch (AnimalException ex) {
            assertEquals(ex.getReason(), AnimalException.Reason.DELETE_FAILED);
        }
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