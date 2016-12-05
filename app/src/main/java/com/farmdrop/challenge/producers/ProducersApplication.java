package com.farmdrop.challenge.producers;


import android.app.Application;
import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.dagger.components.DaggerProducersComponent;
import com.farmdrop.challenge.producers.dagger.components.ProducersComponent;
import com.farmdrop.challenge.producers.dagger.modules.FarmdropApiNetModule;
import com.farmdrop.challenge.producers.dagger.modules.ProducersModule;
import com.orm.SugarContext;

public class ProducersApplication extends Application {

    private ProducersComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        mComponent = DaggerProducersComponent.builder()
            .farmdropApiNetModule(new FarmdropApiNetModule())
            .producersModule(new ProducersModule())
            .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    @NonNull
    public ProducersComponent getComponent() {
        return mComponent;
    }
}
