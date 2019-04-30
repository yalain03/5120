package com.example.chinni.cs5120;

public class StatItem {
    String key; // the particular stat that we want
    String value; // the value for the stat

    public StatItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
