package com.example.kursy_walut;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class kursyactivity extends AppCompatActivity {
    private ListView currencyListView;
    private List<String> currencyList = new ArrayList<>();
    private ArrayAdapter<String> currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.kursy_layout);

        currencyListView = findViewById(R.id.currency_list);
        currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyList);
        currencyListView.setAdapter(currencyAdapter);

        TextView titleTextView = findViewById(R.id.title);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.nbp.pl/api/exchangerates/tables/a/?format=json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray ratesArray = response.getJSONObject(0).getJSONArray("rates");
                            for (int i = 0; i < ratesArray.length(); i++) {
                                JSONObject rateObject = ratesArray.getJSONObject(i);
                                String currency = rateObject.getString("code");
                                double rate = rateObject.getDouble("mid");
                                currencyList.add(currency + " - " + rate);
                            }
                            currencyAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

}

