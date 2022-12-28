package com.example.pokedex.entity;

import com.google.gson.annotations.SerializedName;

public class Pokemon {

    private String name;
    @SerializedName("url")
    private String urlImage;

    public Pokemon(String name, String urlImage) {
        this.name = name;
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
