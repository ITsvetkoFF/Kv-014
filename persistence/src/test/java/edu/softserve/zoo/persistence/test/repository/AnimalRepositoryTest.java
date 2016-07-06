package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.exception.PersistenceProviderException;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.repository.AnimalRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalFindOneWithBirthdayHouseAndSpeciesSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalGetAllByHouseIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.animal.AnimalGetAllBySpeciesIdSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * @author Serhii Alekseichenko
 */
@Transactional
public class AnimalRepositoryTest extends AbstractRepositoryTest<Animal> {

    private static final Long EXISTENT_ANIMAL_ID = 1L;
    private static final Long EXISTENT_HOUSE_ID = 1L;
    private static final Long EXISTENT_SPECIES_ID = 161130L;
    private static final Long NONEXISTENT_ANIMAL_ID = 100L;
    private static final Long ANIMALS_AMOUNT = 17L;
    private static final Long NEXT_ANIMAL_ID = 18L;

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void findOne() throws Exception {
        Animal expectedAnimal = getValidAnimal();
        Animal actualAnimal = super.findOne(new GetByIdSpecification<>(Animal.class, EXISTENT_ANIMAL_ID), EXISTENT_ANIMAL_ID);
        assertByPrimaryFields(expectedAnimal, actualAnimal);
    }

    @Test
    public void findOneWithBirthdayHouseAndSpecies() throws Exception {
        Animal actualAnimal = super.findOne(new AnimalFindOneWithBirthdayHouseAndSpeciesSpecification(EXISTENT_ANIMAL_ID), EXISTENT_ANIMAL_ID);
        Assert.assertNotNull(actualAnimal.getBirthday());
        Assert.assertNotNull(actualAnimal.getHouse());
        Assert.assertNotNull(actualAnimal.getSpecies());
        Assert.assertNull(actualAnimal.getNickname());
        Assert.assertNull(actualAnimal.getFoodConsumption());
    }

    @Test(expected = SpecificationException.class)
    public void findOneWithNullId() throws Exception {
        super.findOne(new GetByIdSpecification<>(Animal.class, null), null);
    }

    @Test
    public void findOneWithWrongId() throws Exception {
        Animal actual = animalRepository.findOne(new GetByIdSpecification<>(getType(), NONEXISTENT_ANIMAL_ID));
        assertNull(actual);
    }

    @Test
    public void count() throws Exception {
        Long actualAmount = animalRepository.count();
        assertEquals(ANIMALS_AMOUNT, actualAmount);
    }

    @Test
    public void save() throws Exception {
        Animal expectedAnimal = getValidAnimal();
        Animal actualAnimal = super.save(expectedAnimal, NEXT_ANIMAL_ID);
        assertByPrimaryFields(expectedAnimal, actualAnimal);
    }

    @Test(expected = PersistenceProviderException.class)
    public void saveWithNullFields() throws Exception {
        Animal expectedAnimal = getValidAnimal();
        expectedAnimal.setFoodConsumption(null);
        expectedAnimal.setHouse(null);
        expectedAnimal.setSpecies(null);
        super.save(expectedAnimal, NEXT_ANIMAL_ID);
    }

    @Test(expected = ValidationException.class)
    public void saveNullAnimal() throws Exception {
        animalRepository.save(null);
    }

    @Test
    public void delete() throws Exception {
        super.delete(EXISTENT_ANIMAL_ID);
    }

    @Test
    public void deleteWithWrongId() throws Exception {
        assertFalse(animalRepository.delete(NONEXISTENT_ANIMAL_ID, getType()));
    }

    @Test(expected = ValidationException.class)
    public void deleteWithNullId() throws Exception {
        super.delete(null);
    }

    @Test
    public void update() throws Exception {
        Animal expectedAnimal = findOne(new GetByIdSpecification<>(getType(), EXISTENT_ANIMAL_ID), EXISTENT_ANIMAL_ID);
        expectedAnimal.setNickname("Testy");
        expectedAnimal.setFoodConsumption(42);
        Animal actualAnimal = super.update(expectedAnimal);
        assertByPrimaryFields(expectedAnimal, actualAnimal);
    }

    @Test(expected = PersistenceProviderException.class)
    public void updateWithNullFields() throws Exception {
        Animal expectedAnimal = findOne(new GetByIdSpecification<>(getType(), EXISTENT_ANIMAL_ID), EXISTENT_ANIMAL_ID);
        expectedAnimal.setFoodConsumption(null);
        expectedAnimal.setHouse(null);
        expectedAnimal.setSpecies(null);
        super.update(expectedAnimal);
    }


    @Test
    public void findBySpeciesId() throws Exception {
        super.find(new AnimalGetAllBySpeciesIdSpecification(EXISTENT_SPECIES_ID), 1);
    }

    @Test
    public void findByHouseIdId() throws Exception {
        super.find(new AnimalGetAllByHouseIdSpecification(EXISTENT_HOUSE_ID), 4);
    }

    @Test(expected = ValidationException.class)
    public void findWithNullSpecification() throws Exception {
        animalRepository.find(null);
    }

    @Override
    protected Repository<Animal> getRepository() {
        return animalRepository;
    }

    @Override
    protected Class<Animal> getType() {
        return Animal.class;
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