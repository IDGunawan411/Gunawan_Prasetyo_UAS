package com.example.gunawan_prasetyo_uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

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

public class MainActivity extends AppCompatActivity {
    ProgressBar pb;
    static MyDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "database").allowMainThreadQueries().build();

        List<DevGame> devGames = db.dataDao().getAll();

        if(devGames.size() > 0) { // jika tak ada data
            Intent i = new Intent(getApplicationContext(), MainAppActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(i);
            finish();
            return;
        }
        
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url1 = "https://api.rawg.io/api/developers?page=7";

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                Request.Method.GET,
                url1,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cek json: ", response.toString());
                        String id, dev, game, image, added;

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            if (jsonArray.length() != 0) {
                                for (int i = 2; i < 7 ; i++) {
                                    game = "";
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    dev = data.getString("name").toString().trim();
                                    image = data.getString("image_background").toString().trim();

                                    JSONArray jsonArray1 = data.getJSONArray("games");
                                    if (jsonArray1.length() != 0) {
                                        for (int g = 0; g < jsonArray1.length() ; g++) {
                                            JSONObject data1 = jsonArray1.getJSONObject(g);
                                            game = game + data1.getString("name").toString().trim() +"("+ data1.getString("added").toString().trim() +"), ";

                                        }
                                    }
                                    added = data.getString("games_count").toString().trim();
                                    MainActivity.db.dataDao().insert(dev, game,added,image);
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
        queue.add(jsonObjectRequest1);
    }

}