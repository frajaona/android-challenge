package com.farmdrop.challenge.producers.presenters;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.ProducersListener;
import com.farmdrop.challenge.producers.model.ProducersProvider;

import javax.inject.Inject;

public class ProducersListPresenter {

    @NonNull
    private final ProducersProvider mProvider;

    @Inject
    public ProducersListPresenter(@NonNull ProducersProvider provider) {
        mProvider = provider;

    }

    public void registerListener(@NonNull ProducersListener listener) {
        mProvider.registerListener(listener);
    }

    public void unregisterListener() {
        mProvider.unregisterListener();
    }
}
