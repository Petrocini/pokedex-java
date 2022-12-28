package com.example.pokedex.domain;

import androidx.lifecycle.MutableLiveData;

import com.example.pokedex.entity.PokemonResponse;
import com.example.pokedex.repository.PokemonRepository;

import javax.inject.Inject;

import retrofit2.Callback;

public class FetchPokemonUseCaseImpl implements FetchPokemonUseCase {

    private final PokemonRepository pokemonRepository;
    private Callback<PokemonResponse> fetchPokemonCallback;
    private MutableLiveData<Boolean> loading;

    @Inject
    public FetchPokemonUseCaseImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public void fetchPokemon(int limit, int offset) {
        loading.setValue(true);
        pokemonRepository.fetchPokemon(limit, offset).enqueue(fetchPokemonCallback);
    }

    public void setFetchPokemonCallback(Callback<PokemonResponse> fetchPokemonCallback) {
        this.fetchPokemonCallback = fetchPokemonCallback;
    }

    public void setLoading(MutableLiveData<Boolean> loading) {
        this.loading = loading;
    }
}
