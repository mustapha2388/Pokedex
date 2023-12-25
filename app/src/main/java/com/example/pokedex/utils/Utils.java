package com.example.pokedex.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.pokedex.R;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonListItem;
import com.example.pokedex.ui.controllers.DetailActivity;
import com.example.pokedex.ui.controllers.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String ID_POKEMON = "ID_POKEMON";
    public static int SECONDS_LOADING = 1200;

    public static int LIMIT = 40;


    public static String OFFSET = "OFFSET";
    public static String OFFSET_PREFS = "OFFSET_PREFS";


    public static void startActivity(Context context, Class<?> destinationClass) {
        Intent intent = new Intent(context, destinationClass);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> destinationClass, int id) {
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(ID_POKEMON, id);
        context.startActivity(intent);
    }


    public static void initToolbar(AppCompatActivity activity, Toolbar toolbar) {
        hideStatusBar(activity);
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null && activity instanceof DetailActivity) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Resources resources = activity.getResources();
            Drawable backArrow = ResourcesCompat.getDrawable(resources, R.drawable.arrow_back, null);
            actionBar.setHomeAsUpIndicator(backArrow);
        }
    }


    public static void hideStatusBar(AppCompatActivity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static ArrayList<Integer> extractAllIdsFromList(List<PokemonListItem> pokemonListItem) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (PokemonListItem item : pokemonListItem) {
            String[] parts = item.getUrl().split("pokemon/");
            String idCleaned = parts[1].replace("/", "");
            ids.add(Integer.parseInt(idCleaned));
        }
        return ids;
    }

    public static ArrayList<Pokemon> getPokemonArrayListSorted(ArrayList<Pokemon> pokemonArrayList) {

        ArrayList<Pokemon> pokemonArrayListSorted;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pokemonArrayListSorted = pokemonArrayList.stream()
                    .sorted(Comparator.comparing(Pokemon::getId)).collect(Collectors.toCollection(ArrayList::new));
        } else {
            Collections.sort(pokemonArrayList, new Comparator<Pokemon>() {
                @Override
                public int compare(Pokemon p1, Pokemon p2) {
                    return Integer.compare(p1.getId(), p2.getId());
                }
            });
            // Initialize pokemonArrayListSorted if using the else block
            pokemonArrayListSorted = new ArrayList<>(pokemonArrayList);
        }

        return pokemonArrayListSorted;
    }

    public static void saveCurrentOffset(MainActivity mainActivity, int offset) {
        SharedPreferences preferences = mainActivity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(OFFSET_PREFS, offset);
        editor.apply();
    }


    public static int getOffsetSaved(MainActivity mainActivity) {
        SharedPreferences preferences = mainActivity.getPreferences(MODE_PRIVATE);
        return preferences.getInt(OFFSET_PREFS, 0);
    }
}
