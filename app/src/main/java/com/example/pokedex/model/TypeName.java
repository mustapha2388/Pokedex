package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class TypeName {

    @Json(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
