package com.askello.requesttester.model;

/**
 * Created by askello on 05.05.2016.
 */
public class Param {
    private String key;
    private String value;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key+"="+value;
    }
}
