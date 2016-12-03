package com.farmdrop.challenge.producers.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;

public class ProducerDetailsFragment extends Fragment {

    private static final String TAG = "ProducerDetailsFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_producer_details, container);
        return rootView;
    }

    @UiThread
    public void displayProducer(@NonNull Producer producer) {
        Log.d(TAG, "Display Producer: " + producer);
    }
}
