package com.example.pokedex.ui.controllers;

import static com.example.pokedex.utils.Utils.initToolbar;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.pokedex.databinding.ActivityMainBinding;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.ui.adapter.PokemonAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<Pokemon> pokemons;
    private PokemonAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initToolbar(this, binding.toolbar.getRoot());
        initRecyclerview();
    }

    private void initRecyclerview() {

        pokemons = new ArrayList<>(Arrays.asList(
                new Pokemon("Bulbasaur", 1),
                new Pokemon("Bulbasaur", 2),
                new Pokemon("Bulbasaur", 3),
                new Pokemon("Bulbasaur", 4),
                new Pokemon("Bulbasaur", 5),
                new Pokemon("Bulbasaur", 6),
                new Pokemon("Bulbasaur", 7),
                new Pokemon("Bulbasaur", 8),
                new Pokemon("Bulbasaur", 9),
                new Pokemon("Bulbasaur", 10),
                new Pokemon("Bulbasaur", 11),
                new Pokemon("Bulbasaur", 12),
                new Pokemon("Bulbasaur", 13),
                new Pokemon("Bulbasaur", 14),
                new Pokemon("Bulbasaur", 15),
                new Pokemon("Bulbasaur", 16),
                new Pokemon("Bulbasaur", 17),
                new Pokemon("Bulbasaur", 18),
                new Pokemon("Bulbasaur", 19),
                new Pokemon("Bulbasaur", 20)

        )

        );

        adapter = new PokemonAdapter(pokemons);
        binding.recyclerView.setAdapter(adapter);
    }
}