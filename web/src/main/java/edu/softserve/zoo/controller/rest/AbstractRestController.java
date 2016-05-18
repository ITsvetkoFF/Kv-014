package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.BaseDto;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.service.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vadym Holub
 */
public abstract class AbstractRestController<D extends BaseDto, E extends BaseEntity> {

    @Autowired
    private ModelMapper modelMapper;
    private Class<E> entityType;
    private Class<D> dtoType;

    protected AbstractRestController(Class<E> entityType, Class<D> dtoType) {
        this.entityType = entityType;
        this.dtoType = dtoType;
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException exception) {
        return ResponseEntity.status(exception.getReason().getCode()).build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public D getById(@PathVariable Long id) {
        return convertToDto(getService().findOne(id, entityType));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<D> getAll() {
        return convertToDto(getService().findAll(entityType));
    }

    @RequestMapping(method = RequestMethod.POST)
    public D create(@RequestBody D dto) {
        return convertToDto(getService().save(convertToEntity(dto)));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public D update(@RequestBody D dto, @PathVariable Long id) {
        dto.setId(id);
        return convertToDto(getService().update(convertToEntity(dto)));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        getService().delete(id, entityType);
        return ResponseEntity.ok().build();
    }

    protected abstract Service<E> getService();

    protected D convertToDto(E entity) {
        return modelMapper.map(entity, dtoType);
    }

    protected List<D> convertToDto(List<E> entities) {
       return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    protected E convertToEntity(D dto) {
        return modelMapper.map(dto, entityType);
    }

}