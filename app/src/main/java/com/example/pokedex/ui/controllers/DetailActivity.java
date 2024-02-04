package com.example.pokedex.ui.controllers;

import static com.example.pokedex.utils.Utils.concatenateDescriptionsWithLineBreaks;
import static com.example.pokedex.utils.Utils.extractIdFromUrl;
import static com.example.pokedex.utils.Utils.extractUniqueDescriptionsFr;
import static com.example.pokedex.utils.Utils.getIdOfPokemon;
import static com.example.pokedex.utils.Utils.initToolbar;

import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.pokedex.R;
import com.example.pokedex.databinding.ActivityDetailBinding;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.ui.fragments.BodyPokemonDetailFragment;
import com.example.pokedex.ui.fragments.HeaderPokemonDetailFragment;
import com.example.pokedex.utils.Utils;
import com.example.pokedex.viewModel.PokemonViewModel;

import java.util.Locale;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    private PokemonViewModel pokemonViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initToolbar(DetailActivity.this, binding.toolbar.getRoot());
        initViewModel();


        int id = getIdOfPokemon(getIntent());
        getDataOfPokemon(id);

        setupOnBackPressed();
    }

    private void initViewModel() {

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
    }

    private void getDataOfPokemon(int id) {

        pokemonViewModel.getLiveDataPokemon().observe(this, pokemon -> {

            updateHeaderPokemonData(pokemon);


            int idExtracted = extractIdFromUrl(pokemon.getSpecie().getUrl());
            pokemonViewModel.getLiveDataPokemonDescription().observe(this, FlavorTextEntries -> {


                Set<String> filterUniqueDescriptionsFr = extractUniqueDescriptionsFr(FlavorTextEntries);

                String descriptions = concatenateDescriptionsWithLineBreaks(filterUniqueDescriptionsFr);
                pokemon.setDescription(descriptions);
                setupFragments(pokemon);
            });

            if (idExtracted > -1) {
                pokemonViewModel.getPokemonDescriptionById(idExtracted);
            }
        });

        pokemonViewModel.getPokemonById(id);
    }

    private void updateHeaderPokemonData(Pokemon pokemon) {

        String formattedId = String.format(Locale.FRANCE, "%03d", pokemon.getId());
        String sharpWithPokemonId = getString(R.string.sharp_with_pokemon_id, formattedId);
        binding.toolbar.pokemonNumber.setText(sharpWithPokemonId);
        binding.toolbar.pokemonNameTitle.setText(pokemon.getName().toUpperCase());


        String color = pokemon.getTypes().get(0).getTypeName().getName();
        int colorType = Utils.getColorOfType(color);

        binding.detailActivity.setBackgroundColor(getResources().getColor(colorType));
        binding.toolbar.getRoot().setBackgroundColor(getResources().getColor(colorType));

        int colorBackground = getResources().getColor(colorType);

        Drawable myDrawable = binding.toolbar.pokeballBackground.getDrawable();

        float alpha = 0.09f;

        int tintedColor = ColorUtils.blendARGB(colorBackground, Color.TRANSPARENT, alpha);
        DrawableCompat.setTint(myDrawable, tintedColor);

        binding.toolbar.pokeballBackground.setImageDrawable(myDrawable);
    }

    private void setupFragments(Pokemon pokemon) {
        Log.i(ContentValues.TAG, "pokemon: " + pokemon.toString());


        HeaderPokemonDetailFragment headerPokemonDetailFrag = (HeaderPokemonDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_header);

        if (headerPokemonDetailFrag == null) {
            headerPokemonDetailFrag = HeaderPokemonDetailFragment.newInstance(pokemon);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view_header, headerPokemonDetailFrag)
                    .commit();
        }

        BodyPokemonDetailFragment bodyPokemonDetailFrag = (BodyPokemonDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_body);

        if (bodyPokemonDetailFrag == null) {
            bodyPokemonDetailFrag = BodyPokemonDetailFragment.newInstance(pokemon);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view_body, bodyPokemonDetailFrag)
                    .commit();
        }

    }

    private void setupOnBackPressed() {
        // Set up a callback for the back press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom back press handling
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        };

        // Add the callback to the back press dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}