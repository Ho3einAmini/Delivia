package test.mobileapp.controller;

import com.androidnetworking.error.ANError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface NetworkListener {
    void OnDataReceived(JSONArray data);
    void OnError(ANError error);
}
