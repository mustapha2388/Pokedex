package com.example.pokedex.ui.adapter;

import static android.content.ContentValues.TAG;
import static com.example.pokedex.utils.Utils.getIdWith3DigitsFormat;
import static com.example.pokedex.utils.Utils.isNameTooLong;
import static com.example.pokedex.utils.Utils.shortcutStr;
import static com.example.pokedex.utils.Utils.startActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.pokedex.R;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.services.OnItemClickListener;
import com.example.pokedex.ui.controllers.DetailActivity;
import com.example.pokedex.ui.controllers.MainActivity;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private final ArrayList<Pokemon> pokemons;
    private final Context context;

    private final OnItemClickListener listener;

    public PokemonAdapter(ArrayList<Pokemon> pokemons, Context context, OnItemClickListener listener) {
        this.pokemons = pokemons;
        this.context = context;
        this.listener = listener;
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
        TextView number;

        ImageView image;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pokemon_name);
            number = itemView.findViewById(R.id.pokemon_number);
            image = itemView.findViewById(R.id.pokemon_shape);

            itemView.setOnClickListener(v -> {
                int index = getAbsoluteAdapterPosition();
                Context context = v.getContext();
                int id = pokemons.get(index).getId();


                if (context instanceof MainActivity) {

                    listener.onItemClick();
                    startActivity(context, DetailActivity.class, id);
                }

            });

        }

        public void displayPokemon(Pokemon pokemon) {

            String pokemonName = pokemon.getName();
            if (isNameTooLong(pokemon.getName())) {
                pokemonName = shortcutStr(pokemonName);
            }
            name.setText(pokemonName);
            String id = getIdWith3DigitsFormat(pokemon.getId());
            number.setText(id);
            name.setOnClickListener(view -> Log.i(TAG, "displayPokemon " + name));

            String urlImg = pokemon.getSprite().getOther().getOfficialArtwork().getFrontDefault();

            Glide.with(context)
                    .load(urlImg)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.shape_pokemon)
                            .error(R.drawable.unknow_pokemon))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(image);

        }
    }


}