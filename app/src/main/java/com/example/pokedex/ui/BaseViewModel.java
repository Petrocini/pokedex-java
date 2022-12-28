package com.example.pokedex.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    private final MutableLiveData<Boolean> mIsLoadingMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoadingMutableLiveData() {
        return mIsLoadingMutableLiveData;
    }
}
