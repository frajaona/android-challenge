package com.farmdrop.challenge.producers.model.provider;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.ProducersListener;

public interface ProducersProviderInterface {
    void loadProducers(@NonNull ProducersListener listener);
}
