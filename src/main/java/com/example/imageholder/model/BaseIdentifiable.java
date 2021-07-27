package com.example.imageholder.model;

import java.io.Serializable;

public interface BaseIdentifiable extends Serializable {

    Long getId();

    String getUuid();
}
