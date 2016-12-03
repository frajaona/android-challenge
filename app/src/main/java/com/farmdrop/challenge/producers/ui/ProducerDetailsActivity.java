package com.farmdrop.challenge.producers.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;

import org.parceler.Parcels;

public class ProducerDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ProducerDetailsActivity";

    private static final String KEY_PRODUCER = "KEY_PRODUCER";

    private ProducerDetailsFragment mProducerDetailsFragment;

    private Producer mProducer;

    public static Intent getStartingIntent(@NonNull Context context, @NonNull Producer producer) {
        Intent intent = new Intent(context, ProducerDetailsActivity.class);
        intent.putExtra(KEY_PRODUCER, Parcels.wrap(producer));
        return intent;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_details);
        mProducerDetailsFragment = (ProducerDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_producer_details_fragment);

        mProducer = Parcels.unwrap(getIntent().getParcelableExtra(KEY_PRODUCER));

        Log.d(TAG, "ProducerDetails: " + mProducer.getName());
    }
}
