package com.farmdrop.challenge.producers.presenters;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersListener;
import com.farmdrop.challenge.producers.model.ProducersSearchListener;
import com.farmdrop.challenge.producers.model.provider.ProducersProvider;

import java.util.List;

import javax.inject.Inject;

public class ProducersListPresenter {

    @NonNull
    private final ProducersProvider mProvider;

    @Inject
    public ProducersListPresenter(@NonNull ProducersProvider provider) {
        mProvider = provider;
    }

    public void loadProducers() {
        mProvider.loadProducers();
    }

    public void registerListener(@NonNull ProducersListener listener) {
        mProvider.registerListener(listener);
    }

    public void unregisterListener() {
        mProvider.unregisterListener();
    }

    public void searchProducers(@NonNull String query) {
        mProvider.searchProducers(query);
    }

    public void registerSearchListener(@NonNull ProducersSearchListener searchListener) {
        mProvider.registerSearchListener(searchListener);
    }

    public void unregisterSearchListener() {
        mProvider.unregisterSearchListener();
    }

    @NonNull
    public List<Producer> getProducers() {
        return mProvider.getProducers();
    }
}
