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
import com.example.pokedex.model.FlavorTextEntries;
import com.example.pokedex.model.FlavorTextEntry;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonListItem;
import com.example.pokedex.ui.controllers.DetailActivity;
import com.example.pokedex.ui.controllers.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    public static String ID_POKEMON = "ID_POKEMON";
    public static int SECONDS_LOADING = 1200;

    public static int LIMIT = 40;
    public static int OFFSET_FIRST_PAGE = 0;

    public static boolean MODE_DEV = false;


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
        ((MainActivity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public static int getIdOfPokemon(Intent intent) {
        return intent.getIntExtra(ID_POKEMON, 0);
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
                public int compare(Pokemon pokemon1, Pokemon pokemon2) {
                    return Integer.compare(pokemon1.getId(), pokemon2.getId());
                }
            });
            pokemonArrayListSorted = new ArrayList<>(pokemonArrayList);
        }

        return pokemonArrayListSorted;
    }


    public static int getColorOfType(String colorName) {

        switch (colorName) {

            case "fighting":
                return R.color.Fighting;
            case "flying":
                return R.color.Flying;
            case "ground":
                return R.color.Ground;
            case "poison":
                return R.color.Poison;
            case "rock":
                return R.color.Rock;
            case "bug":
                return R.color.Bug;
            case "ghost":
                return R.color.Ghost;
            case "steel":
                return R.color.Steel;
            case "fire":
                return R.color.Fire;
            case "water":
                return R.color.Water;
            case "grass":
                return R.color.Grass;
            case "electric":
                return R.color.Electric;
            case "psychic":
                return R.color.Psychic;
            case "ice":
                return R.color.Ice;
            case "dragon":
                return R.color.Dragon;
            case "dark":
                return R.color.Dark;
            case "fairy":
                return R.color.Fairy;
            default:
                return R.color.Normal;
        }
    }


    public static int getStringOfType(String type) {

        switch (type) {

            case "fighting":
                return R.string.fighting;
            case "flying":
                return R.string.flying;
            case "ground":
                return R.string.ground;
            case "poison":
                return R.string.poison;
            case "rock":
                return R.string.rock;
            case "bug":
                return R.string.bug;
            case "ghost":
                return R.string.ghost;
            case "steel":
                return R.string.steel;
            case "fire":
                return R.string.fire;
            case "water":
                return R.string.water;
            case "grass":
                return R.string.grass;
            case "electric":
                return R.string.electric;
            case "psychic":
                return R.string.psychic;
            case "ice":
                return R.string.ice;
            case "dragon":
                return R.string.dragon;
            case "dark":
                return R.string.dark;
            case "fairy":
                return R.string.fairy;
            default:
                return R.string.normal;
        }
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

    public static int extractOffsetOf(String url) {
        if (url != null && url.contains(OFFSET.toLowerCase())) {
            String[] urlParts = url.split(OFFSET.toLowerCase());
            if (urlParts.length > 1) {
                String offsetStr = urlParts[1].split("&")[0].replace("=", "");
                return Integer.parseInt(offsetStr);
            }
        }
        return 0;
    }

    public static Set<String> extractUniqueDescriptionsFr(FlavorTextEntries flavorTextEntries) {

        Set<String> uniqueFlavorTextsSet = new HashSet<>();

        for (FlavorTextEntry flavorTextEntry : flavorTextEntries.getFlavorTextEntries()) {
            if (Objects.equals(flavorTextEntry.getLanguage().getName(), "fr")) {
                uniqueFlavorTextsSet.add(flavorTextEntry.getFlavorTexts());
            }
        }
        return uniqueFlavorTextsSet;
    }

    public static String concatenateDescriptionsWithLineBreaks(Set<String> uniqueFlavorTextsSet) {

        StringBuilder description = new StringBuilder();
        for (String s : uniqueFlavorTextsSet) {
            s = removeLineBreak(s);
            description.append(s).append("\n\n");
        }
        return description.toString();
    }

    private static String removeLineBreak(String description) {
        return description.replace("\n", "");
    }

    public static int extractIdFromUrl(String url) {

        if (url == null || url.isEmpty()) {
            return -1;
        }
        String[] segments = url.split("/");

        String lastSegment = segments[segments.length - 1];

        return Integer.parseInt(lastSegment);
    }

    public static String getIdWith3DigitsFormat(int id) {
        return String.format(Locale.FRANCE, "#%03d", id);

    }

    public static boolean isNameTooLong(String name) {
        return name.contains("-") && name.length() >= 10;
    }

    public static String shortcutStr(String name) {
        return name.split("-")[0];
    }

}
