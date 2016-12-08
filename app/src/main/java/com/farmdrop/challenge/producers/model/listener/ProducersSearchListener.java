package com.farmdrop.challenge.producers.model.listener;

import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.Producer;

import java.util.List;

public interface ProducersSearchListener {
    void onProducersFound(@NonNull String query, @NonNull List<Producer> producers);
}
