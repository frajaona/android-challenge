package com.farmdrop.challenge.producers.presenters;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.ProducersListener;
import com.farmdrop.challenge.producers.model.provider.ProducersProvider;

import javax.inject.Inject;

public class ProducersListPresenter {

    @NonNull
    private final ProducersProvider mProvider;

    @Inject
    public ProducersListPresenter(@NonNull ProducersProvider provider) {
        mProvider = provider;
        mProvider.loadProducers();
    }

    public void registerListener(@NonNull ProducersListener listener) {
        mProvider.registerListener(listener);
    }

    public void unregisterListener() {
        mProvider.unregisterListener();
    }

    public void loadNextProducers() {
        mProvider.loadNextProducers();
    }
}
