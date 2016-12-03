package com.farmdrop.challenge.producers.model;


import java.util.List;

public class Producer {

    private String mName;
    private String mShortDescription;
    private String mDescription;
    private String mLocation;
    private List<Image> mImages;

    public Producer(String name, String shortDescription, String description, String location, List<Image> images) {
        mName = name;
        mShortDescription = shortDescription;
        mDescription = description;
        mLocation = location;
        mImages = images;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }
}
