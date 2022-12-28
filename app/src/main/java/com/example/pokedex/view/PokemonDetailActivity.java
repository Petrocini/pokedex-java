package com.example.pokedex.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.pokedex.databinding.ActivityPokemonDetailBinding;
import com.example.pokedex.ui.BaseActivity;

public class PokemonDetailActivity extends BaseActivity {

    private ActivityPokemonDetailBinding activityPokemonDetailBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPokemonDetailBinding = ActivityPokemonDetailBinding.inflate(getLayoutInflater());
        setContentView(activityPokemonDetailBinding.getRoot());
    }

}
