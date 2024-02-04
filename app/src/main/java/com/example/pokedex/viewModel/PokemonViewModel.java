package com.example.pokedex.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pokedex.model.FlavorTextEntries;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.repository.PokemonRepository;

import java.util.ArrayList;

public class PokemonViewModel extends AndroidViewModel {

    private final PokemonRepository pokemonRepository;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        this.pokemonRepository = new PokemonRepository();
    }

    public void getIdsPokemonList(int offset, int limit) {
        pokemonRepository.getIdsPokemonList(offset, limit);

    }

    public void getPokemonById(int id) {
        pokemonRepository.getPokemonById(id);
    }

    public void getPokemonDescriptionById(int id) {
        pokemonRepository.getPokemonDescriptionById(id);
    }

    public void getAllPokemonFromPageSortedById(ArrayList<Integer> ids) {
        pokemonRepository.getAllPokemonFromPageSortedById(ids);
    }

    public LiveData<ArrayList<Integer>> getLiveDataIdsPokemonList() {
        return pokemonRepository.getLiveDataIdsPokemonList();
    }

    public LiveData<Pokemon> getLiveDataPokemon() {
        return pokemonRepository.getLiveDataPokemon();
    }

    public LiveData<ArrayList<Pokemon>> getLiveDataPokemonArrayListSortedById() {
        return pokemonRepository.getLiveDataPokemonArrayListSortedById();
    }

    public LiveData<ArrayList<Pokemon>> getLiveDataAllPokemonsForSearchByNameOrId() {
        return pokemonRepository.getLiveDataAllPokemonsForSearchByNameOrId();
    }

    public LiveData<String> getLiveDataPrevious() {
        return pokemonRepository.getLiveDataPrevious();
    }

    public LiveData<String> getLiveDataNext() {
        return pokemonRepository.getLiveDataNext();
    }

    public LiveData<Integer> getLiveDataOffset() {
        return pokemonRepository.getLiveDataOffset();
    }


    public LiveData<FlavorTextEntries> getLiveDataPokemonDescription() {
        return pokemonRepository.getLiveDataPokemonDescription();
    }

    public void updateOffset(int value) {
        pokemonRepository.updateOffset(value);
    }

    public void getPokemonByName(String name) {
        pokemonRepository.getPokemonByName(name);
    }

    public void getPokemonsById(int id) {
        pokemonRepository.getPokemonsById(id);
    }
}
