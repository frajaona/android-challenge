package com.farmdrop.challenge.producers.presenters;


import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.listener.NoErrorProducersListener;
import com.farmdrop.challenge.producers.model.listener.ProducersListener;
import com.farmdrop.challenge.producers.model.listener.ProducersSearchListener;
import com.farmdrop.challenge.producers.model.provider.ProducersLocalProvider;
import com.farmdrop.challenge.producers.model.provider.ProducersNetProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class ProducersListPresenter {

    @IntDef({ERROR_UNKNOWN, ERROR_NETWORK, ERROR_ALL_LOADED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {
    }

    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_NETWORK = 0;
    public static final int ERROR_ALL_LOADED = 1;

    @NonNull
    private final ProducersNetProvider mNetProvider;

    @NonNull
    private final ProducersLocalProvider mLocalProvider;

    @Nullable
    private ProducersListener mListener;

    @Nullable
    private ProducersSearchListener mSearchListener;

    @NonNull
    private final List<Producer> mProducersList;

    @NonNull
    private final SparseArray<Producer> mProducersSparseArray;

    /**
     * Network producers loading result listener.
     */
    @NonNull
    private final ProducersListener mNetProducersListener = new ProducersListener() {
        @Override
        public void onNewProducersLoaded(@NonNull List<Producer> producers) {
            updateLocalProducersFromNetwork(producers);
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

    /**
     * Local producers loading result listener.
     */
    @NonNull
    private final ProducersListener mLocalProducersListener = new NoErrorProducersListener() {
        @Override
        public void onNewProducersLoaded(@NonNull List<Producer> producers) {
            if (producers.isEmpty()) {
                loadProducers(true);
            } else {
                mProducersList.addAll(producers);
                for (Producer producer : producers) {
                    mProducersSparseArray.append(producer.getId(), producer);
                }
                // start update local producers from network, TODO: improve
                mNetProvider.loadProducers(mUpdateLocalProducersListener, 1, producers.size());
                if (mListener != null) {
                    mListener.onNewProducersLoaded(mProducersList);
                }
            }
        }
    };

    /**
     * Update local producers from network result listener.
     */
    @NonNull
    private final ProducersListener mUpdateLocalProducersListener = new NoErrorProducersListener() {
        @Override
        public void onNewProducersLoaded(@NonNull List<Producer> producers) {
            updateLocalProducersFromNetwork(producers);
            if (mListener != null) {
                mListener.onNewProducersLoaded(mProducersList);
            }
        }
    };

    /**
     * Search result listener.
     */
    @NonNull
    private final ProducersSearchListener mProducersSearchListener = new ProducersSearchListener() {
        @Override
        public void onProducersFound(@NonNull String query, @NonNull List<Producer> producers) {
            if (mSearchListener != null) {
                mSearchListener.onProducersFound(query, producers);
            }
        }
    };

    @Inject
    public ProducersListPresenter(@NonNull ProducersNetProvider netProvider, @NonNull ProducersLocalProvider localProvider) {
        mNetProvider = netProvider;
        mLocalProvider = localProvider;
        mProducersList = new LinkedList<>();
        mProducersSparseArray = new SparseArray<>();

        loadProducers();
    }

    @NonNull
    public List<Producer> getProducers() {
        return mProducersList;
    }

    public void loadProducers() {
        loadProducers(false);
    }

    private void loadProducers(boolean forceNet) {
        if (forceNet || !mProducersList.isEmpty()) {
            mNetProvider.loadProducers(mNetProducersListener, mNetProvider.calcNextPageIndex(mProducersList.size()));
        } else {
            mLocalProvider.loadProducers(mLocalProducersListener);
        }
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

    public void searchProducers(@NonNull String query) {
        mLocalProvider.searchProducers(query, mProducersSearchListener);
    }

    public void registerSearchListener(ProducersSearchListener producersSearchListener) {
        mSearchListener = producersSearchListener;
    }

    public void unregisterSearchListener() {
        mSearchListener = null;
    }

    /**
     * Update the producers list stored on the device from online producers. Local producers list is
     * created if it doesn't exist.
     *
     * @param onlineProducers Producers list fetched from network.
     */
    private void updateLocalProducersFromNetwork(@NonNull List<Producer> onlineProducers) {
        for (Producer producer : onlineProducers) {
            int producerId = producer.getId();
            Producer localProducer = mProducersSparseArray.get(producerId);
            if (localProducer == null) {
                producer.persist();
                mProducersList.add(producer);
                mProducersSparseArray.append(producerId, producer);
            } else if (producer.getUpdatedAt().after(localProducer.getUpdatedAt())) {
                localProducer.update(producer);
            }
        }
    }
}
