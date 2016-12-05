package com.farmdrop.challenge.producers.model.provider;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersListener;
import com.google.common.collect.Lists;
import com.orm.SugarRecord;

import java.util.Iterator;

public class ProducersLocalProvider {

    public void loadProducers(@NonNull ProducersListener listener) {
        Iterator<Producer> iterator = SugarRecord.findAll(Producer.class);
        listener.onNewProducersLoaded(Lists.newArrayList(iterator));
    }
}
