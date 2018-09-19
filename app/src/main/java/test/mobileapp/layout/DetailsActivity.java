package test.mobileapp.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import test.mobileapp.R;
import test.mobileapp.model.DeliveryModel;

public class DetailsActivity extends AppCompatActivity {
    MapFragment mapFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapFragment = MapFragment.newInstance();
        DeliveryModel model = (DeliveryModel) getIntent().getSerializableExtra("model");
        mapFragment.setModel(model);
        setContentView(R.layout.activity_details);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_details_map , mapFragment);
        transaction.commit();
    }
}
