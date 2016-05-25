package edu.softserve.zoo;

import edu.softserve.zoo.annotation.Dto;
import edu.softserve.zoo.annotation.IrrespectiveDto;
import edu.softserve.zoo.dto.BaseDto;
import edu.softserve.zoo.model.BaseEntity;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Consumer;

import static java.lang.String.format;

/**
 * Class that manages relationships between Entities and their DTO.
 */
public class DtoMapper {

    private final static Logger LOGGER = LoggerFactory.getLogger(DtoMapper.class);

    private final BidiMap<Class<? extends BaseEntity>, Class<? extends BaseDto>> mappings = new DualHashBidiMap<>();

    private String dtoPackage;

    public void setDtoPackage(String dtoPackage) {
        this.dtoPackage = dtoPackage;
    }

    /**
     * @param forClass dtoClass
     * @return Entity class for which was DTO created.
     */
    public Class<? extends BaseEntity> getEntityClass(Class<?> forClass) {
        return mappings.getKey(forClass);
    }

    /**
     * @param forClass entityClass
     * @return DTO class for which specified Entity class was associated.
     */
    public Class<? extends BaseDto> getDtoClass(Class<?> forClass) {
        return mappings.get(forClass);
    }

    /**
     * @return mapping Entities against their DTO
     */
    public BidiMap<Class<? extends BaseEntity>, Class<? extends BaseDto>> getMappings() {
        return this.mappings;
    }


    /**
     * Programmatically associates Entity Class with their Data Transfer Object
     */
    @PostConstruct
    @SuppressWarnings("unchecked")
    private void initMappings() {

        // Includes all classes found in specified package
        final TypeFilter ALL_FILTER = (metadata, metadataReader) -> true;

        // false - to turn off basic include filter (e.g. @Component, @Service, etc.)
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(ALL_FILTER);

        final Set<BeanDefinition> foundClasses = scanner.findCandidateComponents(dtoPackage);
        foundClasses.stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::loadDtoClass)
                .peek(this::validate)
                .forEach(dtoClass -> {
                            tryWithAnnotation(Dto.class, dtoClass, (annotation) -> {
                                final Class<? extends BaseEntity> entityClass = (Class<? extends BaseEntity>) AnnotationUtils.getValue(annotation);
                                LOGGER.info(format("For `%s` found `%s`", entityClass.getSimpleName(), dtoClass.getSimpleName()));
                                mappings.put(entityClass, dtoClass);
                            });

                            tryWithAnnotation(IrrespectiveDto.class, dtoClass, (annotation) -> {
                                LOGGER.info(format("Found irrespective dto: `%s`", dtoClass.getSimpleName()));
                            });
                        }
                );
    }

    /**
     * Checks if every Class in {@link #dtoPackage} is annotated.
     *
     * @param dtoClass
     */
    private void validate(Class<? extends BaseDto> dtoClass) {
        final Dto dtoAnnotation = AnnotationUtils.getAnnotation(dtoClass, Dto.class);
        final IrrespectiveDto irrDtoAnnotation = AnnotationUtils.getAnnotation(dtoClass, IrrespectiveDto.class);
        if (dtoAnnotation == null && irrDtoAnnotation == null) {
            String message = "Dto %s is not mapped to Entity (@Dto) or as irrespective (@IrrespectiveDto). " +
                    "All entities in dto package should have that.";
            throw new IllegalStateException(format(message, dtoClass));
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends BaseDto> loadDtoClass(String className) {
        try {
            return (Class<? extends BaseDto>) ClassUtils.forName(className, null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error while loading class: " + className, e);
        }
    }

    private void tryWithAnnotation(Class<? extends Annotation> annClass, Class<? extends BaseDto> dtoClass, Consumer<Annotation> closure) {
        final Annotation annotation = AnnotationUtils.getAnnotation(dtoClass, annClass);
        if (annotation != null) {
            closure.accept(annotation);
        }
    }


}
