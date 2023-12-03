package com.example.pokedex.ui.adapter;

import static android.content.ContentValues.TAG;
import static com.example.pokedex.utils.Utils.startActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.ui.controllers.DetailActivity;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private final ArrayList<Pokemon> pokemons;

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

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pokemon_name);
            itemView.setOnClickListener(v -> {
                int index = getAbsoluteAdapterPosition();
                Context context = v.getContext();
                String name = pokemons.get(index).getName();
                startActivity(context, DetailActivity.class);
            });

        }

        public void displayPokemon(Pokemon pokemon) {

            name.setText(pokemon.getName());
            name.setOnClickListener(view -> Log.i(TAG, "displayPokemon " + name));
        }

    }
}