package com.example.pokedex.repository;

import static com.example.pokedex.utils.Utils.extractAllIdsFromList;
import static com.example.pokedex.utils.Utils.getPokemonArrayListSorted;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.pokedex.model.FlavorTextEntries;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonListItem;
import com.example.pokedex.model.PokemonListResponse;
import com.example.pokedex.services.PokeApiClient;
import com.example.pokedex.services.PokeApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonRepository {
    private final MutableLiveData<ArrayList<Integer>> muLivDataIdsPokemonList;
    private final MutableLiveData<Pokemon> muLivDataPokemon;
    private final MutableLiveData<ArrayList<Pokemon>> muLivDataPokemonArrListSortedById;
    private final MutableLiveData<ArrayList<Pokemon>> muLivDataAllPokemonsFilterBySearch;
    private final MutableLiveData<String> muLivDataPrevious;
    private final MutableLiveData<String> muLivDataNext;
    private final MutableLiveData<Integer> muLivDataOffset;

    private final MutableLiveData<FlavorTextEntries> muLivDataDescription;

    public PokemonRepository() {
        this.muLivDataIdsPokemonList = new MutableLiveData<>();
        this.muLivDataPokemon = new MutableLiveData<>();
        this.muLivDataPokemonArrListSortedById = new MutableLiveData<>();
        this.muLivDataPrevious = new MutableLiveData<>();
        this.muLivDataNext = new MutableLiveData<>();
        this.muLivDataOffset = new MutableLiveData<>();
        this.muLivDataAllPokemonsFilterBySearch = new MutableLiveData<>();
        this.muLivDataDescription = new MutableLiveData<>();

    }

    public MutableLiveData<Integer> getLiveDataOffset() {
        return muLivDataOffset;
    }

    public MutableLiveData<ArrayList<Integer>> getLiveDataIdsPokemonList() {
        return muLivDataIdsPokemonList;
    }

    public MutableLiveData<FlavorTextEntries> getLiveDataPokemonDescription() {
        return muLivDataDescription;
    }

    public MutableLiveData<String> getLiveDataPrevious() {
        return muLivDataPrevious;
    }

    public MutableLiveData<String> getLiveDataNext() {
        return muLivDataNext;
    }

    public MutableLiveData<Pokemon> getLiveDataPokemon() {
        return muLivDataPokemon;
    }


    public MutableLiveData<ArrayList<Pokemon>> getLiveDataPokemonArrayListSortedById() {
        return muLivDataPokemonArrListSortedById;
    }

    public MutableLiveData<ArrayList<Pokemon>> getLiveDataAllPokemonsForSearchByNameOrId() {
        return muLivDataAllPokemonsFilterBySearch;
    }

    public void getIdsPokemonList(int offset, int limit) {

        PokeApiService apiService = PokeApiClient.getRetrofitInstance().create(PokeApiService.class);

        Call<PokemonListResponse> call = apiService.getPokemonList(offset, limit);
        ArrayList<Integer> ids = new ArrayList<>();

        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonListResponse> call, @NonNull Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    PokemonListResponse pokemonListResponse = response.body();
                    if (pokemonListResponse != null) {


                        List<PokemonListItem> pokemonList = pokemonListResponse.getResults();
                        ids.addAll(extractAllIdsFromList(pokemonList));
                        muLivDataIdsPokemonList.postValue(ids);
                        muLivDataPrevious.postValue(pokemonListResponse.getPrevious());
                        muLivDataNext.postValue(pokemonListResponse.getNext());
                    }
                } else {
                    // Handle errors
                    Log.e("Pokemons", "Handle errors");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonListResponse> call, @NonNull Throwable t) {
                // Handle failures
                Log.e("Pokemons", "onFailure errors");
            }
        });
    }


    public void getPokemonById(int id) {
        PokeApiService apiService = PokeApiClient.getRetrofitInstance().create(PokeApiService.class);

        Call<Pokemon> call = apiService.getPokemonById(id);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon pokemon = response.body();
                    if (pokemon != null) {
                        muLivDataPokemon.setValue(pokemon);
                    }
                } else {
                    // Handle errors
                    Log.e("Pokemons", "Handle errors");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {
                // Handle failures
                Log.e("Pokemons", "onFailure errors");
            }
        });

    }

    public void getAllPokemonFromPageSortedById(ArrayList<Integer> ids) {

        PokeApiService apiService = PokeApiClient.getRetrofitInstance().create(PokeApiService.class);
        ArrayList<Pokemon> pokemons = new ArrayList<>();

        for (Integer id : ids) {
            Call<Pokemon> call = apiService.getPokemonById(id);


            call.enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                    if (response.isSuccessful()) {
                        Pokemon pokemon = response.body();

                        if (pokemon != null) {
                            pokemons.add(pokemon);

                            if (pokemons.size() == ids.size()) {

                                ArrayList<Pokemon> pokemonArrayListSorted = getPokemonArrayListSorted(pokemons);
                                muLivDataPokemonArrListSortedById.postValue(pokemonArrayListSorted);
                            }
                        }
                    } else {
                        // Handle errors
                        Log.e("Pokemons", "Handle errors");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {
                    // Handle failures
                    Log.e("Pokemons", "onFailure errors");
                }
            });

        }
    }


    public void getPokemonDescriptionById(int id) {
        PokeApiService apiService = PokeApiClient.getRetrofitInstance().create(PokeApiService.class);

        Call<FlavorTextEntries> call = apiService.getPokemonDescription(id);

        call.enqueue(new Callback<FlavorTextEntries>() {
            @Override
            public void onResponse(@NonNull Call<FlavorTextEntries> call, @NonNull Response<FlavorTextEntries> response) {
                if (response.isSuccessful()) {
                    FlavorTextEntries flavorTextEntries = response.body();
                    if (flavorTextEntries != null) {
                        muLivDataDescription.setValue(flavorTextEntries);
                    }
                } else {
                    // Handle errors
                    Log.e("Pokemons", "Handle errors");
                }
            }

            @Override
            public void onFailure(@NonNull Call<FlavorTextEntries> call, @NonNull Throwable t) {
                // Handle failures
                Log.e("Pokemons", "onFailure errors");
            }
        });
    }

    public void updateOffset(int value) {
        muLivDataOffset.postValue(value);
    }


    public void getPokemonByName(String name) {
        PokeApiService apiService = PokeApiClient.getRetrofitInstance().create(PokeApiService.class);

        Call<PokemonListResponse> call = apiService.getAllPokemons(0, 1302);

        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonListResponse> call, @NonNull Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    PokemonListResponse pokemonListResponse = response.body();

                    if (pokemonListResponse != null) {

                        ArrayList<PokemonListItem> pokemonsMatched = new ArrayList<>();
                        List<PokemonListItem> poke = pokemonListResponse.getResults();

                        for (PokemonListItem pokemonListItem : poke) {
                            if (pokemonListItem.getName().toLowerCase().contains(name.toLowerCase())) {
                                pokemonsMatched.add(pokemonListItem);
                            }
                        }

                        getAllPokemonsByNameSearch(pokemonsMatched);
                    }
                } else {
                    // Handle errors
                    Log.e("Pokemons", "Handle errors");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonListResponse> call, @NonNull Throwable t) {
                // Handle failures
                Log.e("Pokemons", "onFailure errors");
            }
        });
    }


    public void getAllPokemonsByNameSearch(List<PokemonListItem> pokemonListItems) {
        PokeApiService apiService = PokeApiClient.getRetrofitInstance().create(PokeApiService.class);

        ArrayList<Pokemon> pokemonsSearched = new ArrayList<>();

        if (pokemonListItems.size() == 0) {
            muLivDataAllPokemonsFilterBySearch.postValue(new ArrayList<>());
        } else {
            for (PokemonListItem pokemonListItem : pokemonListItems) {
                String name = pokemonListItem.getName();

                Call<Pokemon> call = apiService.getPokemonByName(name);

                call.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                        if (response.isSuccessful()) {
                            Pokemon pokemon = response.body();
                            if (pokemon != null) {
                                pokemonsSearched.add(pokemon);

                                if (pokemonsSearched.size() == pokemonListItems.size()) {
                                    ArrayList<Pokemon> getAllPokemonsByNameSearchSorted = getPokemonArrayListSorted(pokemonsSearched);
//                                    pokemonsSearched = getPokemonArrayListSorted(pokemonsSearched);
                                    muLivDataAllPokemonsFilterBySearch.postValue(getAllPokemonsByNameSearchSorted);
                                }
                            }
                        } else {
                            // Handle errors
                            Log.e("Pokemons", "Handle errors");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {
                        // Handle failures
                        Log.e("Pokemons", "onFailure errors");
                    }
                });
            }
        }
    }

    public void getPokemonsById(int id) {
        PokeApiService apiService = PokeApiClient.getRetrofitInstance().create(PokeApiService.class);

        Call<Pokemon> call = apiService.getPokemonById(id);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon pokemon = response.body();

                    if (pokemon != null) {
                        ArrayList<Pokemon> pokemons = new ArrayList<>(Collections.singletonList(pokemon));
                        muLivDataAllPokemonsFilterBySearch.postValue(pokemons);
                    }
                } else {
                    // Handle errors
                    Log.e("Pokemons", "Handle errors");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {
                // Handle failures
                Log.e("Pokemons", "onFailure errors");
            }
        });
    }
}
