package com.farmdrop.challenge.producers.utils;


import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class AndroidNamingFieldStrategy implements FieldNamingStrategy {

    @Override
    public String translateName(Field field) {
        String fieldName = LOWER_CASE_WITH_UNDERSCORES.translateName(field);
        if (fieldName.startsWith("m_")) {
            return fieldName.substring(2);
        } else {
            return fieldName;
        }
    }
}
