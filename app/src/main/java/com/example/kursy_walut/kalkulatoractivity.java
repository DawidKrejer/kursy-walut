package com.example.kursy_walut;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;

public class kalkulatoractivity extends AppCompatActivity {

    TextView convertfrom, convertTo, conversionratetext;
    EditText converttovalue;
    ArrayList<String> arrayList;
    Dialog fromdialog;
    Dialog todialog;
    Button conversionButton;
    String convertFromValue, convertToValue, conversionValue;
    String[] country = {"AFN", "USD", "PLN", "EUR", "THB", "AUD","HKD","CAD","NZD","HUF","CHF","GBP",};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalkulator_layout);
        getSupportActionBar().hide();

        convertfrom = findViewById(R.id.menu);
        convertTo = findViewById(R.id.menu2);
        conversionButton = findViewById(R.id.conversionButton);
        conversionratetext = findViewById(R.id.conversionratetext);
        converttovalue = findViewById(R.id.converttovalue);

        arrayList = new ArrayList<>();
        for(String i : country){
            arrayList.add(i);
        }
        convertfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromdialog = new Dialog(kalkulatoractivity.this);
                fromdialog.setContentView(R.layout.home);
                fromdialog.getWindow().setLayout(650, 800);
                fromdialog.show();

                EditText editText = fromdialog.findViewById(R.id.edit_text);
                ListView listView = fromdialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(kalkulatoractivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        convertfrom.setText(adapter.getItem(position));
                        fromdialog.dismiss();
                        convertFromValue = adapter.getItem(position);

                    }
                });
            }
        });

        convertTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todialog = new Dialog(kalkulatoractivity.this);
                todialog.setContentView(R.layout.to_home);
                todialog.getWindow().setLayout(650, 800);
                todialog.show();

                EditText editText = todialog.findViewById(R.id.edit_text);
                ListView listView = todialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(kalkulatoractivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        convertTo.setText(adapter.getItem(position));
                        todialog.dismiss();
                        convertToValue = adapter.getItem(position);
                    }
                });
            }
        });

        conversionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Double converttovalue = Double.valueOf(kalkulatoractivity.this.converttovalue.getText().toString());
                    getconversionValue(convertFromValue, convertToValue, converttovalue);

                }
                catch (Exception e) {

                }
            }
        });



    }

    public String getconversionValue(String convertFrom, String convertTo, Double converttovalue) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertTo+"&compact=ultra&apiKey=16cbd7c2c9eda82dff19";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double conversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertTo)), 2);
                    conversionValue = "" + round((conversionRateValue * converttovalue), 2);
                    conversionratetext.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
        return null;
    }
    public static double round(double value, int places) {
        if(places<0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}