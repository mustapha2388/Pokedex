package com.example.pokedex.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.pokedex.R;
import com.example.pokedex.databinding.FragmentHeaderPokemonDetailBinding;
import com.example.pokedex.model.Pokemon;

public class HeaderPokemonDetailFragment extends Fragment {


    public FragmentHeaderPokemonDetailBinding binding;

    public static Pokemon pokemon;

    public static HeaderPokemonDetailFragment newInstance(Pokemon pokemon) {
        HeaderPokemonDetailFragment fragment = new HeaderPokemonDetailFragment();
        HeaderPokemonDetailFragment.pokemon = pokemon;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHeaderPokemonDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupListener();
        String urlImgDefault = pokemon.getSprite().getOther().getOfficialArtwork().getFrontDefault();

        bindData(urlImgDefault);
        animateImage(binding.pokemonImg);
    }

    private void setupListener() {
        String urlImgShiny = pokemon.getSprite().getOther().getOfficialArtwork().getFrontShiny();
        String urlImgDefault = pokemon.getSprite().getOther().getOfficialArtwork().getFrontDefault();
        binding.pokemonImg.setOnClickListener(view -> {

            String currentImageUrl = (String) binding.pokemonImg.getTag();
            String newImageUrl = (currentImageUrl != null && currentImageUrl.equals(urlImgShiny)) ? urlImgDefault : urlImgShiny;

            bindData(newImageUrl);
        });
    }

    private void bindData(String url) {

        Glide.with(this)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.shape_pokemon)
                        .error(R.drawable.unknow_pokemon))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(binding.pokemonImg);

        binding.pokemonImg.setTag(url);
    }


    private void animateImage(ImageView imageView) {
        Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in);
        imageView.startAnimation(animation);
    }

}