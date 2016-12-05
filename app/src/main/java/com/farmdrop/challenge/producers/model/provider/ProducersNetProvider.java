package com.farmdrop.challenge.producers.model.provider;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.Pagination;
import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersListener;
import com.farmdrop.challenge.producers.model.ProducersPage;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ProducersNetProvider {
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

    public ProducersNetProvider(@NonNull Retrofit retrofit) {
        mRetrofit = retrofit;
        mProducersService = retrofit.create(ProducersService.class);
    }

    public void loadProducers(@NonNull final ProducersListener listener, int page) {
        loadProducers(listener, page, PAGE_DEFAULT_LIMIT);
    }

    public int calcNextPageIndex(int localNbProducers) {
        return localNbProducers / PAGE_DEFAULT_LIMIT + 1;
    }

    private void loadProducers(@NonNull final ProducersListener listener, int page, int limit) {
        mProducersService.getProducersPage(page, limit).enqueue(new Callback<ProducersPage>() {
            @Override
            public void onResponse(Call<ProducersPage> call, Response<ProducersPage> response) {
                if (response.isSuccessful()) {
                    ProducersPage page = response.body();
                    List<Producer> producers = page.getResponse();
                    Pagination pagination = page.getPagination();
                    if (producers != null && !producers.isEmpty()) {
                        listener.onNewProducersLoaded(producers);
                        if (!(pagination.getCurrent() < pagination.getPages())) {
                            listener.onError(ProducersProvider.ERROR_ALL_LOADED);
                        }
                    } else {
                        listener.onError(ProducersProvider.ERROR_ALL_LOADED);
                    }
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
