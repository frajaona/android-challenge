package com.farmdrop.challenge.producers;


import android.app.Application;
import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.dagger.components.DaggerProducersComponent;
import com.farmdrop.challenge.producers.dagger.components.ProducersComponent;

public class ProducersApplication extends Application {

    private ProducersComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerProducersComponent.builder().build();
    }

    @NonNull
    public ProducersComponent getComponent() {
        return mComponent;
    }
}
