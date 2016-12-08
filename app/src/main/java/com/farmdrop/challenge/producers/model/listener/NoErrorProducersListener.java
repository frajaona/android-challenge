package com.farmdrop.challenge.producers.model.listener;

import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

public abstract class NoErrorProducersListener implements ProducersListener {
    @Override
    public void onError(@ProducersListPresenter.Error int error) {
    }
}
