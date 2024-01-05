package com.example.pokedex.ui.controllers;

import static com.example.pokedex.utils.Utils.LIMIT;
import static com.example.pokedex.utils.Utils.OFFSET_FIRST_PAGE;
import static com.example.pokedex.utils.Utils.SECONDS_LOADING;
import static com.example.pokedex.utils.Utils.extractOffsetOf;
import static com.example.pokedex.utils.Utils.getOffsetSaved;
import static com.example.pokedex.utils.Utils.initToolbar;
import static com.example.pokedex.utils.Utils.saveCurrentOffset;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.databinding.ActivityMainBinding;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.ui.adapter.PokemonAdapter;
import com.example.pokedex.viewModel.PokemonViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private PokemonAdapter adapter;

    private PokemonViewModel pokemonViewModel;

    private Handler handler;

    private String previous = null;
    private String next = null;
    ArrayList<Pokemon> pokemons = new ArrayList<>();

    private boolean pokemonSearched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        handler = new Handler(Looper.getMainLooper());
        initToolbar(MainActivity.this, binding.toolbar.getRoot());

        initViewModel();

        setupSearchViewListener();
        setupRecyclerViewListener();

        getIdsPokemonListFromService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int offsetSaved = getOffsetSaved(this);
        pokemonViewModel.updateOffset(offsetSaved);

    }

    @Override
    protected void onPause() {
        super.onPause();
        assert pokemonViewModel.getLiveDataOffset().getValue() != null;
        int offset = pokemonViewModel.getLiveDataOffset().getValue();
        saveCurrentOffset(this, offset);
    }


    private void initViewModel() {

        pokemonViewModel = new ViewModelProvider(MainActivity.this).get(PokemonViewModel.class);
    }

    private void setupSearchViewListener() {

        binding.toolbar.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String name) {

                if (name.length() == 3) {

                    pokemonViewModel.getLiveDataPokemonsSearched().observe(MainActivity.this, pokemonsSearched -> updateRecyclerView(pokemonsSearched));
                    pokemonViewModel.getAllPokemonsByName(name);
                    pokemonSearched = true;
                } else if (name.length() == 0) {

                    refreshPreviousPokemonListState();

                    pokemonSearched = false;

                }
                return false;
            }
        });
    }

    private void refreshPreviousPokemonListState() {
        if (pokemonViewModel.getLiveDataOffset().getValue() != null && pokemonViewModel.getLiveDataOffset().getValue() > 0) {
            int previousOffset = pokemonViewModel.getLiveDataOffset().getValue();
            pokemonViewModel.updateOffset(previousOffset);
        } else {
            pokemonViewModel.updateOffset(OFFSET_FIRST_PAGE);
        }
    }

    private void setupRecyclerViewListener() {
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();


                if (dy < 0 && isBeginOfList(firstVisibleItemPosition) && isPreviousPageNotNull() && !pokemonSearched) {

                    updateRecyclerviewToPage(previous);

                } else if (dy > 0 && isEndOfList(lastVisibleItemPosition, totalItemCount) && isNextPageNotNull() && !pokemonSearched) {

                    updateRecyclerviewToPage(next);
                }
            }
        });
    }

    private boolean isBeginOfList(int firstVisibleItemPosition) {
        return firstVisibleItemPosition == 0;
    }

    private boolean isEndOfList(int lastVisibleItemPosition, int totalItemCount) {
        return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == totalItemCount - 1;
    }

    private boolean isPreviousPageNotNull() {
        return previous != null;
    }

    private boolean isNextPageNotNull() {
        return next != null;
    }

    private void updateRecyclerviewToPage(String url) {

        int offset = extractOffsetOf(url);
        pokemonViewModel.updateOffset(offset);
    }


    private void getIdsPokemonListFromService() {


        pokemonViewModel.getLiveDataIdsPokemonList().observe(MainActivity.this, idArrayLiveData -> {


            getPreviousAndNextUrls(pokemonViewModel.getLiveDataPrevious(), PageDirection.PREVIOUS);
            getPreviousAndNextUrls(pokemonViewModel.getLiveDataNext(), PageDirection.NEXT);

            getPokemons(idArrayLiveData);

        });


        pokemonViewModel.getLiveDataOffset().observe(MainActivity.this, offset -> pokemonViewModel.getIdsPokemonList(offset, LIMIT));
    }

    private void getPreviousAndNextUrls(LiveData<String> liveDataPage, PageDirection page) {

        liveDataPage.observe(MainActivity.this, pageUrl -> {
            if (page.equals(PageDirection.PREVIOUS)) {
                previous = pageUrl;
            } else {
                next = pageUrl;
            }
        });
    }

    private void getPokemons(ArrayList<Integer> ids) {

        adapter = new PokemonAdapter(pokemons, MainActivity.this);
        binding.recyclerView.setAdapter(adapter);

        pokemonViewModel.getLiveDataPokemonArrayListSortedById().observe(MainActivity.this, this::updateRecyclerView);


        pokemonViewModel.getAllPokemonFromPageSortedById(ids);
    }


    private void updateRecyclerView(ArrayList<Pokemon> pokemonArrayList) {
        showProgressBarForNumberOfAFewSeconds();
        pokemons.clear();
        pokemons.addAll(pokemonArrayList);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showProgressBarForNumberOfAFewSeconds() {

        binding.progressCircular.setVisibility(View.VISIBLE);
        // Hide the progress bar after 2 seconds
        handler.postDelayed(MainActivity.this::hideProgressBar, SECONDS_LOADING);
    }


    private void hideProgressBar() {
        binding.progressCircular.setVisibility(View.INVISIBLE);
    }


    public enum PageDirection {
        PREVIOUS, NEXT
    }

}