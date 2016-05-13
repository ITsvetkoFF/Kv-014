package edu.softserve.zoo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import static com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS;

public class HibernateAwareObjectMapper extends ObjectMapper {
    public HibernateAwareObjectMapper() {
        Hibernate5Module module = new Hibernate5Module();
        module.enable(SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        registerModule(module);
    }
}