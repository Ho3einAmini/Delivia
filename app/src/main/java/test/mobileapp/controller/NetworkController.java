package test.mobileapp.controller;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import test.mobileapp.model.Request;

public class NetworkController {
    public static void SimpleRequest(final Request request)
    {
        AndroidNetworking.get(request.getUrl())
                .setTag(request.getTag())
                .setPriority(request.getPriority())
                .build()
              .getAsJSONArray(new JSONArrayRequestListener() {
                  @Override
                  public void onResponse(JSONArray response) {
                      request.getListener().OnDataReceived(response);
                  }

                  @Override
                  public void onError(ANError anError) {
                      request.getListener().OnError(anError);
                  }
              });
    }
}
