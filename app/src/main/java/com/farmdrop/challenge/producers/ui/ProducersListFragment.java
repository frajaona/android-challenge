package com.farmdrop.challenge.producers.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProducersListFragment extends Fragment {
    @BindView(R.id.fragment_producers_list_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_producers_list_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.fragment_producers_list_textview_error)
    TextView mErrorTextView;

    private ProducersRecyclerViewAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_producers_list, container);
        ButterKnife.bind(this, rootView);
        initRecyclerView(getContext());
        initProgressBar();
        return rootView;
    }

    @UiThread
    public void displayProducers(@NonNull List<Producer> producersList) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);

        mAdapter.setProducersList(producersList);
        mAdapter.notifyDataSetChanged();
    }

    @UiThread
    public void displayError(@ProducersProvider.Error int error) {
        @StringRes int errorMessage = 0;
        switch (error) {
            case ProducersProvider.ERROR_NETWORK:
                errorMessage = R.string.error_network;
                break;
            case ProducersProvider.ERROR_UNKNOWN:
            default:
                errorMessage = R.string.error_unknown;
                break;
        }
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(errorMessage);
    }

    private void initRecyclerView(@NonNull Context context) {
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context.getResources(), R.drawable.producers_recycler_view_line_divider));

        mAdapter = new ProducersRecyclerViewAdapter(context);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }
}
