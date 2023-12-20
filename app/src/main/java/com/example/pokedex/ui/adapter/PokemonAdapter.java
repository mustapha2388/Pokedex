package com.example.pokedex.ui.adapter;

import static android.content.ContentValues.TAG;
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
import com.example.pokedex.ui.controllers.DetailActivity;

import java.util.ArrayList;
import java.util.Locale;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private final ArrayList<Pokemon> pokemons;
    private final Context context;

    public PokemonAdapter(ArrayList<Pokemon> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
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
                startActivity(context, DetailActivity.class, id);
            });

        }

        public void displayPokemon(Pokemon pokemon) {

            name.setText(pokemon.getName());
            String id = getIdWithFormat(pokemon.getId());
            number.setText(id);
            name.setOnClickListener(view -> Log.i(TAG, "displayPokemon " + name));

            String urlImg = pokemon.getSprites().getOthers().getOfficialArtwork().getFrontDefault();

            Glide.with(context)
                    .load(urlImg)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.shape_pokemon)
                            .error(R.drawable.unknow_pokemon))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(image);

        }

    }

    private String getIdWithFormat(int id) {
        return String.format(Locale.FRANCE, "#%03d", id);

    }
}