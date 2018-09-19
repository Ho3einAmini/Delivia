package test.mobileapp.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import test.mobileapp.R;
import test.mobileapp.adapter.DeliveryAdapter;
import test.mobileapp.adapter.LoadMoreHelper;
import test.mobileapp.controller.NetworkController;
import test.mobileapp.controller.NetworkListener;
import test.mobileapp.model.DeliveryModel;
import test.mobileapp.model.Request;
import test.mobileapp.utility.SharedPreference;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WaitView waitView;
    DeliveryAdapter adapter = new DeliveryAdapter(new ArrayList<DeliveryModel>());
    int page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waitView = findViewById(R.id.wait_view);
        recyclerView = findViewById(R.id.delivery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setMoreLoadListener(recyclerView, new LoadMoreHelper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getApplicationContext(), "Load more data...", Toast.LENGTH_SHORT).show();
                sendRequest();
            }
        });
        sendRequest();
    }

    private void sendRequest() {
        List<DeliveryModel> models = SharedPreference.getShopCategory(getApplicationContext(), page);
        if(models == null && page == 0)
            waitView.enable();
        if(models != null)
        {
            setData(models);
            adapter.setLoading(false);
            page++;
            return;
        }
        waitView.invisibleBackground();
        String url = getUri();
        final Request request = new Request(url, "deliveries", Priority.IMMEDIATE, new NetworkListener() {
            @Override
            public void OnDataReceived(JSONArray data) {
                Gson gson = new Gson();
                List<DeliveryModel> models = gson.fromJson(data.toString(), new TypeToken<List<DeliveryModel>>(){}.getType());
                SharedPreference.setShopCategory(getApplicationContext(), data.toString() , page);
                setData(models);
                adapter.setLoading(false);
                if(models.size() == 0)
                    adapter.setEnable(false);
                page++;
                waitView.finishOnSuccess();
            }

            @Override
            public void OnError(ANError error) {
                Toast.makeText(getApplicationContext(), "Server Error..." , Toast.LENGTH_SHORT).show();
                if (page == 0)
                    finish();
            }
        });
        NetworkController.SimpleRequest(request);
    }

    private String getUri() {
        String offset = String.valueOf(page*20);
        return "https://mock-api-mobile.dev.lalamove.com/deliveries?limit=20&offset=" + offset;
    }

    private void setData(List<DeliveryModel> models) {
        adapter.addData(models);
        recyclerView.setAdapter(adapter);
    }
}
