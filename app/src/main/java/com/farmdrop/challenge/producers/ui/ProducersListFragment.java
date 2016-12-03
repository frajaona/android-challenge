package com.farmdrop.challenge.producers.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProducersListFragment extends Fragment {
    @BindView(R.id.fragment_producers_list_recycler_view)
    RecyclerView mRecyclerView;

    private ProducersRecyclerViewAdapter mAdapter;

    @Nullable
    private List<Producer> mProducerList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_producers_list, container);
        ButterKnife.bind(this, rootView);
        initRecyclerView(getContext());
        return rootView;
    }

    public void displayProducers(@NonNull List<Producer> producersList) {
        if (mAdapter != null) {
            mAdapter.setProducersList(producersList);
            mAdapter.notifyDataSetChanged();
        } else {
            mProducerList = producersList;
        }
    }

    private void initRecyclerView(@NonNull Context context) {
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context.getResources(), R.drawable.producers_recycler_view_line_divider));

        mAdapter = new ProducersRecyclerViewAdapter(context, mProducerList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
