package com.example.imageholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.SEQUENCE;

@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler" })
@MappedSuperclass
@EqualsAndHashCode(exclude = { "uuid" })
public abstract class BaseEntity implements BaseIdentifiable {

    public static final int ALLOCATION_SIZE = 1;
    public static final String ID_FIELD_NAME = "id";

    @Id
    @GeneratedValue(generator = "sequence", strategy = SEQUENCE)
    private long id;

    private final String uuid;

    @Override
    public long getId() {
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
