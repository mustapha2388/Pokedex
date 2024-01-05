package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class Stat {

    @Json(name = "base_stat")
    private int baseStat;

    @Json(name = "stat")
    private ValueStat valueStat;

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public ValueStat getValueStat() {
        return valueStat;
    }

    public void setValueStat(ValueStat valueStat) {
        this.valueStat = valueStat;
    }
}
