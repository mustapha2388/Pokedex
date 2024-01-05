package com.example.pokedex.model;

import com.squareup.moshi.Json;


public class FlavorTextEntry {

    @Json(name = "flavor_text")
    private String flavorTexts;

    @Json(name = "language")
    private Language language;

    public String getFlavorTexts() {
        return flavorTexts;
    }

    public void setFlavorTexts(String flavorTexts) {
        this.flavorTexts = flavorTexts;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
