package com.example.pokedex.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.pokedex.domain.FetchPokemonUseCaseImpl;
import com.example.pokedex.entity.Pokemon;
import com.example.pokedex.entity.PokemonResponse;
import com.example.pokedex.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class PokemonViewModel extends BaseViewModel {

    private final MutableLiveData<List<Pokemon>> pokemonListMutableLiveData = new MutableLiveData<>();
    private final List<Pokemon> pokemonList = new ArrayList<>();

    private final FetchPokemonUseCaseImpl fetchPokemonUseCase;

    @Inject
    public PokemonViewModel(FetchPokemonUseCaseImpl fetchPokemonUseCase) {
        this.fetchPokemonUseCase = fetchPokemonUseCase;
        this.fetchPokemonUseCase.setLoading(getIsLoadingMutableLiveData());
        this.fetchPokemonUseCase.setFetchPokemonCallback(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                if (response.body() != null) {
                    pokemonListMutableLiveData.setValue(response.body().getPokemonList());
                }
                getIsLoadingMutableLiveData().setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<PokemonResponse> call, @NonNull Throwable t) {
                Log.e("errorrequest", t.getMessage());
                getIsLoadingMutableLiveData().setValue(false);
            }
        });
    }

    public void fetchPokemon(int limit, int offset) {
        fetchPokemonUseCase.fetchPokemon(limit, offset);
    }

    public MutableLiveData<List<Pokemon>> getPokemonListMutableLiveData() {
        return pokemonListMutableLiveData;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
}
