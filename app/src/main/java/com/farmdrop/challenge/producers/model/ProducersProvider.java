package com.farmdrop.challenge.producers.model;


import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

public class ProducersProvider {

    @IntDef({ERROR_UNKNOWN, ERROR_NO_INTERNET})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {
    }

    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_NO_INTERNET = 0;


    @NonNull
    private final ProducersNetProvider mNetProvider;

    @Nullable
    private ProducersListener mListener;

    @Inject
    public ProducersProvider(@NonNull ProducersNetProvider netProvider) {
        mNetProvider = netProvider;
    }

    public void registerListener(@NonNull ProducersListener listener) {
        mListener = listener;
    }

    public void unregisterListener() {
        mListener = null;
    }
}
