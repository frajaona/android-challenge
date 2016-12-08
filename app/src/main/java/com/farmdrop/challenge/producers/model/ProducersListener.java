package com.farmdrop.challenge.producers.model;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

import java.util.List;

public interface ProducersListener {
    void onNewProducersLoaded(@NonNull List<Producer> producers);
    void onError(@ProducersListPresenter.Error int error);
}
