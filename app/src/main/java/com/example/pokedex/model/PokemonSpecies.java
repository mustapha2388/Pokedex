package com.example.pokedex.model;

import com.squareup.moshi.Json;

import java.util.List;

public class PokemonSpecies {


    @Json(name = "flavor_text_entries")
    private List<FlavorTextEntries> flavorTextEntries;

    public List<FlavorTextEntries> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntries> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }
}
