package com.farmdrop.challenge.producers.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;

import org.parceler.Parcels;

public class ProducerDetailsActivity extends AppCompatActivity {
    private static final String KEY_PRODUCER = "KEY_PRODUCER";

    public static Intent getStartingIntent(@NonNull Context context, @NonNull Producer producer) {
        Intent intent = new Intent(context, ProducerDetailsActivity.class);
        intent.putExtra(KEY_PRODUCER, Parcels.wrap(producer));
        return intent;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_details);
        ProducerDetailsFragment producerDetailsFragment = (ProducerDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_producer_details_fragment);

        Producer producer = Parcels.unwrap(getIntent().getParcelableExtra(KEY_PRODUCER));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(producer.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        producerDetailsFragment.displayProducer(producer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
