package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * The entity class with additional name property
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {
    /**
     * The name property of the entity
     */
    @Column(nullable = false)
    private String name;

    /**
     * Constructor without input argument
     */
    public NamedEntity() {}

    /**
     * Constructor expected id and name input
     * @param id expected entity id
     * @param name expected name
     */
    public NamedEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * @return the entity name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name expected name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return return the name of the entity
     */
    @Override
    public String toString() {
        return getName();
    }
}
