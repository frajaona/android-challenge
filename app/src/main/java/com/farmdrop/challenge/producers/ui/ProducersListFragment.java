package com.farmdrop.challenge.producers.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.farmdrop.challenge.producers.presenters.ProducersListPresenter;

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

    private LinearLayoutManager mLayoutManager;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_producers_list, container);
        ButterKnife.bind(this, rootView);
        initRecyclerView(getContext());
        initProgressBar();
        mScrollDownToLoadNext = true;
        return rootView;
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
        @StringRes int errorMessage = 0;
        switch (error) {
            case ProducersListPresenter.ERROR_ALL_LOADED:
                mAllProducersLoaded = true;
                mAdapter.setAllProducersLoaded(true);
                break;
            case ProducersListPresenter.ERROR_NETWORK:
                errorMessage = R.string.error_network;
                break;
            case ProducersListPresenter.ERROR_UNKNOWN:
            default:
                errorMessage = R.string.error_unknown;
                break;
        }
        if (errorMessage != 0) {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mErrorTextView.setVisibility(View.VISIBLE);
            mErrorTextView.setText(errorMessage);
        }
    }

    public void setScrollDownToLoadNextEnable(boolean scrollDownToLoadNext) {
        mScrollDownToLoadNext = scrollDownToLoadNext;
        if (!mScrollDownToLoadNext) {
            mAdapter.setAllProducersLoaded(true);
        } else {
            mAdapter.setAllProducersLoaded(mAllProducersLoaded);
        }
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

    private void initProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

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
}
