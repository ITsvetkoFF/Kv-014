import edu.softserve.zoo.DtoMapper;
import edu.softserve.zoo.dto.*;
import edu.softserve.zoo.model.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Utility-Tester for matching DTOs (Data Transfer Objects) with their DOs (Domain Objects).
 * Criteria for success includes such factors:
 * <ul>
 * <li>Matching number of fields: DTO has to have not more fields declared than the DO does.</li>
 * <li>Matching naming of fields: DTO has to have same field naming as the DO does.</li>
 * <li>Matching types of fields: DTO has to have same field type <strong>OR</strong> DTO for that type
 * as the DO does.</li>
 * </ul>
 * <p>
 * <p>
 * This util stands not only for testing matching DTOs with their DOs,
 * it also provides information about declared fields in tested Classes.
 * </p>
 *
 * @author Andrii Abramov on 23-May-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/web-test-ctx.xml")
public class AntiVandalDtoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AntiVandalDtoTest.class);

    @Autowired
    private DtoMapper dtoMapper;

    @Test
    public void testIt() {

        final String countMismatch = "Number of field in %s dto should not exceed the number %s entity does.%n";
        final String curClass = "Working around: `%s` class:";
        final String curField = "\tField: `%s` %s";
        final String reqField = "\t\tRequiring field type from mapping: `%s`";
        final String noMapping = "No mapping found for `%s`%n";
        final String typeError = "Type mismatch for `%s`%n";

        LOGGER.info("Validating dto classes...");

        dtoMapper.getMappings().forEach((entity, dto) -> {

            LOGGER.info(format(curClass, dto.getSimpleName()));

            final List<Field> dtoFields = FieldUtils.getAllFieldsList(dto);
            final Map<String, Field> entityFields = getNamesAgainstFields(entity);

            assertTrue(format(countMismatch, dto.getName(), entity.getName()), dtoFields.size() <= entityFields.size());

            dtoFields.forEach(dtoField -> {

                final Class<?> dtoFieldType = dtoField.getType();

                assertTrue(format(noMapping, dtoField), entityFields.containsKey(dtoField.getName()));

                final Field entityField = entityFields.get(dtoField.getName());
                final boolean typeMatches = entityField.getType().equals(dtoFieldType);

                LOGGER.info(format(curField, dtoField.getName(), typeMatches ? " matches." : " found type mismatch:"));
                if (!typeMatches) {
                    handleTypeMismatch(dtoField, entityField);
                    final Class<? extends BaseEntity> mappedType = dtoMapper.getEntityClass(dtoFieldType);
                    LOGGER.info(format(reqField, mappedType.getName()));
                    assertEquals(format(typeError, dtoField), entityField.getType(), mappedType);
                }

            });

        });


    }

    // TODO should I write this dummy test #testDtosMapping, testEntitiesMapping
    @Test
    public void testDtosMapping() {

        final Map<Class<? extends BaseDto>, Class<? extends BaseEntity>> testCases = new HashMap<>();
        testCases.put(AnimalDto.class, Animal.class);
        testCases.put(AnimalClassDto.class, AnimalClass.class);
        testCases.put(EmployeeDto.class, Employee.class);
        testCases.put(FamilyDto.class, Family.class);
        testCases.put(GeographicalZoneDto.class, GeographicalZone.class);
        testCases.put(HouseDto.class, House.class);
        testCases.put(RoleDto.class, Role.class);
        testCases.put(SpeciesDto.class, Species.class);
        testCases.put(TaskDto.class, Task.class);
        testCases.put(WarehouseDto.class, Warehouse.class);
        testCases.put(ZooZoneDto.class, ZooZone.class);

        testCases.forEach((expected, entity) -> {
            assertEquals(expected, dtoMapper.getDtoClass(entity));
        });
    }

    @Test
    public void testEntitiesMapping() {

        final Map<Class<? extends BaseEntity>, Class<? extends BaseDto>> testCases = new HashMap<>();
        testCases.put(Animal.class, AnimalDto.class);
        testCases.put(AnimalClass.class, AnimalClassDto.class);
        testCases.put(Employee.class, EmployeeDto.class);
        testCases.put(Family.class, FamilyDto.class);
        testCases.put(GeographicalZone.class, GeographicalZoneDto.class);
        testCases.put(House.class, HouseDto.class);
        testCases.put(Role.class, RoleDto.class);
        testCases.put(Species.class, SpeciesDto.class);
        testCases.put(Task.class, TaskDto.class);
        testCases.put(Warehouse.class, WarehouseDto.class);
        testCases.put(ZooZone.class, ZooZoneDto.class);

        testCases.forEach((expected, entity) -> {
            assertEquals(expected, dtoMapper.getEntityClass(entity));
        });
    }

    private void handleTypeMismatch(Field dtoField, Field entityField) {
        LOGGER.info(format("\t\t%-10s `%s`", "in dto:", dtoField.getType().getName()));
        LOGGER.info(format("\t\t%-10s `%s`", "in entity:", entityField.getType().getName()));
    }

    private Map<String, Field> getNamesAgainstFields(Class<?> clazz) {
        return FieldUtils.getAllFieldsList(clazz)
                .stream().collect(Collectors.toMap(Field::getName, Function.identity()));
    }
}
