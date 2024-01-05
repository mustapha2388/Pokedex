package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class Ability {

    @Json(name = "ability")
    private AbilityName AbilityName;

    public com.example.pokedex.model.AbilityName getAbilityName() {
        return AbilityName;
    }

    public void setAbilityName(com.example.pokedex.model.AbilityName abilityName) {
        AbilityName = abilityName;
    }
}
