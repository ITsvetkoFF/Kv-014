package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsDescription;

public abstract class BaseDto {

    @DocsDescription("The id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}