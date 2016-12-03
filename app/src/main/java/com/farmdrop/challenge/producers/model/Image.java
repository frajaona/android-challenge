package com.farmdrop.challenge.producers.model;


public class Image {
    private String mRemoteUrl;

    public Image(String remoteUrl) {
        mRemoteUrl = remoteUrl;
    }

    public String getRemoteUrl() {
        return mRemoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        mRemoteUrl = remoteUrl;
    }
}
