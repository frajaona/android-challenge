package com.farmdrop.challenge.producers.ui;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProducersRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_PRODUCER = 0;
    private static final int TYPE_LOADING = 1;

    @Nullable
    private List<Producer> mProducersList;

    @NonNull
    private final LayoutInflater mLayoutInflater;

    @Nullable
    private final OnProducersListActionListener mOnProducersListActionListener;

    private boolean mAllProducersLoaded;

    public ProducersRecyclerViewAdapter(@NonNull Context context, @Nullable OnProducersListActionListener onProducersListActionListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mOnProducersListActionListener = onProducersListActionListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PRODUCER) {
            View view = mLayoutInflater.inflate(R.layout.producers_recycler_view_item, parent, false);
            return new ProducerViewHolder(view, mOnProducersListActionListener);
        } else {
            View view = mLayoutInflater.inflate(R.layout.producers_recycler_view_loading_item, parent, false);
            return new ContentLoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mProducersList != null && holder instanceof ProducerViewHolder) {
            Producer producer = mProducersList.get(position);
            ((ProducerViewHolder) holder).displayProducer(producer);
        }
    }

    @Override
    public int getItemCount() {
        if (mProducersList != null) {
            return mAllProducersLoaded ? mProducersList.size() : mProducersList.size() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mProducersList != null && position == mProducersList.size() ? TYPE_LOADING : TYPE_PRODUCER;
    }

    public void setProducersList(@NonNull List<Producer> producersList) {
        mProducersList = producersList;
    }

    public void setAllProducersLoaded(boolean allProducersLoaded) {
        mAllProducersLoaded = allProducersLoaded;
    }

    static class ProducerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.producers_recycler_view_item_textview_name)
        TextView mNameTextView;

        @BindView(R.id.producers_recycler_view_item_textview_short_description)
        TextView mShortDescriptionTextView;

        @Nullable
        private Producer mProducer;

        @Nullable
        private final OnProducersListActionListener mProducersListActionListener;

        public ProducerViewHolder(View itemView, @Nullable OnProducersListActionListener producersListActionListener) {
            super(itemView);
            mProducersListActionListener = producersListActionListener;
            ButterKnife.bind(this, itemView);
        }

        public void displayProducer(@NonNull Producer producer) {
            mProducer = producer;
            mNameTextView.setText(producer.getName());
            String shortDescription = producer.getShortDescription().trim();
            if (!TextUtils.isEmpty(shortDescription)) {
                mShortDescriptionTextView.setText(producer.getShortDescription());
            } else {
                mShortDescriptionTextView.setText(R.string.no_description);
            }
        }

        @OnClick(R.id.producers_recycler_view_item_layout_root)
        public void onClick() {
            if (mProducersListActionListener != null && mProducer != null) {
                mProducersListActionListener.onProducerClick(mProducer);
            }
        }
    }

    static class ContentLoadingViewHolder extends RecyclerView.ViewHolder {
        public ContentLoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
