package com.example.pokedex.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private List<Pokemon> pokemons;

    public PokemonAdapter(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent,
                false);
        return new PokemonViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        holder.displayPokemon(pokemons.get(position));
    }


    @Override
    public int
    getItemCount() {
        return pokemons.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pokemon_name);
        }

        public void displayPokemon(Pokemon pokemon) {

            name.setText(pokemon.getName());
        }

    }
}