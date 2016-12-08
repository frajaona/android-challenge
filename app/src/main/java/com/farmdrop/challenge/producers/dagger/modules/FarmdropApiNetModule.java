package com.farmdrop.challenge.producers.dagger.modules;

import com.farmdrop.challenge.producers.BuildConfig;
import com.farmdrop.challenge.producers.config.gson.AndroidNamingFieldStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class FarmdropApiNetModule {

    private static final String KEY_FARMDROP_API_URL = "farmdropApiUrl";

    @Provides
    Retrofit provideRetrofit(@Named(KEY_FARMDROP_API_URL) String farmdropApiUrl, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
            .baseUrl(farmdropApiUrl)
            .addConverterFactory(gsonConverterFactory)
            .build();
    }

    @Provides
    @Named(KEY_FARMDROP_API_URL)
    String provideFarmdropApiUrl() {
        return BuildConfig.FARM_DROP_API_URL;
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
            .setFieldNamingStrategy(new AndroidNamingFieldStrategy())
            .create();
    }
}
