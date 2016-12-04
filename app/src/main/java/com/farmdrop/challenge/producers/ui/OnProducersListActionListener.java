package com.farmdrop.challenge.producers.ui;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.Producer;

public interface OnProducersListActionListener {
    void onProducerClick(@NonNull Producer producer);
    void onLoadNextNeeded();
}
