package com.farmdrop.challenge.producers.model.provider;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersListener;
import com.farmdrop.challenge.producers.model.ProducersSearchListener;
import com.google.common.collect.Lists;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;

import java.util.Iterator;
import java.util.List;

public class ProducersLocalProvider {

    @Nullable
    private SearchProducersTask mSearchProducersTask;

    public void loadProducers(@NonNull ProducersListener listener) {
        Iterator<Producer> iterator = SugarRecord.findAll(Producer.class);
        listener.onNewProducersLoaded(Lists.newArrayList(iterator));
    }

    public void searchProducers(@NonNull String query, @NonNull ProducersSearchListener searchListener) {
        if (mSearchProducersTask != null) {
            mSearchProducersTask.cancel(true);
        }
        mSearchProducersTask = new SearchProducersTask(query, searchListener);
        mSearchProducersTask.execute((Void)null);
    }

    private static class SearchProducersTask extends AsyncTask<Void, Void, List<Producer>> {
        @NonNull
        private final String mQuery;

        @NonNull
        private final ProducersSearchListener mProducersSearchListener;

        public SearchProducersTask(@NonNull String query, @NonNull ProducersSearchListener searchListener) {
            mQuery = query;
            mProducersSearchListener = searchListener;
        }

        @Override
        protected List<Producer> doInBackground(Void... param) {
            if (!isCancelled()) {
                return SugarRecord.find(Producer.class, NamingHelper.toSQLNameDefault(Producer.NAMING_PRODUCER_NAME) + " LIKE ?", "%" + mQuery + "%");
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Producer> producers) {
            if (!isCancelled()) {
                mProducersSearchListener.onProducersFound(mQuery, producers);
            }
        }
    }
}
