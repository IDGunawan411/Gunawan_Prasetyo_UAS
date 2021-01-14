package com.example.gunawan_prasetyo_uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

public class MainAppActivity extends AppCompatActivity {
    DataAdapter adapter;
    List<Product> products;
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app_activity);
        showRecycleView();
        srl = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Intent i = new Intent(getApplicationContext(), MainAppActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
                finish();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 1 seconds)
                        srl.setRefreshing(false);
                    }
                }, 1000); // Delay in millis
            }
        });
    }

    private void showRecycleView() {
        RecyclerView view = (RecyclerView) findViewById(R.id.rv_product);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        view.setLayoutManager(mLayoutManager);

        products = MainActivity.db.dataDao().getAll(); //Ambil semua data
        adapter = new DataAdapter(products, this);
        view.setAdapter(adapter);
    }
}