package com.farmdrop.challenge.producers.dagger.components;

import com.farmdrop.challenge.producers.dagger.modules.FarmdropApiNetModule;
import com.farmdrop.challenge.producers.dagger.modules.ProducersModule;
import com.farmdrop.challenge.producers.ui.ProducersActivity;

import dagger.Component;

@Component(modules = {ProducersModule.class, FarmdropApiNetModule.class})
public interface ProducersComponent {
    void inject(ProducersActivity activity);
}
