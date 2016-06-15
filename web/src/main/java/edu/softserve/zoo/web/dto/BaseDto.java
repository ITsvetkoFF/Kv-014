package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.web.annotation.DocsFieldDescription;

public abstract class BaseDto {

    @DocsFieldDescription("The id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}