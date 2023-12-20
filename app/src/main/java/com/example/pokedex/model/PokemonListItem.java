package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class PokemonListItem {
    @Json(name = "name")
    private String name;

    @Json(name = "url")
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}