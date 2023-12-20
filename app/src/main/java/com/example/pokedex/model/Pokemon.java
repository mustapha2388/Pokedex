package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class Pokemon {
    @Json(name = "name")
    private String name;

    @Json(name = "id")
    private int id;

    @Json(name = "sprites")
    private Sprite sprites;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sprite getSprites() {
        return sprites;
    }
}
