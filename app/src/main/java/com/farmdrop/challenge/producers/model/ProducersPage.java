package com.farmdrop.challenge.producers.model;


import java.util.List;

public class ProducersPage {
    private List<Producer> mResponse;
    private int mCount;
    private Pagination mPagination;

    public ProducersPage(List<Producer> response, int count, Pagination pagination) {
        mResponse = response;
        mCount = count;
        mPagination = pagination;
    }

    public List<Producer> getResponse() {
        return mResponse;
    }

    public void setResponse(List<Producer> response) {
        mResponse = response;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public Pagination getPagination() {
        return mPagination;
    }

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
    }
}
