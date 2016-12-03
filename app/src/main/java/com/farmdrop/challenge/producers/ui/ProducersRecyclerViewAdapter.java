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

public class ProducersRecyclerViewAdapter extends RecyclerView.Adapter<ProducersRecyclerViewAdapter.ProducerViewHolder> {

    public static class ProducerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.producers_recycler_view_item_textview_name)
        public TextView nameTextView;

        @BindView(R.id.producers_recycler_view_item_textview_short_description)
        public TextView shortDescriptionTextView;

        public ProducerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Nullable
    private List<Producer> mProducersList;

    @NonNull
    private final LayoutInflater mLayoutInflater;

    public ProducersRecyclerViewAdapter(@NonNull Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ProducerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.producers_recycler_view_item, parent, false);
        return new ProducerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProducerViewHolder holder, int position) {
        Producer producer = mProducersList.get(position);
        holder.nameTextView.setText(producer.getName());
        String shortDescription = producer.getShortDescription();
        if (!TextUtils.isEmpty(shortDescription)) {
            holder.shortDescriptionTextView.setText(producer.getShortDescription());
        } else {
            holder.shortDescriptionTextView.setText(R.string.no_description);
        }
    }

    @Override
    public int getItemCount() {
        return mProducersList != null ? mProducersList.size() : 0;
    }

    public void setProducersList(@NonNull List<Producer> producersList) {
        mProducersList = producersList;
    }
}
