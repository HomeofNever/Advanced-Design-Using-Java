package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Basic entity class which implements Serializable
 */
@MappedSuperclass
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class BaseEntity implements Serializable {
    /**
     * The entity id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**
     * Constructor without input argument
     */
    public BaseEntity() {}

    /**
     * Constructor expected id input
     * @param id expected entity id
     */
    public BaseEntity(Long id) {
        this.id = id;
    }

    /**
     * @return the id of the entity
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id to the expected one
     * @param id expected id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return true if the id equals null (has not been set)
     */
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
}
