package test.mobileapp.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import test.mobileapp.R;
import test.mobileapp.utility.SharedPreference;

public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        RelativeLayout btnFetch = findViewById(R.id.fetch_deliveries_btn);
        RelativeLayout btnDelete = findViewById(R.id.delete_catch_btn);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.deleteAll(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Catch deleted...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
