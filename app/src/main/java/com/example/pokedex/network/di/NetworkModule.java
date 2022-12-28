package com.example.pokedex.network.di;

import com.example.pokedex.network.APIService;
import com.example.pokedex.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    public static final String BASE_URL = Application.DEFAULT_URL;

    @Provides
    @Singleton
    OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(120, TimeUnit.SECONDS);
        builder.connectTimeout(120, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.addNetworkInterceptor(httpLoggingInterceptor);
        return builder.build();
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofitInstance(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static APIService provideApiService(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }

}

