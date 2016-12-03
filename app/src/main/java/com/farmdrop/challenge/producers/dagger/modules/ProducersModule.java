package com.farmdrop.challenge.producers.dagger.modules;

import com.farmdrop.challenge.producers.model.ProducersNetProvider;
import com.farmdrop.challenge.producers.model.ProducersProvider;
import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ProducersModule {
    @Provides
    ProducersListPresenter provideProducersListPresenter(ProducersProvider producersProvider) {
        return new ProducersListPresenter(producersProvider);
    }

    @Provides
    ProducersProvider provideProducersProvider(ProducersNetProvider netProvider) {
        return new ProducersProvider(netProvider);
    }

    @Provides
    ProducersNetProvider provideProducersNetProvider(Retrofit retrofit) {
        return new ProducersNetProvider(retrofit);
    }
}
