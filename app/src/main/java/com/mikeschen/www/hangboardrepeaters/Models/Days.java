package com.mikeschen.www.hangboardrepeaters.Models;

public class Days {
    private long id;
    private String log;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setComment(String log) {
        this.log = log;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return log;
    }
}
