package com.farmdrop.challenge.producers.model;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.provider.ProducersProvider;

import java.util.List;

public interface ProducersListener {
    void onNewProducersLoaded(@NonNull List<Producer> producers);
    void onError(@ProducersProvider.Error int error);
}
