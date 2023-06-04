package com.example.kursy_walut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button kalkulatorbutton, kursybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        kalkulatorbutton = findViewById(R.id.kalkulatorbutton);

        kalkulatorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, kalkulatoractivity.class);
                startActivity(intent);

            }
        });

        kursybutton = findViewById(R.id.kursybutton);

        kursybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, kursyactivity.class);
                startActivity(intent);

            }
        });
    }
}