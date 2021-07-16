package com.example.imageholder.model;

import java.io.Serializable;

public interface BaseIdentifiable extends Serializable {

    long getId();

    String getUuid();
}
