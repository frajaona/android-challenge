package com.farmdrop.challenge.producers.model;


public class Image {
    private String mPath;
    private int mPosition;

    public Image(String path, int position) {
        mPath = path;
        mPosition = position;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
