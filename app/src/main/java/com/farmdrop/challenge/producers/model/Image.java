package com.farmdrop.challenge.producers.model;

import com.orm.dsl.Table;

import org.parceler.Parcel;

@Parcel
@Table
public class Image {
    String mPath;
    int mPosition;

    public Image() {
    }

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
