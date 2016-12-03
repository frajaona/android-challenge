package com.farmdrop.challenge.producers.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Image;
import com.farmdrop.challenge.producers.model.Producer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProducerDetailsFragment extends Fragment {

    private static final String TAG = "ProducerDetailsFragment";

    @BindView(R.id.fragment_producer_details_image)
    ImageView mImageView;

    @BindView(R.id.fragment_producer_details_textview_description)
    TextView mDescriptionTextView;


    @BindView(R.id.fragment_producer_details_textview_location)
    TextView mLocationTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_producer_details, container);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @UiThread
    public void displayProducer(@NonNull Producer producer) {
        List<Image> images = producer.getImages();
        if (images == null || images.isEmpty()) {
            mImageView.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(images.get(0).getPath()).into(mImageView);
        }

        String location = producer.getLocation();
        if (!TextUtils.isEmpty(location)) {
            mLocationTextView.setText(location);
        } else {
            mLocationTextView.setVisibility(View.GONE);
        }

        String description = producer.getDescription();
        if (TextUtils.isEmpty(description)) {
            description = producer.getShortDescription();
        }
        if (!TextUtils.isEmpty(description)) {
            mDescriptionTextView.setText(description);
        } else {
            mDescriptionTextView.setVisibility(View.GONE);
        }
    }
}
