package com.example.imageholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.SEQUENCE;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@MappedSuperclass
@EqualsAndHashCode(exclude = {"uuid"})
public abstract class BaseEntity implements BaseIdentifiable {

    public static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(generator = "sequence", strategy = SEQUENCE)
    private Long id;

    private final String uuid;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public BaseEntity() {
        this.uuid = randomUUID().toString();
    }
}
