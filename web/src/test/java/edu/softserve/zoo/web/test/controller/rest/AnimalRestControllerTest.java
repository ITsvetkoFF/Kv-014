package edu.softserve.zoo.web.test.controller.rest;

import edu.softserve.zoo.controller.rest.AnimalRestController;
import edu.softserve.zoo.dto.AnimalDto;
import edu.softserve.zoo.dto.HouseDto;
import edu.softserve.zoo.dto.SpeciesDto;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.service.exception.AnimalException;
import edu.softserve.zoo.util.AppProfiles;
import edu.softserve.zoo.web.test.config.WebTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Serhii Alekseichenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebTestConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@WebAppConfiguration
@Transactional
public class AnimalRestControllerTest {

    private static final Long EXISTENT_ANIMAL_ID = 1L;
    private static final Long EXISTENT_HOUSE_ID = 1L;
    private static final Long EMPTY_HOUSE_ID = 13L;
    private static final Long EXISTENT_SPECIES_ID = 161130L;
    private static final Long NONEXISTENT_SPECIES_ID = 1L;
    private static final Long NONEXISTENT_ANIMAL_ID = 100L;
    private static final Long NONEXISTENT_HOUSE_ID = 100L;
    private static final Long NEXT_ANIMAL_ID = 18L;
    private static final int ANIMALS_AMOUNT = 17;
    private static final String VERY_LONG_NICKNAME = "Looooooooooooooooooooooooooooooooooooooong nickname";


    @Autowired
    private AnimalRestController controller;

    @Test
    public void getAll() throws Exception {
        List<AnimalDto> all = controller.getAll();
        Assert.assertEquals(ANIMALS_AMOUNT, all.size());
    }

    @Test
    public void testGetById() throws Exception {
        AnimalDto actual = controller.getById(EXISTENT_ANIMAL_ID);
        Assert.assertEquals(getValidAnimalDto(), actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetByWrongId() throws Exception {
        controller.getById(NONEXISTENT_ANIMAL_ID);
    }

    @Test(expected = SpecificationException.class)
    public void testGetByNullId() throws Exception {
        controller.getById(null);
    }

    @Test
    public void create() throws Exception {
        AnimalDto validAnimalDto = getValidAnimalDto();
        HouseDto houseDto = new HouseDto();
        houseDto.setId(EMPTY_HOUSE_ID);
        validAnimalDto.setHouse(houseDto);
        AnimalDto animalDto = controller.create(validAnimalDto);
        Assert.assertEquals(NEXT_ANIMAL_ID, animalDto.getId());
        Assert.assertEquals(validAnimalDto, animalDto);
    }

    @Test(expected = AnimalException.class)
    public void createFullHouse() throws Exception {
        AnimalDto validAnimalDto = getValidAnimalDto();
        controller.create(validAnimalDto);
    }

    @Test(expected = AnimalException.class)
    public void createLongNickname() throws Exception {
        AnimalDto validAnimalDto = getValidAnimalDto();
        validAnimalDto.setNickname(VERY_LONG_NICKNAME);
        controller.create(validAnimalDto);
    }

    @Test(expected = AnimalException.class)
    public void createWrongBirthday() throws Exception {
        AnimalDto validAnimalDto = getValidAnimalDto();
        validAnimalDto.setBirthday(LocalDate.MAX);
        controller.create(validAnimalDto);
    }


    @Test
    public void update() throws Exception {
        AnimalDto animalDto = getValidAnimalDto();
        animalDto.setNickname("Testy");
        AnimalDto updatedAnimalDto = controller.update(animalDto, EXISTENT_ANIMAL_ID);
        Assert.assertEquals(animalDto, updatedAnimalDto);
    }

    @Test
    public void delete() throws Exception {
        ResponseEntity delete = controller.delete(EXISTENT_ANIMAL_ID);
        Assert.assertTrue(delete.getStatusCode().is2xxSuccessful());
    }

    @Test(expected = AnimalException.class)
    public void deleteWrongId() throws Exception {
        controller.delete(NONEXISTENT_ANIMAL_ID);
    }

    @Test(expected = AnimalException.class)
    public void deleteNull() throws Exception {
        controller.delete(null);
    }

    @Test
    public void getAllByHouseId() throws Exception {
        List<AnimalDto> allByHouseId = controller.getAllByHouseId(EXISTENT_HOUSE_ID);
        Assert.assertEquals(4, allByHouseId.size());
        allByHouseId.forEach(animalDto -> Assert.assertEquals(animalDto.getHouse().getId(), EXISTENT_HOUSE_ID));
    }

    @Test
    public void getAllByHouseWrongId() throws Exception {
        List<AnimalDto> allByHouseId = controller.getAllByHouseId(NONEXISTENT_HOUSE_ID);
        Assert.assertTrue(allByHouseId.isEmpty());
    }

    @Test(expected = SpecificationException.class)
    public void getAllByHouseNullId() throws Exception {
        controller.getAllByHouseId(null);
    }

    @Test
    public void getAllBySpeciesId() throws Exception {
        List<AnimalDto> allBySpeciesId = controller.getAllBySpeciesId(EXISTENT_SPECIES_ID);
        Assert.assertEquals(1, allBySpeciesId.size());
        allBySpeciesId.forEach(animalDto -> Assert.assertEquals(animalDto.getSpecies().getId(), EXISTENT_SPECIES_ID));
    }

    @Test
    public void getAllBySpeciesWrongId() throws Exception {
        List<AnimalDto> allBySpeciesId = controller.getAllBySpeciesId(NONEXISTENT_SPECIES_ID);
        Assert.assertTrue(allBySpeciesId.isEmpty());
    }

    @Test(expected = SpecificationException.class)
    public void getAllBySpeciesNullId() throws Exception {
        controller.getAllBySpeciesId(null);
    }

    @Test
    public void count() throws Exception {
        Long count = controller.count();
        Assert.assertEquals(ANIMALS_AMOUNT, count.intValue());
    }


    private AnimalDto getValidAnimalDto() {
        AnimalDto animalDto = new AnimalDto();
        animalDto.setId(EXISTENT_ANIMAL_ID);
        animalDto.setNickname("Locuroumee");
        SpeciesDto speciesDto = new SpeciesDto();
        speciesDto.setId(EXISTENT_SPECIES_ID);
        animalDto.setSpecies(speciesDto);
        HouseDto houseDto = new HouseDto();
        houseDto.setId(EXISTENT_HOUSE_ID);
        animalDto.setHouse(houseDto);
        animalDto.setBirthday(LocalDate.of(2015, 5, 15));
        animalDto.setFoodConsumption(10);
        return animalDto;
    }

}