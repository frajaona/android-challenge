package com.farmdrop.challenge.producers.model;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.provider.Persistable;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;
import com.orm.util.NamingHelper;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
@Table
public class Producer implements Persistable {
    int mId;
    String mName;
    String mPermalink;
    Date mCreatedAt;
    Date mUpdatedAt;
    @Ignore
    List<Image> mImages;
    String mShortDescription;
    String mDescription;
    String mLocation;
    boolean mViaWholesaler;
    String mWholesalerName;

    public Producer() {
    }

    public Producer(int id, String name, String permalink, Date createdAt, Date updatedAt, List<Image> images, String shortDescription, String description, String location, boolean viaWholesaler, String wholesalerName) {
        mId = id;
        set(name, permalink, createdAt, updatedAt, images, shortDescription, description, location, viaWholesaler, wholesalerName);
    }

    public void set(String name, String permalink, Date createdAt, Date updatedAt, List<Image> images, String shortDescription, String description, String location, boolean viaWholesaler, String wholesalerName) {
        mName = name;
        mPermalink = permalink;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
        mImages = images;
        mShortDescription = shortDescription;
        mDescription = description;
        mLocation = location;
        mViaWholesaler = viaWholesaler;
        mWholesalerName = wholesalerName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(String permalink) {
        mPermalink = permalink;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public List<Image> getImages() {
        if (mImages == null || mImages.isEmpty()) {
            mImages = SugarRecord.find(Image.class, NamingHelper.toSQLNameDefault(Image.NAMING_PRODUCER_ID) + " = ?", Integer.toString(mId));
        }
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
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

    public boolean isViaWholesaler() {
        return mViaWholesaler;
    }

    public void setViaWholesaler(boolean viaWholesaler) {
        mViaWholesaler = viaWholesaler;
    }

    public String getWholesalerName() {
        return mWholesalerName;
    }

    public void setWholesalerName(String wholesalerName) {
        mWholesalerName = wholesalerName;
    }

    public void update(@NonNull Producer producer) {
        set(producer.mName, producer.mPermalink, producer.mCreatedAt, producer.mUpdatedAt, producer.mImages, producer.mShortDescription, producer.mDescription, producer.mLocation, producer.mViaWholesaler, producer.mWholesalerName);
        update();
    }

    @Override
    public void persist() {
        SugarRecord.save(this);
        for (Image image : mImages) {
            image.setProducerId(mId);
            image.persist();
        }
    }

    @Override
    public void update() {
        SugarRecord.save(this);
        for (Image image : mImages) {
            image.update();
        }
    }
}
