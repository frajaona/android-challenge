package com.farmdrop.challenge.producers.model;


import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ProducersNetProvider {

    private static final int PAGE_UNKNOWN_INDEX = -1;
    private static final int PAGE_FIRST_INDEX = 1;
    private static final int PAGE_DEFAULT_LIMIT = 10;

    interface ProducersService {
        @GET("2/producers")
        Call<ProducersPage> getProducersPage(@Query("page") int page, @Query("per_page_limit") int limit);
    }

    @NonNull
    @Inject
    Retrofit mRetrofit;

    @NonNull
    private final ProducersService mProducersService;

    private int mNextPage = PAGE_UNKNOWN_INDEX;

    public ProducersNetProvider(@NonNull Retrofit retrofit) {
        mRetrofit = retrofit;
        mProducersService = retrofit.create(ProducersService.class);
    }

    public void loadProducers(@NonNull final ProducersListener listener) {
        loadProducers(listener, PAGE_FIRST_INDEX, PAGE_DEFAULT_LIMIT);
    }

    public void loadNextProducers(@NonNull final ProducersListener listener) {
        if (mNextPage == -1) {
            mNextPage = PAGE_FIRST_INDEX + 1;
        }
        loadProducers(listener, mNextPage, PAGE_DEFAULT_LIMIT);
    }

    private void loadProducers(@NonNull final ProducersListener listener, int page, int limit) {
        mProducersService.getProducersPage(page, limit).enqueue(new Callback<ProducersPage>() {
            @Override
            public void onResponse(Call<ProducersPage> call, Response<ProducersPage> response) {
                if (response.isSuccessful()) {
                    ProducersPage page = response.body();
                    List<Producer> producers = page.getResponse();
                    if (producers != null && !producers.isEmpty()) {
                        mNextPage = page.getPagination().getNext();
                    }
                    listener.onProducersLoaded(producers);
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
