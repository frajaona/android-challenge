package com.farmdrop.challenge.producers.model.provider;


import android.support.annotation.NonNull;

import com.farmdrop.challenge.producers.model.Producer;
import com.farmdrop.challenge.producers.model.ProducersListener;
import com.google.common.collect.Lists;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;

import java.util.Iterator;
import java.util.List;

public class ProducersLocalProvider {

    public void loadProducers(@NonNull ProducersListener listener) {
        Iterator<Producer> iterator = SugarRecord.findAll(Producer.class);
        listener.onNewProducersLoaded(Lists.newArrayList(iterator));
    }

    @NonNull
    public List<Producer> searchProducers(@NonNull String query) {
        return SugarRecord.find(Producer.class, NamingHelper.toSQLNameDefault(Producer.NAMING_PRODUCER_NAME) + " LIKE ?", "%" + query + "%");
    }
}
