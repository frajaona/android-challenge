package com.farmdrop.challenge.producers.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.farmdrop.challenge.producers.R;
import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProducersListFragment extends Fragment {
    @BindView(R.id.fragment_producers_list_layout_root)
    FrameLayout mRootLayout;

    @BindView(R.id.fragment_producers_list_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_producers_list_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.fragment_producers_list_textview_error)
    TextView mErrorTextView;

    private ProducersRecyclerViewAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;

    private ConnectivityBroadcastReceiver mNetworkBroadcastReceiver;

    @Nullable
    private OnProducersListActionListener mOnProducersListActionListener;

    private boolean mLoadingNext;

    private boolean mAllProducersLoaded;

    private boolean mScrollDownToLoadNext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProducersActivity) {
            mOnProducersListActionListener = ((ProducersActivity) context).getOnProducersListActionListener();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkBroadcastReceiver = new ConnectivityBroadcastReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_producers_list, container);
        ButterKnife.bind(this, rootView);
        initRecyclerView(getContext());
        displayLoading();
        mScrollDownToLoadNext = true;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(mNetworkBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mNetworkBroadcastReceiver);
    }

    @UiThread
    public void displayProducers(@NonNull List<Producer> producersList) {
        mLoadingNext = false;

        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);

        mAdapter.setProducersList(producersList);
        mAdapter.notifyDataSetChanged();
    }

    @UiThread
    public void displayError(@ProducersListPresenter.Error int error) {
        int nbProducersDisplayed = mAdapter.getItemCount();
        @StringRes int errorMessage = 0;
        switch (error) {
            case ProducersListPresenter.ERROR_ALL_LOADED:
                mAllProducersLoaded = true;
                mAdapter.setAllProducersLoaded(true);
                mAdapter.notifyDataSetChanged();
                break;
            case ProducersListPresenter.ERROR_NETWORK:
                if (nbProducersDisplayed == 0) {
                    errorMessage = R.string.error_network;
                } else {
                    errorMessage = R.string.error_network_load_more;
                }
                break;
            case ProducersListPresenter.ERROR_UNKNOWN:
            default:
                errorMessage = R.string.error_unknown;
                break;
        }
        if (errorMessage != 0) {
            // if we haven't displayed any producers, display the error in the centered error textview
            if (nbProducersDisplayed == 0) {
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                mErrorTextView.setVisibility(View.VISIBLE);
                mErrorTextView.setText(errorMessage);
            } else {
                // simply display a SnackBar otherwise
                Snackbar.make(mRootLayout, errorMessage, Snackbar.LENGTH_LONG).show();

                // and stop displaying loading ring on last item
                setScrollDownToLoadNextEnable(false);
            }
        }
    }

    public void setScrollDownToLoadNextEnable(boolean scrollDownToLoadNext) {
        mScrollDownToLoadNext = scrollDownToLoadNext;
        if (!mScrollDownToLoadNext) {
            mAdapter.setAllProducersLoaded(true);
        } else {
            mAdapter.setAllProducersLoaded(mAllProducersLoaded);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(@NonNull Context context) {
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context.getResources(), R.drawable.producers_recycler_view_line_divider));
        mRecyclerView.addOnScrollListener(new OnRecyclerViewScrollListener());

        mAdapter = new ProducersRecyclerViewAdapter(context, mOnProducersListActionListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void displayLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);
    }

    /**
     * RecyclerView scroll gesture listener to detect when the user reaches the end of the list, in
     * order to load more producers.
     */
    private class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        private static final int ITEMS_LEFT_BEFORE_LOADING_NEXT = 3;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            // number of views currently display on screen
            int childCount = mLayoutManager.getChildCount();
            // total number of views in layout
            int itemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (mOnProducersListActionListener != null && firstVisibleItemPosition + childCount >= itemCount - ITEMS_LEFT_BEFORE_LOADING_NEXT && !mLoadingNext && !mAllProducersLoaded && mScrollDownToLoadNext) {
                mLoadingNext = true;
                mOnProducersListActionListener.onLoadNextNeeded();
            }
        }
    }

    /**
     * Detect Network connectivity changes to
     */
    private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (!noConnectivity) {
                // when we're back online, reset flags & try to reload the next producers if needed
                setScrollDownToLoadNextEnable(true);

                int nbProducersDisplayed = mAdapter.getItemCount();
                if (mOnProducersListActionListener != null && (mLoadingNext || nbProducersDisplayed == 0)) {
                    if (nbProducersDisplayed == 0) {
                        displayLoading();
                    }
                    mOnProducersListActionListener.onLoadNextNeeded();
                }
            }
        }
    }
}
