package com.farmdrop.challenge.producers.model;


import javax.inject.Inject;

import retrofit2.Retrofit;

public class ProducersNetProvider {

    @Inject
    Retrofit mRetrofit;

    public ProducersNetProvider(Retrofit retrofit) {
        mRetrofit = retrofit;
    }
}
