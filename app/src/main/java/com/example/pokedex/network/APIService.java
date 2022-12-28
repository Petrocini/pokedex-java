package com.example.pokedex.network;

import com.example.pokedex.entity.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("/api/v2/pokemon")
    Call<PokemonResponse> fetchPokemon(@Query("limit") int limit, @Query("offset") int offset);

}
