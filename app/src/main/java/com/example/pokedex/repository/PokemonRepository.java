package com.example.pokedex.repository;

import com.example.pokedex.entity.PokemonResponse;
import com.example.pokedex.network.APIService;

import javax.inject.Inject;

import retrofit2.Call;

public class PokemonRepository {

    private final APIService apiService;

    @Inject
    public PokemonRepository(APIService apiService) {
        this.apiService = apiService;
    }

    public Call<PokemonResponse> fetchPokemon(int limit, int offset) {
        return apiService.fetchPokemon(limit, offset);
    }
}
