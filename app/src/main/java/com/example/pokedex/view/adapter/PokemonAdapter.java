package com.example.pokedex.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex.databinding.AdapterPokemonBinding;
import com.example.pokedex.entity.Pokemon;
import com.example.pokedex.glidepallete.BitmapPalette;
import com.example.pokedex.glidepallete.GlidePalette;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder> {

    private final Context context;
    private List<Pokemon> pokemonList;
    private final View.OnClickListener onClickListener;

    public PokemonAdapter(Context context, List<Pokemon> pokemonList, View.OnClickListener onClickListener) {
        this.context = context;
        this.pokemonList = pokemonList;
        this.onClickListener = onClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredList(List<Pokemon> filteredList) {
        this.pokemonList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPokemonBinding adapterPokemonBinding = AdapterPokemonBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(adapterPokemonBinding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        int id = position + 1;
        String urlImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";
        holder.adapterPokemonBinding.txtName.setText(pokemon.getName());
        Glide.with(context).load(urlImage)
                .listener(
                        GlidePalette.with(urlImage).use(BitmapPalette.Profile.MUTED_LIGHT)
                                .intoCallBack(palette -> {
                                    if (palette != null && palette.getDominantSwatch() != null) {
                                        int rgbHexCode = palette.getDominantSwatch().getRgb();
                                        holder.adapterPokemonBinding.cvPokemon.setCardBackgroundColor(rgbHexCode);
                                    }
                                }).crossfade(true))
                .into(holder.adapterPokemonBinding.imgPoke);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterPokemonBinding adapterPokemonBinding;

        public MyViewHolder(@NonNull AdapterPokemonBinding adapterPokemonBinding, View.OnClickListener onClickListener) {
            super(adapterPokemonBinding.getRoot());
            this.adapterPokemonBinding = adapterPokemonBinding;
            itemView.setOnClickListener(onClickListener);
        }
    }
}
