package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Family;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.exception.PersistenceProviderException;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.SpeciesRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.species.SpeciesFindOneWithAnimalsPerHouseSpecification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author Serhii Alekseichenko
 */
@Transactional
public class SpeciesRepositoryTest extends AbstractRepositoryTest<Species> {

    private final static Long EXISTENT_SPECIES_ID = 159793L;
    private final static Long NONEXISTENT_SPECIES_ID = 1L;
    private final static Long EXISTENT_FAMILY_ID = 159789L;
    private final static Long SPECIES_AMOUNT = 18308L;
    private final static Long NEXT_SPECIES_ID = 1_000_000L;

    @Autowired
    private SpeciesRepository speciesRepository;


    @Test
    public void findOne() throws Exception {
        Species expected = getValidSpecies();
        Species actual = super.findOne(new GetByIdSpecification<>(Species.class, EXISTENT_SPECIES_ID), EXISTENT_SPECIES_ID);
        assertByPrimaryFields(expected, actual);
    }

    @Test
    public void findOneWithAnimalsPerHouse() throws Exception {
        Species actual = super.findOne(new SpeciesFindOneWithAnimalsPerHouseSpecification(EXISTENT_SPECIES_ID), EXISTENT_SPECIES_ID);
        assertNotNull(actual.getAnimalsPerHouse());
        assertNull(actual.getCommonName());
        assertNull(actual.getScientificName());
        assertNull(actual.getFamily());
        assertNull(actual.getTemperatureMin());
        assertNull(actual.getTemperatureMax());
    }

    @Test(expected = SpecificationException.class)
    public void findOneWithNullId() throws Exception {
        super.findOne(new GetByIdSpecification<>(Species.class, null), null);
    }

    @Test
    public void findOneWithWrongId() throws Exception {
        Species actual = speciesRepository.findOne(new GetByIdSpecification<>(getType(), NONEXISTENT_SPECIES_ID));
        assertNull(actual);
    }

    @Test
    public void count() throws Exception {
        Long actualAmount = speciesRepository.count();
        assertEquals(SPECIES_AMOUNT, actualAmount);
    }

    @Test
    public void save() throws Exception {
        Species expected = getValidSpecies();
        Species actual = super.save(expected, NEXT_SPECIES_ID);
        assertByPrimaryFields(expected, actual);
    }

    @Test(expected = PersistenceProviderException.class)
    public void saveWithNullFields() throws Exception {
        Species expected = getValidSpecies();
        expected.setScientificName(null);
        expected.setFamily(null);
        expected.setAnimalsPerHouse(null);
        expected.setTemperatureMin(null);
        expected.setTemperatureMax(null);
        super.save(expected, NEXT_SPECIES_ID);
    }

    @Test(expected = ValidationException.class)
    public void saveNullSpecies() throws Exception {
        speciesRepository.save(null);
    }

    @Test
    public void delete() throws Exception {
        super.delete(EXISTENT_SPECIES_ID);
    }

    @Test
    public void deleteWithWrongId() throws Exception {
        assertFalse(speciesRepository.delete(NONEXISTENT_SPECIES_ID, getType()));
    }

    @Test(expected = ValidationException.class)
    public void deleteWithNullId() throws Exception {
        super.delete(null);
    }

    @Test
    public void update() throws Exception {
        Species expected = findOne(new GetByIdSpecification<>(getType(), EXISTENT_SPECIES_ID), EXISTENT_SPECIES_ID);
        expected.setTemperatureMin(0);
        expected.setTemperatureMax(42);
        Species actual = super.update(expected);
        assertByPrimaryFields(expected, actual);
    }

    @Test(expected = PersistenceProviderException.class)
    public void updateWithNullFields() throws Exception {
        Species expected = findOne(new GetByIdSpecification<>(getType(), EXISTENT_SPECIES_ID), EXISTENT_SPECIES_ID);
        expected.setScientificName(null);
        expected.setTemperatureMin(null);
        expected.setTemperatureMax(null);
        expected.setFamily(null);
        super.update(expected);
    }

    @Override
    protected Repository<Species> getRepository() {
        return speciesRepository;
    }

    @Override
    protected Class<Species> getType() {
        return Species.class;
    }

    private Species getValidSpecies() {
        Species species = new Species();
        species.setId(EXISTENT_SPECIES_ID);
        species.setScientificName("Heterodontus galeatus");
        species.setCommonName("crested bullhead shark");
        Family family = new Family();
        family.setId(EXISTENT_FAMILY_ID);
        species.setFamily(family);
        species.setAnimalsPerHouse(7);
        species.setTemperatureMin(7);
        species.setTemperatureMax(33);
        return species;
    }

    private void assertByPrimaryFields(Species expected, Species actual) {
        assertEquals(expected.getScientificName(), actual.getScientificName());
        assertEquals(expected.getCommonName(), actual.getCommonName());
        assertNotNull(actual.getFamily());
        assertEquals(expected.getFamily().getId(), actual.getFamily().getId());
        assertEquals(expected.getAnimalsPerHouse(), actual.getAnimalsPerHouse());
        assertEquals(expected.getTemperatureMin(), actual.getTemperatureMin());
        assertEquals(expected.getTemperatureMax(), actual.getTemperatureMax());
    }
}