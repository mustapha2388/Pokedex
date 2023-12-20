package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class Other {


    @Json(name = "official-artwork")
    private OfficialArtwork officialArtwork;

    public OfficialArtwork getOfficialArtwork() {
        return officialArtwork;
    }

    public void setOfficialArtwork(OfficialArtwork officialArtwork) {
        this.officialArtwork = officialArtwork;
    }
}
