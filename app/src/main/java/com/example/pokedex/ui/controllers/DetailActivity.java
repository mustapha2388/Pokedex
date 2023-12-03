package com.example.pokedex.ui.controllers;

import static com.example.pokedex.utils.Utils.initToolbar;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.R;
import com.example.pokedex.databinding.ActivityDetailBinding;
import com.example.pokedex.ui.fragments.BodyPokemonDetailFragment;
import com.example.pokedex.ui.fragments.HeaderPokemonDetailFragment;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private HeaderPokemonDetailFragment mHeaderPokemonDetailFrag;
    private BodyPokemonDetailFragment mBodyPokemonDetailFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initToolbar(DetailActivity.this, binding.toolbar.getRoot());
        setupFragments();
    }

    private void setupFragments() {

        mHeaderPokemonDetailFrag = (HeaderPokemonDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_header);

        if (mHeaderPokemonDetailFrag == null) {
            mHeaderPokemonDetailFrag = HeaderPokemonDetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view_header, mHeaderPokemonDetailFrag)
                    .commit();
        }

        mBodyPokemonDetailFrag = (BodyPokemonDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_body);

        if (mBodyPokemonDetailFrag == null) {
            mBodyPokemonDetailFrag = BodyPokemonDetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view_body, mBodyPokemonDetailFrag)
                    .commit();
        }
    }


}