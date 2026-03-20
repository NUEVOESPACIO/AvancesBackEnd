package com.mycompany.mavenproject4.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class BeanUtilsHelper {

    public static String[] getNullPropertyNames(Object source) {

        BeanWrapper src = new BeanWrapperImpl(source);

        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}