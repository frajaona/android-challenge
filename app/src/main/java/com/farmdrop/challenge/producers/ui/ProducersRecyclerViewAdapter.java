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

public class ProducersRecyclerViewAdapter extends RecyclerView.Adapter<ProducersRecyclerViewAdapter.ProducerViewHolder> {

    public static class ProducerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.producers_recycler_view_item_textview_name)
        TextView mNameTextView;

        @BindView(R.id.producers_recycler_view_item_textview_short_description)
        TextView mShortDescriptionTextView;

        @Nullable
        private Producer mProducer;

        @NonNull
        private final OnProducerClickListener mProducerClickListener;

        public ProducerViewHolder(View itemView, OnProducerClickListener producerClickListener) {
            super(itemView);
            mProducerClickListener = producerClickListener;
            ButterKnife.bind(this, itemView);
        }

        public void displayProducer(@NonNull Producer producer) {
            mProducer = producer;
            mNameTextView.setText(producer.getName());
            String shortDescription = producer.getShortDescription();
            if (!TextUtils.isEmpty(shortDescription)) {
                mShortDescriptionTextView.setText(producer.getShortDescription());
            } else {
                mShortDescriptionTextView.setText(R.string.no_description);
            }
        }

        @OnClick(R.id.producers_recycler_view_item_layout_root)
        public void onClick() {
            mProducerClickListener.onProducerClick(mProducer);
        }
    }

    @Nullable
    private List<Producer> mProducersList;

    @NonNull
    private final LayoutInflater mLayoutInflater;

    @NonNull
    private final OnProducerClickListener mOnProducerClickListener;

    public ProducersRecyclerViewAdapter(@NonNull Context context, @NonNull OnProducerClickListener onProducerClickListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mOnProducerClickListener = onProducerClickListener;
    }

    @Override
    public ProducerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.producers_recycler_view_item, parent, false);
        return new ProducerViewHolder(view, mOnProducerClickListener);
    }

    @Override
    public void onBindViewHolder(ProducerViewHolder holder, int position) {
        Producer producer = mProducersList.get(position);
        holder.displayProducer(producer);
    }

    @Override
    public int getItemCount() {
        return mProducersList != null ? mProducersList.size() : 0;
    }

    public void setProducersList(@NonNull List<Producer> producersList) {
        mProducersList = producersList;
    }
}
