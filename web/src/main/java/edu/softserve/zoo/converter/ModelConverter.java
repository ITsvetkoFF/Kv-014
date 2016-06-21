package edu.softserve.zoo.converter;

import edu.softserve.zoo.converter.mapping.DtoMapper;
import edu.softserve.zoo.dto.BaseDto;
import edu.softserve.zoo.exception.ModelConverterException;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.BaseEntity;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Simple DTO<-to->Entity converter</p>
 * <p>
 * <p>Just use one of the appropriate static methods to convert {@link BaseEntity} inheritor to {@link BaseDto}
 * corresponding class</p>
 *
 * @author Serhii Alekseichenko
 */
@Component
public class ModelConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelConverter.class);
    private static final String ERROR_LOG_TEMPLATE = "An exception occurred during {} operation. Message: {}";

    @Autowired
    private DtoMapper mapper;

    @Autowired
    private List<MappingStrategy> strategies;


    /**
     * <p>Returns true if and only if the specified class is a proxy class.</p>
     *
     * @param type class to test
     * @return {@code true} if the class is a proxy class and
     * {@code false} otherwise
     */
    static boolean isProxy(Class type) {
        return type.getSimpleName().contains("$$");
    }


    /**
     * <p>Return the corresponding Dto class for this Entity.</p>
     *
     * @param entity Entity to be used to return corresponding dto
     * @return instantiated dto
     */
    <Entity extends BaseEntity, Dto extends BaseDto> Dto getDto(Entity entity) {
        Class<Entity> entityClass = getEntityClass(entity);
        try {
            return (Dto) (mapper.getDtoClass(entityClass)).newInstance();
        } catch (NullPointerException | ReflectiveOperationException e) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "get Dto", e.getMessage());
            throw ApplicationException.getBuilderFor(ModelConverterException.class).forReason(ModelConverterException.Reason.MAPPING_TO_DTO_FAILED).causedBy(e).build();
        }
    }


    /**
     * <p>Return the corresponding Entity class for this DTO.</p>
     *
     * @param dto DTO to be used to return corresponding entity
     * @return instantiated entity
     */
    private <Entity extends BaseEntity, Dto extends BaseDto> Entity getEntity(Dto dto) {
        try {
            return (Entity) mapper.getEntityClass(dto.getClass()).newInstance();
        } catch (NullPointerException | ReflectiveOperationException e) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "get Entity", e.getMessage());
            throw ApplicationException.getBuilderFor(ModelConverterException.class).forReason(ModelConverterException.Reason.MAPPING_TO_ENTITY_FAILED).causedBy(e).build();
        }
    }

    /**
     * <p>Return stream of a dto converted from entities collection.</p>
     *
     * @param collection of entities
     * @return stream of a dto
     */
    <Entity extends BaseEntity, Dto extends BaseDto> Stream<Dto> convertToDtoCollection(Collection<Entity> collection) {
        return collection.stream().map(this::convertToDto);
    }


    /**
     * <p>Return stream of a entities converted from dto collection.</p>
     *
     * @param collection of dto
     * @return stream of a entities
     */
    <Entity extends BaseEntity, Dto extends BaseDto> Stream<Entity> convertToEntitiesCollection(Collection<Dto> collection) {
        return collection.stream().map(this::convertToEntity);
    }


    /**
     * <p>Return entity class depending of entity is a proxy or not.</p>
     *
     * @param entity of entities
     * @return entity class
     */
    private <Entity extends BaseEntity> Class<Entity> getEntityClass(Entity entity) {
        if (isProxy(entity.getClass())) {
            return (Class<Entity>) entity.getClass().getSuperclass();
        } else {
            return (Class<Entity>) entity.getClass();
        }
    }


    /**
     * <p>Return {@link BaseDto} inheritor after mapping from Entity object</p>
     * *
     *
     * @param entity {@link BaseEntity} inheritor that will be converted to dto
     * @return dto object
     */
    public <Entity extends BaseEntity, Dto extends BaseDto> Dto convertToDto(Entity entity) {
        Dto dto = getDto(entity);
        dto.setId(entity.getId());

        Arrays.stream(dto.getClass().getDeclaredFields()).forEach(field -> {
                    try {
                        Object fieldProperty = PropertyUtils.getProperty(entity, field.getName());
                        if (fieldProperty == null) return;
                        MappingStrategy mappingStrategy = strategies.stream()
                                .filter(strategy -> strategy.isApplicable(fieldProperty))
                                .findFirst()
                                .orElseThrow((Supplier<RuntimeException>) () ->
                                        ApplicationException.getBuilderFor(ModelConverterException.class).forReason(ModelConverterException.Reason.MAPPING_STRATEGY_NOT_FOUND).build());
                        Object result = mappingStrategy.convertToDto(fieldProperty);
                        PropertyUtils.setProperty(dto, field.getName(), result);
                    } catch (ReflectiveOperationException e) {
                        LOGGER.debug(ERROR_LOG_TEMPLATE, "convert to DTO", e.getMessage());
                        throw ApplicationException.getBuilderFor(ModelConverterException.class).forReason(ModelConverterException.Reason.MAPPING_TO_DTO_FAILED).build();
                    }
                }
        );
        return dto;

    }


    /**
     * <p>Return {@link BaseEntity} inheritor after mapping from Data Transfer Objects</p>
     * *
     *
     * @param dto {@link BaseDto} inheritor that will be converted to entity
     * @return entity object
     */
    public <Entity extends BaseEntity, Dto extends BaseDto> Entity convertToEntity(Dto dto) {
        Entity entity = getEntity(dto);
        entity.setId(dto.getId());

        Arrays.stream(dto.getClass().getDeclaredFields()).forEach(field -> {
            try {
                Object fieldProperty = PropertyUtils.getProperty(dto, field.getName());
                if (fieldProperty == null) return;
                MappingStrategy mappingStrategy = strategies.stream()
                        .filter(strategy -> strategy.isApplicable(fieldProperty)).findFirst()
                        .orElseThrow((Supplier<RuntimeException>) () ->
                                ApplicationException.getBuilderFor(ModelConverterException.class).forReason(ModelConverterException.Reason.MAPPING_STRATEGY_NOT_FOUND).build());
                Object result = mappingStrategy.convertToEntity(fieldProperty);
                PropertyUtils.setProperty(entity, field.getName(), result);
            } catch (ReflectiveOperationException e) {
                LOGGER.debug(ERROR_LOG_TEMPLATE, "convert to Entity", e.getMessage());
                throw ApplicationException.getBuilderFor(ModelConverterException.class).forReason(ModelConverterException.Reason.MAPPING_TO_ENTITY_FAILED).build();
            }
        });
        return entity;
    }


    /**
     * <p>Return list of a dto converted from entities list.</p>
     *
     * @param entities list of entities
     * @return list of dto
     */
    public <Entity extends BaseEntity, Dto extends BaseDto> List<Dto> convertToDto(List<Entity> entities) {
        return entities.stream().map(this::convertToDto).map(object -> ((Dto) object)).collect(Collectors.toList());
    }
}
