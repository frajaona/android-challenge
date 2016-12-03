package com.farmdrop.challenge.producers.dagger.modules;

import com.farmdrop.challenge.producers.model.ProducersNetProvider;
import com.farmdrop.challenge.producers.model.ProducersProvider;
import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ProducersModule {
    @Provides
    public static ProducersListPresenter provideProducersListPresenter(ProducersProvider producersProvider) {
        return new ProducersListPresenter(producersProvider);
    }

    @Provides
    public static ProducersProvider provideProducersProvider(ProducersNetProvider netProvider) {
        return new ProducersProvider(netProvider);
    }

    @Provides
    public static ProducersNetProvider provideProducersNetProvider() {
        return new ProducersNetProvider();
    }
}
