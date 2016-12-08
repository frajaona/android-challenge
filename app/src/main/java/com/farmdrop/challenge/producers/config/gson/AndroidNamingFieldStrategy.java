package com.farmdrop.challenge.producers.config.gson;


import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class AndroidNamingFieldStrategy implements FieldNamingStrategy {
    private static final String ANDROID_ATTRIBUTES_NAMING_PREFIX = "m_";

    @Override
    public String translateName(Field field) {
        String fieldName = LOWER_CASE_WITH_UNDERSCORES.translateName(field);
        if (fieldName.startsWith(ANDROID_ATTRIBUTES_NAMING_PREFIX)) {
            return fieldName.substring(ANDROID_ATTRIBUTES_NAMING_PREFIX.length());
        } else {
            return fieldName;
        }
    }
}
