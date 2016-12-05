package com.farmdrop.challenge.producers.model;

import com.farmdrop.challenge.producers.model.provider.Persistable;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import org.parceler.Parcel;

@Parcel
@Table
public class Image implements Persistable {
    // Producer id info, needed for Producer <-> Image relationship for the ORM.
    static final String NAMING_PRODUCER_ID = "mProducerId";
    int mProducerId;

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

    public void setProducerId(int producerId) {
        mProducerId = producerId;
    }

    public void persist() {
        SugarRecord.save(this);
    }
}
