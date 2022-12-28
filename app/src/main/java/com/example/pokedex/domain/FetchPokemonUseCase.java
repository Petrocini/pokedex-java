package com.example.pokedex.domain;

public interface FetchPokemonUseCase {
    void fetchPokemon(int limit, int offset);
}
