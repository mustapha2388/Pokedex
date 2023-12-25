package com.example.pokedex.ui.controllers;

import static com.example.pokedex.utils.Utils.LIMIT;
import static com.example.pokedex.utils.Utils.OFFSET;
import static com.example.pokedex.utils.Utils.SECONDS_LOADING;
import static com.example.pokedex.utils.Utils.getOffsetSaved;
import static com.example.pokedex.utils.Utils.initToolbar;
import static com.example.pokedex.utils.Utils.saveCurrentOffset;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        handler = new Handler(Looper.getMainLooper());
        initToolbar(MainActivity.this, binding.toolbar.getRoot());

        initViewModel();
        setupRecyclerViewListener();

        if (pokemonViewModel.getLiveDataOffset().getValue() != null && pokemonViewModel.getLiveDataOffset().getValue() > 0) {
            pokemonViewModel.updateOffset(pokemonViewModel.getLiveDataOffset().getValue());
        } else {
            pokemonViewModel.updateOffset(0);
        }


        getIdsPokemonListFromService();

    }

    private void initViewModel() {

        pokemonViewModel = new ViewModelProvider(MainActivity.this).get(PokemonViewModel.class);
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


                if (dy < 0 && isBeginOfList(firstVisibleItemPosition) && previous != null) {

                    updateUIRecyclerviewToPage(previous);

                } else if (dy > 0 && next != null && isEndOfList(lastVisibleItemPosition, totalItemCount)) {

                    updateUIRecyclerviewToPage(next);
                }

            }
        });
    }


    private boolean isEndOfList(int lastVisibleItemPosition, int totalItemCount) {
        return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == totalItemCount - 1;
    }

    private boolean isBeginOfList(int firstVisibleItemPosition) {
        return firstVisibleItemPosition == 0;
    }

    private void updateUIRecyclerviewToPage(String url) {

        int offset = extractOffsetOf(url);
        pokemonViewModel.updateOffset(offset);
    }


    private int extractOffsetOf(String url) {
        if (url != null && url.contains(OFFSET.toLowerCase())) {
            String[] urlParts = url.split(OFFSET.toLowerCase());
            if (urlParts.length > 1) {
                String offsetStr = urlParts[1].split("&")[0].replace("=", "");
                return Integer.parseInt(offsetStr);
            }
        }
        return 0;
    }


    private void showProgressBarForNumberOfSeconds() {

        binding.progressCircular.setVisibility(View.VISIBLE);
        // Hide the progress bar after 2 seconds
        handler.postDelayed(MainActivity.this::hideProgressBar, SECONDS_LOADING);
    }


    private void hideProgressBar() {
        binding.progressCircular.setVisibility(View.INVISIBLE);
    }

    private void getIdsPokemonListFromService() {


        pokemonViewModel.getLiveDataIdsPokemonList().observe(MainActivity.this, idArrayLiveData -> {


            getPreviousAndNextUrls(pokemonViewModel.getLiveDataPrevious(), PageDirection.PREVIOUS);
            getPreviousAndNextUrls(pokemonViewModel.getLiveDataNext(), PageDirection.NEXT);

            updateRecyclerView(idArrayLiveData);

        });


        pokemonViewModel.getLiveDataOffset().observe(MainActivity.this, offsetLiveData -> {
            pokemonViewModel.getIdsPokemonList(offsetLiveData, LIMIT);
        });
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

    private void updateRecyclerView(ArrayList<Integer> ids) {

        adapter = new PokemonAdapter(pokemons, MainActivity.this);
        binding.recyclerView.setAdapter(adapter);

        pokemonViewModel.getLiveDataPokemonArrayListSortedById().observe(MainActivity.this, pokemonArrayList -> {
            showProgressBarForNumberOfSeconds();
            pokemons.clear();
            pokemons.addAll(pokemonArrayList);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });


        pokemonViewModel.getAllPokemonFromPageSortedById(ids);


    }

    public enum PageDirection {
        PREVIOUS, NEXT
    }

}