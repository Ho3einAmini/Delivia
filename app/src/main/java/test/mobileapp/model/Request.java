package test.mobileapp.model;

import com.androidnetworking.common.Priority;

import test.mobileapp.controller.NetworkListener;

public class Request {
    private String url;
    private String tag;
    private Priority priority;
    private NetworkListener listener;

    public Request(String url, String tag, Priority priority, NetworkListener listener) {
        this.url = url;
        this.tag = tag;
        this.priority = priority;
        this.listener = listener;
    }

    public String getUrl() {
        return url;
    }

    public String getTag() {
        return tag;
    }

    public Priority getPriority() {
        return priority;
    }

    public NetworkListener getListener() {
        return listener;
    }
}
