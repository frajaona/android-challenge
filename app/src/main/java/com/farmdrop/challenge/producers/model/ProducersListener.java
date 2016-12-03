package com.farmdrop.challenge.producers.model;


import android.support.annotation.NonNull;

import java.util.List;

public interface ProducersListener {
    void onProducersLoaded(@NonNull List<Producer> producers);
    void onError(@ProducersProvider.Error int error);
}
