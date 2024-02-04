package com.example.pokedex.ui.fragments;

import static com.example.pokedex.utils.Utils.getColorOfType;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pokedex.R;
import com.example.pokedex.databinding.FragmentBodyPokemonDetailBinding;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.Stat;
import com.example.pokedex.model.Type;

import java.util.ArrayList;
import java.util.Arrays;

public class BodyPokemonDetailFragment extends Fragment {


    public FragmentBodyPokemonDetailBinding binding;
    public static Pokemon pokemon;

    public static BodyPokemonDetailFragment newInstance(Pokemon pokemon) {
        BodyPokemonDetailFragment fragment = new BodyPokemonDetailFragment();
        BodyPokemonDetailFragment.pokemon = pokemon;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBodyPokemonDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindData();
    }

    private void bindData() {

        ArrayList<TextView> pokemonTypes = new ArrayList<>(Arrays.asList(binding.pokemonType1, binding.pokemonType2));
        int cpt = 0;
        for (Type type : pokemon.getTypes()) {
            String typeStr = type.getTypeName().getName();
            setPokemonType(pokemonTypes.get(cpt), typeStr);
            cpt += 1;
        }

        //TODO GET DESCRIPTION

        setMeasurementValue(pokemon.getWeight(), binding.weightValue, R.string.kg);
        setMeasurementValue(pokemon.getHeight(), binding.heightValue, R.string.m);

        if (pokemon.getAbilities().size() == 1) {
            setAbilityValue(binding.abilityValue1, pokemon.getAbilities().get(0).getAbilityName().getName());
        } else if (pokemon.getAbilities().size() > 1) {
            setAbilityValue(binding.abilityValue1, pokemon.getAbilities().get(0).getAbilityName().getName());
            setAbilityValue(binding.abilityValue2, pokemon.getAbilities().get(1).getAbilityName().getName());
        }

        setDescription();

        setStats();
    }

    private void setStats() {
        String type_1 = pokemon.getTypes().get(0).getTypeName().getName();
        int color = getColorOfType(type_1);

        setColorStat(binding.hpProgressBar, color);
        setColorStat(binding.atkProgressBar, color);
        setColorStat(binding.defProgressBar, color);
        setColorStat(binding.satkProgressBar, color);
        setColorStat(binding.sdefProgressBar, color);
        setColorStat(binding.spdProgressBar, color);


        for (Stat stat : pokemon.getStats()) {
            setStatCorrectly(stat);
        }

    }

    private void setStatCorrectly(Stat stat) {

        String name = stat.getValueStat().getName();
        int value = stat.getBaseStat();
        switch (name) {
            case "hp":
                binding.hpValue.setText(String.valueOf(value));
                binding.hpProgressBar.setProgress(value);
                break;
            case "attack":
                binding.atkValue.setText(String.valueOf(value));
                binding.atkProgressBar.setProgress(value);
                break;
            case "defense":
                binding.defValue.setText(String.valueOf(value));
                binding.defProgressBar.setProgress(value);
                break;
            case "special-attack":
                binding.satkValue.setText(String.valueOf(value));
                binding.satkProgressBar.setProgress(value);
                break;
            case "special-defense":
                binding.sdefValue.setText(String.valueOf(value));
                binding.sdefProgressBar.setProgress(value);
                break;
            default:
                binding.spdValue.setText(String.valueOf(value));
                binding.spdProgressBar.setProgress(value);
                break;

        }
    }

    private void setColorStat(ProgressBar progressBar, int color) {
        progressBar.setProgressTintList(ContextCompat.getColorStateList(requireContext(), color));
    }

    private void setDescription() {
        binding.pokemonDescription.setText(pokemon.getDescription());

    }


    private void setPokemonType(TextView typeTxt, String type) {
        if (!TextUtils.isEmpty(type)) {

            String uppercaseType = type.toUpperCase();
            int color = ContextCompat.getColor(requireContext(), getColorOfType(type));
            ColorStateList colorStateList = ColorStateList.valueOf(color);

            typeTxt.setVisibility(View.VISIBLE);
            typeTxt.setText(uppercaseType);
            typeTxt.setBackgroundTintList(colorStateList);
        }
    }


    private void setMeasurementValue(int value, TextView textView, int stringResource) {
        double formattedValue = formatHeightOrWeight(value);
        String unit = getResources().getString(stringResource);

        StringBuilder valueStrBuild = new StringBuilder()
                .append(" ")
                .append(formattedValue)
                .append(" ")
                .append(unit);

        textView.setText(valueStrBuild);
    }

    private double formatHeightOrWeight(int value) {
        return (double) value / 10;

    }

    private void setAbilityValue(TextView textView, String ability) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(ability);
    }
}