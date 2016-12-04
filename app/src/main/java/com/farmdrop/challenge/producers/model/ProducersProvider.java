package com.farmdrop.challenge.producers.model;


import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class ProducersProvider {

    @IntDef({ERROR_UNKNOWN, ERROR_NETWORK, ERROR_ALL_LOADED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {
    }

    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_NETWORK = 0;
    public static final int ERROR_ALL_LOADED = 1;


    @NonNull
    private final ProducersNetProvider mNetProvider;

    @Nullable
    private ProducersListener mListener;

    @NonNull
    private final List<Producer> mProducersList;

    @NonNull
    private final ProducersListener mProducersListener = new ProducersListener() {
        @Override
        public void onNewProducersLoaded(@NonNull List<Producer> producers) {
            mProducersList.addAll(producers);
            if (mListener != null) {
                mListener.onNewProducersLoaded(mProducersList);
            }
        }

        @Override
        public void onError(@Error int error) {
            if (mListener != null) {
                mListener.onError(error);
            }
        }
    };

    @Inject
    public ProducersProvider(@NonNull ProducersNetProvider netProvider) {
        mNetProvider = netProvider;
        mProducersList = new LinkedList<>();
    }

    public void loadProducers() {
        mNetProvider.loadProducers(mProducersListener);
    }

    public void loadNextProducers() {
        mNetProvider.loadNextProducers(mProducersListener);
    }

    public void registerListener(@NonNull ProducersListener listener) {
        mListener = listener;
        if (!mProducersList.isEmpty()) {
            mListener.onNewProducersLoaded(mProducersList);
        }
    }

    public void unregisterListener() {
        mListener = null;
    }
}
