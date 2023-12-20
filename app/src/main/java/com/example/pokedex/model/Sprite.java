package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class Sprite {
    @Json(name = "other")
    private Other others;

    public Other getOthers() {
        return others;
    }

    public void setOthers(Other others) {
        this.others = others;
    }
}
