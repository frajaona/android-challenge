package com.farmdrop.challenge.producers.dagger.modules;

import com.farmdrop.challenge.producers.BuildConfig;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class FarmdropApiNetModule {

    private static final String KEY_FARMDROP_API_URL = "farmdropApiUrl";

    @Provides
    Retrofit provideRetrofit(@Named(KEY_FARMDROP_API_URL) String farmdropApiUrl) {
        return new Retrofit.Builder()
            .baseUrl(farmdropApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    @Named(KEY_FARMDROP_API_URL)
    String provideFarmdropApiUrl() {
        return BuildConfig.FARM_DROP_API_URL;
    }
}
