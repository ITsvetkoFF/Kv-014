package edu.softserve.zoo.dto;

import edu.softserve.zoo.model.BaseEntity;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * Class that manages relationships between Entities and their DTO.
 */
public class DtoMapper {

    private final static Logger LOGGER = LoggerFactory.getLogger(DtoMapper.class);

    private Resource location;
    private String entityPackage;
    private String dtoPackage;

    private BidiMap<Class<? extends BaseEntity>, Class<? extends BaseDto>> mappings = new DualHashBidiMap<>();

    /**
     * @param entityPackage base package that contains Entities (prefix of Classes)
     */
    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    /**
     * @param dtoPackage base package that contains DTOs (prefix of Classes)
     */
    public void setDtoPackage(String dtoPackage) {
        this.dtoPackage = dtoPackage;
    }

    /**
     * @param location path to properties file thah contains Entity - DTO mappings
     */
    public void setLocation(Resource location) {
        this.location = location;
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
    private void initMappings() {
        final Properties properties = loadProperties();
        properties.forEach((entityName, dtoName) -> {
            mappings.put(loadEntityClass((String) entityName), loadDtoClass((String) dtoName));
            LOGGER.info("Mapping: {} <=> {}", entityName, dtoName);
        });
    }

    private Properties loadProperties() {
        final Properties properties = new Properties();
        try {
            properties.load(location.getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException("Error while reading properties from " + location.getFilename(), e);
        }
        return properties;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends BaseEntity> loadEntityClass(String className) {
        try {
            final String fullClassName = entityPackage + '.' + className;
            return (Class<? extends BaseEntity>) ClassUtils.forName(fullClassName, null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error while loading class: " + className, e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends BaseDto> loadDtoClass(String className) {
        try {
            final String fullClassName = dtoPackage + '.' + className;
            return (Class<? extends BaseDto>) ClassUtils.forName(fullClassName, null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error while loading class: " + className, e);
        }
    }


}
