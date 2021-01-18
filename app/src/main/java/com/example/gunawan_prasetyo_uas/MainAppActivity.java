package com.example.gunawan_prasetyo_uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                MainActivity.db.dataDao().deleteAll();
                load_data();

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
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        view.setLayoutManager(mLayoutManager);

        products = MainActivity.db.dataDao().getAll(); //Ambil semua data
        adapter = new DataAdapter(products, this);
        view.setAdapter(adapter);
    }

    protected void load_data(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url1 = "https://api.rawg.io/api/developers?page=7";

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                Request.Method.GET,
                url1,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cek json: ", response.toString());

                        String id, nama, desc, image;
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            if (jsonArray.length() != 0) {
                                for (int i = 1; i < 3 ; i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    nama = " : " + data.getString("name").toString().trim();
                                    image = data.getString("image_background").toString().trim();
//                                    desc = data.getString("games").toCharArray().toString();

                                    JSONArray jsonArray1 = data.getJSONArray("games");
                                    if (jsonArray1.length() != 0) {
                                        for (int g = 0; g < 1 ; g++) {
                                            JSONObject data1 = jsonArray1.getJSONObject(g);

                                            desc = " : " + data1.getString("name").toString().trim();
                                            MainActivity.db.dataDao().insertAll(new Product(nama, desc,image));
                                        }
                                    }

                                }

                                // jika data sudah masuk semua, buka MainAppActivity
                                Intent i = new Intent(getApplicationContext(), MainAppActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(i);
                                finish();// tutup activity ini
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error : ", error.toString());
                    }
                }
        );
//        RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
//        String url2 = "https://api.rawg.io/api/developers?page=6";
//
//        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(
//                Request.Method.GET,
//                url2,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("cek json: ", response.toString());
//
//                        String id, nama, desc, image;
//
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("results");
//                            if (jsonArray.length() != 0) {
//                                for (int i = 0; i < 11 ; i++) {
//                                    JSONObject data = jsonArray.getJSONObject(i);
//
//                                    nama = data.getString("name").toString().trim();
//                                    desc = data.getString("games_count").toString().trim();
//                                    image = data.getString("image_background").toString().trim();
//
//                                    // masukkan melalui dao
//                                    db.dataDao().insertAll(new Product(nama, desc, image));
//                                }
//
//                                // jika data sudah masuk semua, buka MainAppActivity
//                                Intent i = new Intent(getApplicationContext(), MainAppActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                getApplicationContext().startActivity(i);
//                                finish();// tutup activity ini
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("error : ", error.toString());
//                    }
//                }
//        );
        queue.add(jsonObjectRequest1);
//        queue.add(jsonObjectRequest2);
    }

}