package com.farmdrop.challenge.producers.dagger.components;

import com.farmdrop.challenge.producers.dagger.modules.ProducersModule;
import com.farmdrop.challenge.producers.ui.ProducersListActivity;

import dagger.Component;

@Component(modules = {ProducersModule.class})
public interface ProducersComponent {
    void inject(ProducersListActivity activity);
}
