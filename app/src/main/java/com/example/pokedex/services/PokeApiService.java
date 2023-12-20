package com.example.pokedex.services;

import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon/{id}/")
    Call<Pokemon> getPokemonById(@Path("id") int id);


    @GET("pokemon/")
    Call<PokemonListResponse> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);
}

