package com.farmdrop.challenge.producers.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.farmdrop.challenge.producers.ProducersApplication;
import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.listener.ProducersListener;
import com.farmdrop.challenge.producers.model.listener.ProducersSearchListener;
import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

import java.util.List;

import javax.inject.Inject;

public class ProducersActivity extends AppCompatActivity {
    private static final String KEY_QUERY = "KEY_QUERY";

    @Inject
    ProducersListPresenter mPresenter;

    private ProducersListFragment mProducersListFragment;

    private ProducerDetailsFragment mProducerDetailsFragment;

    @Nullable
    private SearchView mSearchView;

    @Nullable
    private String mLastQuery;

    @NonNull
    private final OnProducersListActionListener mOnProducersListActionListener = new OnProducersListActionListener() {
        @Override
        public void onProducerClick(@NonNull Producer producer) {
            if (mProducerDetailsFragment != null) {
                mProducerDetailsFragment.displayProducer(producer);
            } else {
                Intent intent = ProducerDetailsActivity.getStartingIntent(ProducersActivity.this, producer);
                startActivity(intent);
            }
        }

        @Override
        public void onLoadNextNeeded() {
            mPresenter.loadProducers();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ProducersApplication) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_producers);
        mProducersListFragment = (ProducersListFragment) getSupportFragmentManager().findFragmentById(R.id.activity_producers_fragment_list);
        mProducerDetailsFragment = (ProducerDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_producers_fragment_details);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_QUERY)) {
            mLastQuery = savedInstanceState.getString(KEY_QUERY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.registerListener(new ProducersListenerImpl());
        mPresenter.registerSearchListener(new ProducersSearchListenerImpl());
        restoreLastSearch();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unregisterListener();
        mPresenter.unregisterSearchListener();
    }

    @Override
    public void onBackPressed() {
        if (mSearchView != null && !mSearchView.isIconified()) {
            mSearchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("ConstantConditions") // mSearchView can't be null in this method.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchActionMenuItem);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProducers(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProducers(newText.trim());
                return true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                List<Producer> localProducers = mPresenter.getProducers();
                mProducersListFragment.setScrollDownToLoadNextEnable(true);
                mProducersListFragment.displayProducers(localProducers);
                return false;
            }
        });
        restoreLastSearch();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        super.onSaveInstanceState(outBundle);
        if (mSearchView != null && !mSearchView.isIconified()) {
            String query = mSearchView.getQuery().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                outBundle.putString(KEY_QUERY, query);
            }
        }
    }

    private boolean searchviewContainsQuery() {
        return mSearchView != null && !TextUtils.isEmpty(mSearchView.getQuery());
    }

    private void searchProducers(@NonNull String query) {
        mPresenter.searchProducers(query);
    }

    private void restoreLastSearch() {
        if (mSearchView != null) {
            String query = null;
            if (searchviewContainsQuery()) {
                query = mSearchView.getQuery().toString();
            } else if (mLastQuery != null) {
                query = mLastQuery;
                mLastQuery = null;
            }
            if (query != null) {
                mSearchView.setIconified(false);
                mSearchView.setQuery(query, true);
            }
        }
    }

    private class ProducersListenerImpl implements ProducersListener {
        @Override
        public void onNewProducersLoaded(@NonNull List<Producer> producers) {
            if (!searchviewContainsQuery()) {
                mProducersListFragment.displayProducers(producers);
            }
        }

        @Override
        public void onError(@ProducersListPresenter.Error int error) {
            mProducersListFragment.displayError(error);
        }
    }

    private class ProducersSearchListenerImpl implements ProducersSearchListener {

        @Override
        public void onProducersFound(@NonNull String query, @NonNull List<Producer> producers) {
            mProducersListFragment.setScrollDownToLoadNextEnable(false);
            mProducersListFragment.displayProducers(producers);
        }
    }

    @NonNull
    public OnProducersListActionListener getOnProducersListActionListener() {
        return mOnProducersListActionListener;
    }
}
