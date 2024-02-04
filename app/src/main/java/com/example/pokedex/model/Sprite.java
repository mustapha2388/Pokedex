package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class Sprite {
    @Json(name = "other")
    private Other other;

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }
}
