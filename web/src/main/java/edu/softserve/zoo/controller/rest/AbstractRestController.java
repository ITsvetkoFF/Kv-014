package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.BaseDto;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.service.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractRestController<D extends BaseDto, E extends BaseEntity> {

    protected static final String API_V1 = "api/v1";

    @Autowired
    private ModelMapper modelMapper;
    private Class<E> entityType;
    private Class<D> dtoType;

    public AbstractRestController(Class<E> entityType, Class<D> dtoType) {
        this.entityType = entityType;
        this.dtoType = dtoType;
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException exception) {
        return ResponseEntity.status(exception.getReason().getCode()).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<D> create(@RequestBody D dto) {
        E entity = convertToEntity(dto);
        return ResponseEntity.ok(convertToDto(getService().save(entity)));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<D> update(@RequestBody D dto, @PathVariable Integer id) {
        dto.setId(id);
        E entity = convertToEntity(dto);
        return ResponseEntity.ok(convertToDto(getService().update(entity)));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Integer id) {
        getService().deleteById(id);
        return ResponseEntity.ok().build();
    }

    protected abstract Service<E> getService();

    D convertToDto(E entity) {
        return modelMapper.map(entity, dtoType);
    }

    E convertToEntity(D dto) {
        return modelMapper.map(dto, entityType);
    }
}
