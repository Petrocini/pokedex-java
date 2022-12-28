package com.example.pokedex.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonResponse {

    private int count;
    @SerializedName("results")
    private List<Pokemon> pokemonList;

    public int getCount() {
        return count;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
}
