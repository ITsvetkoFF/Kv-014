import edu.softserve.zoo.dto.BaseDto;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static org.junit.Assert.assertTrue;

/**
 * Utility-Tester for matching DTOs (Data Transfer Objects) with their DOs (Domain Objects).
 * Criteria for success includes such factors:
 * <ul>
 * <li>Matching number of fields: DTO has to have not more fields declared than the DO does.</li>
 * <li>Matching naming of fields: DTO has to have same field naming as the DO does.</li>
 * </ul>
 * <p>
 * Test created for using mapping models by convention.
 * </p>
 * This util stands not only for testing matching DTOs with their DOs,
 * it also provides information about declared fields in tested Classes.
 *
 * @author Andrii Abramov on 13-May-16.
 */
public final class DtoValidationTest {

    /**
     * Base package to scan for DTO.
     * Pattern for DTO Class should match "XXXDto" where XXX = DTO's DO name.
     */
    private static final String DTO_BASE_PACKAGE = "edu.softserve.zoo.dto";
    /**
     * Base package to scan for DO.
     */
    private static final String ENTITY_BASE_PACKAGE = "edu.softserve.zoo.model";
    /**
     * Stores the classes which potentially belong to DTO's base package but shouldn't be tested.
     */
    private static final List<Class<? extends BaseDto>> EXCLUDED_DTOS = Arrays.asList(BaseDto.class);

    /**
     * Stores pairs DTO against its DO.
     */
    private static Map<Class<?>, Class<?>> dtoToEntity;

    /**
     * Initializes the mapping between DTOs and their DOs.
     * @throws IOException if any path is broken
     * @throws URISyntaxException if any path is broken
     */
    @BeforeClass
    public static void setUp() throws IOException, URISyntaxException {
        dtoToEntity = extractDtos(DTO_BASE_PACKAGE)
                .filter(e -> !EXCLUDED_DTOS.contains(e))
                .collect(Collectors.toMap(
                        Function.identity(),
                        entity -> getEntityForDto(ENTITY_BASE_PACKAGE, entity)
                ));

        dtoToEntity.keySet()
                .forEach(className -> System.out.println("Dto classes found to test: " + className.getSimpleName()));
        dtoToEntity
                .forEach((d, e) -> System.out.println("For " + d.getSimpleName() + " found " + e.getSimpleName())
        );

    }

    /**
     * Fetches all DTOs that match the specified pattern of Class name.
     * @param basePackage package where DTOs are stored
     * @return Stream of Class Objects initialized by convention if file names in basePackage
     * @throws URISyntaxException
     * @throws IOException
     */
    private static Stream<? extends Class<?>> extractDtos(String basePackage) throws URISyntaxException, IOException {

        final Path baseDtoPath = Paths.get(ClassLoader.getSystemResource(basePackage.replace('.', '/')).toURI());

        final String dtoPattern = "[\\w]+Dto\\.class";
        final String classNamePattern = "\\.class";

        return Files.list(baseDtoPath)
                .map(Path::toFile).map(File::getName)
                .filter(s -> s.matches(dtoPattern))
                .map(s -> s.split(classNamePattern)[0])
                .map(className -> loadClass(DTO_BASE_PACKAGE + '.' + className));

    }

    /**
     * The proxy class for loading Classes by name
     * Handles {@link ClassNotFoundException}
     *
     * @param name full name of Class to load
     * @return Initialized class
     * @throws RuntimeException if no Class with such name was found.
     */
    private static Class<?> loadClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads Domain Object for specified DTO Class in basePackage.
     *
     * @param entityBasePackage package where DO are stored
     * @param dto               Class for with the DO will ve loaded
     * @return loaded DO for specified DTO
     */
    private static Class<?> getEntityForDto(String entityBasePackage, Class<?> dto) {
        return loadClass(entityBasePackage + '.' + dto.getSimpleName().replace("Dto", ""));
    }

    /**
     * Matching number of fields: DTO shouldn't have more fields declared than the DO does.
     */
    @Test
    public void testNumberOfFields() {
        dtoToEntity.entrySet().stream()
                .forEach(entry -> {
                    final Class<?> dto = entry.getKey();
                    final Class<?> entity = entry.getValue();
                    final Field[] dtoFields = dto.getDeclaredFields();
                    final Field[] entityFields = entity.getDeclaredFields();

                    assertTrue(dto + " has more fields than " + entity, dtoFields.length <= entityFields.length);
                });

    }

    /**
     * Matching naming of fields: DTO has to have same field naming as the DO does.
     * <p>
     * Naming convention is having the same property name.
     */
    @Test
    public void testNameEquality() {
        dtoToEntity.entrySet().stream()
                .forEach(entry -> {

                    final Class<?> dto = entry.getKey();
                    final Class<?> entity = entry.getValue();

                    final Set<String> entityFieldNames = stream(entity.getDeclaredFields())
                            .map(Field::getName)
                            .collect(Collectors.toSet());

                    System.out.printf("Fields found in %s: %s%n", dto.getSimpleName(), entityFieldNames);

                    Arrays.stream(dto.getDeclaredFields())
                            .map(Field::getName)
                            .forEach(dtoFieldName ->
                                    assertTrue(String.format("%s has no such field '%s' as %s does.",
                                            entity.getSimpleName(), dtoFieldName, dto.getSimpleName()),
                                            entityFieldNames.contains(dtoFieldName)));

                });


    }

}