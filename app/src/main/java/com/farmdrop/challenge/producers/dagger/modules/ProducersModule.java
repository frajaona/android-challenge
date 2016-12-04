package com.farmdrop.challenge.producers.dagger.modules;

import com.farmdrop.challenge.producers.model.provider.ProducersLocalProvider;
import com.farmdrop.challenge.producers.model.provider.ProducersNetProvider;
import com.farmdrop.challenge.producers.model.provider.ProducersProvider;
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
    ProducersProvider provideProducersProvider(ProducersNetProvider netProvider, ProducersLocalProvider localProvider) {
        return new ProducersProvider(netProvider, localProvider);
    }

    @Provides
    ProducersNetProvider provideProducersNetProvider(Retrofit retrofit) {
        return new ProducersNetProvider(retrofit);
    }

    @Provides
    ProducersLocalProvider provideProducersLocalProvider() {
        return new ProducersLocalProvider();
    }
}
