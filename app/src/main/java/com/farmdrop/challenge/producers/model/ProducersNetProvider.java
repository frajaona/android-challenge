package com.farmdrop.challenge.producers.model;


import android.support.annotation.NonNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class ProducersNetProvider {

    interface ProducersService {
        @GET("2/producers")
        Call<ProducersPage> getProducersPage();
    }

    @NonNull
    @Inject
    Retrofit mRetrofit;

    @NonNull
    private final ProducersService mProducersService;

    public ProducersNetProvider(@NonNull Retrofit retrofit) {
        mRetrofit = retrofit;
        mProducersService = retrofit.create(ProducersService.class);
    }

    public void loadProducers(@NonNull final ProducersListener listener) {
        mProducersService.getProducersPage().enqueue(new Callback<ProducersPage>() {
            @Override
            public void onResponse(Call<ProducersPage> call, Response<ProducersPage> response) {
                if (response.isSuccessful()) {
                    listener.onProducersLoaded(response.body().getResponse());
                } else {
                    listener.onError(ProducersProvider.ERROR_UNKNOWN);
                }
            }

            @Override
            public void onFailure(Call<ProducersPage> call, Throwable t) {
                listener.onError(ProducersProvider.ERROR_NETWORK);
            }
        });
    }
}
