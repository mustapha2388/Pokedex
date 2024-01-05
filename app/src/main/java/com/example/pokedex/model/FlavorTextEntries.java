package com.example.pokedex.model;

import com.squareup.moshi.Json;

import java.util.List;

public class FlavorTextEntries {

    @Json(name = "flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }
}
