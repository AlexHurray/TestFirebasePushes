package com.mks.testfirebasepushes.event;

public class SetImageEvent {

    private String url;

    public SetImageEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}