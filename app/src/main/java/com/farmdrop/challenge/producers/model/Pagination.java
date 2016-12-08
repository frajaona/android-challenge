package com.farmdrop.challenge.producers.model;


public class Pagination {
    private int mCurrent;
    private int mPrevious;
    private int mNext;
    private int mPerPage;
    private int mPages;
    private int mCount;

    public Pagination(int current, int previous, int next, int perPage, int pages, int count) {
        mCurrent = current;
        mPrevious = previous;
        mNext = next;
        mPerPage = perPage;
        mPages = pages;
        mCount = count;
    }

    public int getCurrent() {
        return mCurrent;
    }

    public void setCurrent(int current) {
        mCurrent = current;
    }

    public int getPrevious() {
        return mPrevious;
    }

    public void setPrevious(int previous) {
        mPrevious = previous;
    }

    public int getNext() {
        return mNext;
    }

    public void setNext(int next) {
        mNext = next;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public void setPerPage(int perPage) {
        mPerPage = perPage;
    }

    public int getPages() {
        return mPages;
    }

    public void setPages(int pages) {
        mPages = pages;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
