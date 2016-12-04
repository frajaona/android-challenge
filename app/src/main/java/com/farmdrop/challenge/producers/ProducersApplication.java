package com.farmdrop.challenge.producers;


import android.app.Application;
import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.dagger.components.DaggerProducersComponent;
import com.farmdrop.challenge.producers.dagger.components.ProducersComponent;
import com.farmdrop.challenge.producers.dagger.modules.FarmdropApiNetModule;
import com.farmdrop.challenge.producers.dagger.modules.ProducersModule;

public class ProducersApplication extends Application {

    private ProducersComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerProducersComponent.builder()
            .farmdropApiNetModule(new FarmdropApiNetModule())
            .producersModule(new ProducersModule())
            .build();
    }

    @NonNull
    public ProducersComponent getComponent() {
        return mComponent;
    }
}
