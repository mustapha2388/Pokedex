package com.example.pokedex.model;

import com.squareup.moshi.Json;

import java.util.List;

public class PokemonListResponse {

    @Json(name = "next")
    private String next;

    @Json(name = "previous")
    private String previous;

    @Json(name = "results")
    private List<PokemonListItem> results;

    @Json(name = "count")
    private int count;

    public List<PokemonListItem> getResults() {
        return results;
    }

    public String getNext() {
        return next;
    }


    public String getPrevious() {
        return previous;
    }

    public int getCount() {
        return count;
    }
}
