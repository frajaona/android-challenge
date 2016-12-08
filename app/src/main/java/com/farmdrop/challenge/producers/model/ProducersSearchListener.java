package com.farmdrop.challenge.producers.model;

import android.support.annotation.NonNull;

import java.util.List;

public interface ProducersSearchListener {
    void onProducersFound(@NonNull String query, @NonNull List<Producer> producers);
}
