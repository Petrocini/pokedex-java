package com.example.pokedex.view;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.pokedex.databinding.ActivityPokemonBinding;
import com.example.pokedex.entity.Pokemon;
import com.example.pokedex.ui.BaseActivity;
import com.example.pokedex.view.adapter.PokemonAdapter;
import com.example.pokedex.viewmodel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PokemonActivity extends BaseActivity {

    private PokemonViewModel pokemonViewModel;
    private ActivityPokemonBinding pokemonBinding;
    private PokemonAdapter pokemonAdapter;

    int limit = 20;
    int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokemonBinding = ActivityPokemonBinding.inflate(getLayoutInflater());
        setContentView(pokemonBinding.getRoot());

        viewModelConfiguration();
        recyclerViewConfiguration();
        searchViewConfiguration();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void viewModelConfiguration() {
        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        pokemonViewModel.fetchPokemon(limit, offset);

        pokemonViewModel.getIsLoadingMutableLiveData().observe(this, loading -> {
            if (loading) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        pokemonViewModel.getPokemonListMutableLiveData().observe(this, pokemons -> {
            pokemonViewModel.getPokemonList().addAll(pokemons);
            pokemonAdapter.notifyDataSetChanged();
        });
    }

    private void recyclerViewConfiguration() {
        pokemonBinding.rvPokemon.setLayoutManager(new GridLayoutManager(this, 2));
        pokemonBinding.rvPokemon.setHasFixedSize(true);
        pokemonBinding.rvPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { // dy > 0 = check for scroll down
                    if (!recyclerView.canScrollVertically(1)) {
                        limit += 20;
                        offset += 20;
                        pokemonViewModel.fetchPokemon(limit, offset);
                    }
                }
            }
        });
        pokemonAdapter = new PokemonAdapter(
                this,
                pokemonViewModel.getPokemonList(),
                view -> {
                    startActivity(new Intent(this, PokemonDetailActivity.class));
                });
        pokemonBinding.rvPokemon.setAdapter(pokemonAdapter);
    }

    private void searchViewConfiguration() {
        pokemonBinding.svPokemon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPokemonList(newText);
                return true;
            }
        });
    }

    private void filterPokemonList(String text) {
        List<Pokemon> filteredList = new ArrayList<>();
        for (Pokemon pokemon : pokemonViewModel.getPokemonList()) {
            if (pokemon.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(pokemon);
            }
        }

        if (!filteredList.isEmpty()) {
            pokemonAdapter.setFilteredList(filteredList);
        }
    }
}