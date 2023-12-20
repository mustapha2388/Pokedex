package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class OfficialArtwork {

    @Json(name = "front_default")
    private String frontDefault;

    @Json(name = "front_shiny")
    private String frontShiny;

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }
}
