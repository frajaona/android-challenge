package com.farmdrop.challenge.producers.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.farmdrop.challenge.producers.ProducersApplication;
import com.farmdrop.challenge.producers.model.ProducersProvider;
import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;
import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersListener;

import java.util.List;

import javax.inject.Inject;

public class ProducersListActivity extends AppCompatActivity {

    @Inject
    ProducersListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producers_list);
        ((ProducersApplication)getApplication()).getComponent().inject(this);
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

    private class ProducersListenerImpl implements ProducersListener {

        @Override
        public void onProducersLoaded(@NonNull List<Producer> producers) {

        }

        @Override
        public void onError(@ProducersProvider.Error int error) {

        }

    }
}
