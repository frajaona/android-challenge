package com.farmdrop.challenge.producers.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.farmdrop.challenge.producers.ProducersApplication;
import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersListener;
import com.farmdrop.challenge.producers.model.provider.ProducersProvider;
import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

import java.util.List;

import javax.inject.Inject;

public class ProducersActivity extends AppCompatActivity {
    @Inject
    ProducersListPresenter mPresenter;

    private ProducersListFragment mProducersListFragment;

    private ProducerDetailsFragment mProducerDetailsFragment;

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
        ((ProducersApplication)getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_producers);
        mProducersListFragment = (ProducersListFragment) getSupportFragmentManager().findFragmentById(R.id.activity_producers_fragment_list);
        mProducerDetailsFragment = (ProducerDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_producers_fragment_details);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.registerListener(new ProducersListenerImpl());
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unregisterListener();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchActionMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Producer> producersSearchResult = mPresenter.searchProducers(query);
                mProducersListFragment.setScrollDownToLoadNextEnable(false);
                mProducersListFragment.displayProducers(producersSearchResult);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                List<Producer> localProducers = mPresenter.getProducers();
                mProducersListFragment.setScrollDownToLoadNextEnable(true);
                mProducersListFragment.displayProducers(localProducers);
                return false;
            }
        });
        return true;
    }

    private class ProducersListenerImpl implements ProducersListener {
        @Override
        public void onNewProducersLoaded(@NonNull List<Producer> producers) {
            mProducersListFragment.displayProducers(producers);
        }

        @Override
        public void onError(@ProducersProvider.Error int error) {
            mProducersListFragment.displayError(error);
        }
    }

    @NonNull
    public OnProducersListActionListener getOnProducersListActionListener() {
        return mOnProducersListActionListener;
    }
}
