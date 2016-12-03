package com.farmdrop.challenge.producers.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.farmdrop.challenge.producers.R;

public class ProducerDetailsActivity extends AppCompatActivity {

    private ProducerDetailsFragment mProducerDetailsFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_details);
        mProducerDetailsFragment = (ProducerDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_producer_details_fragment);
    }
}
